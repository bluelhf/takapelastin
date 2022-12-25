package blue.lhf.takapelastin.checker.network;

import blue.lhf.takapelastin.http.parsers.*;
import blue.lhf.takapelastin.checker.model.*;
import blue.lhf.takapelastin.http.utility.*;

import java.net.*;
import java.net.http.*;
import java.time.*;
import java.util.concurrent.*;
import java.util.function.*;

import static java.net.http.HttpClient.newHttpClient;
import static java.net.http.HttpRequest.newBuilder;

public class DroneAPI {
    private final HttpClient client = newHttpClient();
    private final URI baseURI;

    private final XMLParser<DroneReport> parser;

    public DroneAPI(final URI baseURI) {
        this.baseURI = baseURI;
        this.parser = new XMLParser<>(DroneReport.class);
    }

    public HttpRequest buildRequest() {
        return newBuilder(baseURI).build();
    }

    public CompletableFuture<DroneReport> fetch() {
        return client.sendAsync(buildRequest(), parser)
            .thenApply(new RateLimiter<>(Duration.ofSeconds(20)))
            .thenApply(HttpResponse::body)
            .thenApply(Supplier::get);
    }
}
