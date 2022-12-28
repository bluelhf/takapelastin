package blue.lhf.takapelastin.server;

import blue.lhf.takapelastin.checker.*;
import blue.lhf.takapelastin.model.registry.ViolationRegistry;
import blue.lhf.takapelastin.http.adapters.*;
import com.google.gson.*;
import com.sun.net.httpserver.*;

import java.io.*;
import java.time.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * An {@link HttpHandler} which returns HTTP responses with
 * the current status of a {@link ViolationChecker} as JSON data in the response body.
 * */
public class StatusHandler implements HttpHandler {
    private final Gson gson;
    private final ViolationChecker checker;

    public StatusHandler(final ViolationChecker checker) {
        this.checker = checker;
        this.gson = new GsonBuilder()
            .registerTypeAdapter(Instant.class, new JSONInstantSerializer())
            .registerTypeAdapter(ViolationChecker.class, new JSONCheckerSerializer())
            .registerTypeAdapter(ViolationRegistry.class, new JSONRegistrySerializer())
            .setPrettyPrinting()
            .create();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        /*
         * We need two try-catch blocks here, as the specification
         * doesn't permit calling getResponseBody() before sendResponseHeaders()...
         *
         * As of 2022-12-25, the implementation in Adoptium 17 would technically
         * allow for it, but relying on implementation details is a no-no.
         * */

        try {
            final String body = gson.toJson(checker, ViolationChecker.class);
            final byte[] bytes = body.getBytes(UTF_8);

            final Headers responseHeaders = exchange.getResponseHeaders();
            responseHeaders.add("Content-Type", "application/json;charset=utf-8");

            // Allow front-end URL
            responseHeaders.add("Access-Control-Allow-Origin", "https://lhf.blue");

            exchange.sendResponseHeaders(200, bytes.length);

            try (final OutputStream response = exchange.getResponseBody()) {
                response.write(bytes);
            }

        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, 0);
        }
    }
}
