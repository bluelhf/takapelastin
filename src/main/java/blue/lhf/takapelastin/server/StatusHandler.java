package blue.lhf.takapelastin.server;

import blue.lhf.takapelastin.checker.*;
import blue.lhf.takapelastin.checker.registry.*;
import blue.lhf.takapelastin.http.adapters.*;
import com.google.gson.*;
import com.sun.net.httpserver.*;

import java.io.*;
import java.time.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class StatusHandler implements HttpHandler {
    private final Gson gson;
    private final ViolationChecker checker;

    public StatusHandler(final ViolationChecker checker) {
        this.checker = checker;
        this.gson = new GsonBuilder()
            .registerTypeAdapter(Instant.class, new JSONInstantAdapter())
            .registerTypeAdapter(ViolationChecker.class, new JSONCheckerAdapter())
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
         * allow for it, but that's bad and a no-no.
         * */

        try {
            final String body = gson.toJson(checker, ViolationChecker.class);
            final byte[] bytes = body.getBytes(UTF_8);
            exchange.sendResponseHeaders(200, bytes.length);

            try (final OutputStream response = exchange.getResponseBody()) {
                exchange.getResponseHeaders().add("Content-Type", "application/json");
                response.write(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, 0);
        }
    }
}
