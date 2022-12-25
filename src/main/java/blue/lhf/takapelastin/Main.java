package blue.lhf.takapelastin;

import blue.lhf.takapelastin.checker.*;
import blue.lhf.takapelastin.checker.model.*;
import blue.lhf.takapelastin.server.*;
import jakarta.xml.bind.*;

import java.io.*;
import java.net.*;
import java.time.*;

import static java.time.Duration.ofSeconds;

public class Main {
    public static final double METRES = 1000;
    public static final Zone NO_DRONE_ZONE = Zone.centredAt(250_000, 250_000).withRadius(100 * METRES);

    public static final ViolationOptions options =
        ViolationOptions.builder()
            .withUpdatePeriod(ofSeconds(2))
            .withViolationLifetime(Duration.ofMinutes(10))
            .withDroneEndpoint(URI.create("https://assignments.reaktor.com/birdnest/drones"))
            .withPilotEndpoint(URI.create("https://assignments.reaktor.com/birdnest/pilots/"))
            .withNoDroneZone(NO_DRONE_ZONE)
            .build();

    public static void main(String[] args) throws IOException, JAXBException {
        final Main main = new Main();
        Runtime.getRuntime().addShutdownHook(new Thread(main::close));
        main.start();
    }

    protected final ViolationChecker checker;
    protected final StatusServer apiServer;

    public Main() throws IOException, JAXBException {
        checker = new ViolationChecker(options);
        this.apiServer = new StatusServer(checker, 8002);
    }

    public void start() {
        checker.start();
        apiServer.start();
    }

    public void close() {
        checker.close();
        apiServer.close();
    }
}
