package blue.lhf.takapelastin.checker.network;

import blue.lhf.takapelastin.http.handlers.XMLHandler;
import blue.lhf.takapelastin.model.*;
import blue.lhf.takapelastin.http.utility.*;

import java.net.*;
import java.net.http.*;
import java.util.concurrent.*;
import java.util.function.*;

import static java.net.http.HttpClient.newHttpClient;
import static java.net.http.HttpRequest.newBuilder;
import static java.time.Duration.ofSeconds;

/**
 * Wrapper for the <code>/drones</code> endpoint in the Reaktor API.
 * */
public class DroneAPI {
    private final HttpClient client = newHttpClient();
    private final URI baseURI;

    private final XMLHandler<DroneReport> parser;

    public DroneAPI(final URI baseURI) {
        this.baseURI = baseURI;
        this.parser = new XMLHandler<>(DroneReport.class);
    }

    public HttpRequest buildRequest() {
        return newBuilder(baseURI).build();
    }

    public CompletableFuture<DroneReport> fetch() {
        return client.sendAsync(buildRequest(), parser)
            .thenApply(new RateLimiter<>(ofSeconds(20)))
            .thenApply(HttpResponse::body)
            .thenApply(Supplier::get);
    }
}
