package blue.lhf.takapelastin;

import blue.lhf.takapelastin.checker.*;
import blue.lhf.takapelastin.server.StatusServer;

import java.io.IOException;
import java.net.*;

import static blue.lhf.takapelastin.model.Zone.centredAt;
import static java.lang.Runtime.getRuntime;
import static java.lang.System.Logger.Level.INFO;
import static java.time.Duration.*;

/**
 * The application entry-point. May throw exceptions if initialisation fails.
 * */
public final class Application {
    /**
     * The application logger. Has to use {@link System.Logger} due to
     * <a href="https://bugs.openjdk.org/browse/JDK-8161253">JDK-8161253</a>.
     * */
    public static final System.Logger LOGGER = System.getLogger(Application.class.getName());

    /**
     * Conversion ratio between metres (used in the problem statement) and API units.
     * */
    public static final double METRES = 1000;

    /**
     * The port to start the status server on.
     * */
    public static final int PORT = 8002;

    /**
     * Static options for the {@link ViolationChecker}, as described in the problem statement.
     * */
    public static final CheckerOptions OPTIONS =
        CheckerOptions.builder()
            .setUpdatePeriod(ofSeconds(2))
            .setViolationLifetime(ofMinutes(10))
            .setDroneEndpoint(URI.create("https://assignments.reaktor.com/birdnest/drones"))
            .setPilotEndpoint(URI.create("https://assignments.reaktor.com/birdnest/pilots/"))
            .setNoDroneZone(
                centredAt(250_000, 250_000)
                    .withRadius(100 * METRES)
            ).build();


    /**
     * Constructs and starts the application.
     * <p>
     * A shutdown hook is added to close the application when the Java Runtime shuts down.
     * */
    public static void main(String[] args) throws IOException {
        final Application application = new Application(PORT);

        getRuntime().addShutdownHook(new Thread(application::close));
        application.start();

    }
    private final ViolationChecker checker;
    private final StatusServer server;

    /**
     * Starts the {@link ViolationChecker} and {@link StatusServer}, then waits.
     * */
    public Application(final int serverPort) throws IOException {
        this.checker = new ViolationChecker(OPTIONS);
        this.server = new StatusServer(checker, serverPort);
    }

    public void start() {
        LOGGER.log(INFO, "Starting everything, serving on port {0,number,#}! :)", PORT);
        LOGGER.log(INFO, "Try going to http://localhost:{0,number,#}/", PORT);

        checker.start();
        server.start();
    }

    public void close() {
        LOGGER.log(INFO, "Stopping everything. I'm a bit slow — please be patient!");

        checker.close();
        server.close();
    }
}
