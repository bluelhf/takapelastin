package blue.lhf.takapelastin.checker.network;

import blue.lhf.takapelastin.http.handlers.JSONHandler;
import blue.lhf.takapelastin.model.*;
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

/**
 * Wrapper for the <code>/pilots/</code> endpoint in the Reaktor API.
 * */
public class PilotAPI {
    private final HttpClient client = newHttpClient();
    private final URI baseURI;

    public PilotAPI(final URI baseURI) {
        this.baseURI = baseURI;
    }

    public CompletableFuture<Map<Drone, Pilot>> fetchFor(final Collection<Drone> drones) {
        final var pilotFutures = drones.stream().map(this::fetchFor).toList();
        return Futures.join(pilotFutures);
    }

    public CompletableFuture<Entry<Drone, Pilot>> fetchFor(final Drone drone) {
        return client.sendAsync(buildRequest(drone),
                new JSONHandler<>(Pilot.class))
            .thenApply(new RateLimiter<>(Duration.ofSeconds(20)))
            .thenApply(HttpResponse::body).thenApply(Supplier::get)
            .thenApply(pilot -> new SimpleEntry<>(drone, pilot));
    }

    public URI resolveURI(final Drone drone) {
        return baseURI.resolve(drone.serialNumber());
    }

    public HttpRequest buildRequest(final Drone drone) {
        return newBuilder(resolveURI(drone)).build();
    }
}
