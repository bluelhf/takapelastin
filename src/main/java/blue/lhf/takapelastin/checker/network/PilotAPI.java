package blue.lhf.takapelastin.checker.network;

import blue.lhf.takapelastin.http.parsers.*;
import blue.lhf.takapelastin.checker.model.*;
import blue.lhf.takapelastin.http.utility.*;

import java.net.*;
import java.net.http.*;
import java.time.*;
import java.util.*;
import java.util.AbstractMap.*;
import java.util.Map.*;
import java.util.concurrent.*;
import java.util.function.*;

import static java.net.http.HttpClient.newHttpClient;
import static java.net.http.HttpRequest.newBuilder;

public class PilotAPI {
    private final HttpClient client = newHttpClient();
    private final URI baseURI;

    public PilotAPI(final URI baseURI) {
        this.baseURI = baseURI;
    }

    public CompletableFuture<Map<Drone, Pilot>> fetch(final Collection<Drone> drones) {
        final var pilotFutures = drones.stream().map(this::fetch).toList();
        return Futures.join(pilotFutures);
    }

    public CompletableFuture<Entry<Drone, Pilot>> fetch(final Drone drone) {
        return client.sendAsync(buildRequest(drone),
                new JSONParser<>(Pilot.class))
            .thenApply(new RateLimiter<>(Duration.ofSeconds(20)))
            .thenApply(HttpResponse::body).thenApply(Supplier::get)
            .thenApply(pilot -> new SimpleEntry<>(drone, pilot));
    }

    public URI resolveURI(final Drone drone) {
        return baseURI.resolve(drone.getSerialNumber());
    }

    public HttpRequest buildRequest(final Drone drone) {
        return newBuilder(resolveURI(drone)).build();
    }
}
