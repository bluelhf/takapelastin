package blue.lhf.takapelastin.checker;

import blue.lhf.takapelastin.Application;
import blue.lhf.takapelastin.model.*;
import blue.lhf.takapelastin.model.registry.*;
import blue.lhf.takapelastin.checker.network.*;

import java.io.Closeable;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.LockSupport;

import static java.lang.System.Logger.Level.ERROR;

/**
 * A {@link Thread} which periodically checks the specified
 * API endpoints for data about drones and pilots, and aggregates
 * them into a {@link ViolationRegistry}
 * <p>
 * The latest drone report is also stored.
 * */
public class ViolationChecker extends Thread implements Closeable {
    private final CheckerOptions options;

    private final PilotAPI pilots;
    private final DroneAPI drones;

    private final ViolationRegistry registry = new ViolationRegistry();

    private long tick = 0;
    private boolean executing = false;
    private DroneReport lastReport = null;

    public ViolationChecker(final CheckerOptions options) {
        this.options = options;
        this.pilots = new PilotAPI(options.pilotBaseURI());
        this.drones = new DroneAPI(options.droneURI());
    }

    @Override
    public void run() {
        executing = true;
        while (executing) {
            tick();

            final Duration updatePeriod = options.updatePeriod();
            final long updatePeriodNanos = updatePeriod.toNanos();
            LockSupport.parkNanos(updatePeriodNanos);
        }
    }

    private void tick() {
        try {

            final DroneReport report = drones.fetch().join();
            final DroneCapture capture = report.getCapture();

            this.lastReport = report;

            final long newTick = tick + 1;
            mergeViolators(capture, newTick).join();
            tick = newTick;

        } catch (Exception exception) {
            Application.LOGGER.log(ERROR, "Failed to tick violation checker", exception);
        }
    }

    private CompletableFuture<Void> mergeViolators(final DroneCapture capture, final long tick) {
        return getViolators(capture).thenAccept(violators -> {
            for (final var entry : violators.entrySet()) {
                final Drone drone = entry.getKey();
                final Pilot pilot = entry.getValue();

                final Violation violation = Violation.forDrone(drone, capture, getNoDroneZone(), tick);
                registry.merge(pilot, violation, Violation::merge);
            }

            registry.removeOlderThan(
                options.violationLifetime(),
                capture.timestamp());
        });
    }

    private CompletableFuture<Map<Drone, Pilot>> getViolators(final DroneCapture capture) {
        final List<Drone> violatingDrones = capture.dronesWithin(getNoDroneZone());
        return pilots.fetchFor(violatingDrones);
    }

    public DroneReport getLastReport() {
        return lastReport;
    }

    public ViolationRegistry getRegistry() {
        return registry;
    }

    public long getTick() {
        return tick;
    }

    public Duration getUpdatePeriod() {
        return options.updatePeriod();
    }

    public Zone getNoDroneZone() {
        return options.noDroneZone();
    }

    public void close() {
        executing = false;
    }
}
