package blue.lhf.takapelastin.checker;

import blue.lhf.takapelastin.checker.model.*;
import blue.lhf.takapelastin.checker.network.*;
import blue.lhf.takapelastin.checker.registry.*;
import com.sun.istack.*;
import jakarta.xml.bind.*;

import java.io.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class ViolationChecker extends Thread implements Closeable {
    private final ViolationOptions options;

    private final PilotAPI pilots;
    private final DroneAPI drones;

    private final ViolationRegistry registry = new ViolationRegistry();

    private final AtomicLong tick = new AtomicLong(0);
    private @Nullable DroneReport lastReport = null;

    private boolean executing = false;

    public ViolationChecker(final ViolationOptions options) throws JAXBException {
        this.options = options;
        this.pilots = new PilotAPI(options.pilotBaseURI());
        this.drones = new DroneAPI(options.droneURI());
    }

    private void tick(final DroneReport report) {
        this.lastReport = report;
        this.tick.getAndIncrement();
    }

    @Override
    public void run() {
        executing = true;
        while (executing) {
            try {
                final DroneReport report = drones.fetch().join();
                tick(report);

                final DroneCapture capture = report.getCapture();
                final Instant timestamp = capture.timestamp();

                registry.removeOlderThan(options.violationLifetime(), timestamp);

                final List<Drone> violatingDrones = report.getCapture()
                    .withinZone(options.noDroneZone());

                final Map<Drone, Pilot> violators = pilots.fetch(violatingDrones).join();

                for (final var entry : violators.entrySet()) {
                    final Drone drone = entry.getKey();
                    final Pilot pilot = entry.getValue();

                    final Violation violation = new Violation(
                        drone.getSerialNumber(), timestamp, tick.get(),
                        options.noDroneZone().distance(drone.getPosition())
                    );

                    registry.merge(pilot, violation,
                        (old, candidate) -> candidate.mergeDistance(old));
                }

            } catch (Exception exception) {
                System.err.println("Failed to update: ");
                exception.printStackTrace();
            }

            LockSupport.parkNanos(options.updatePeriod().toNanos());
        }
    }


    public DroneReport getLastReport() {
        return lastReport;
    }

    public ViolationRegistry getRegistry() {
        return registry;
    }

    public long getTick() {
        return tick.get();
    }

    public void close() {
        executing = false;
    }

    public Duration getUpdatePeriod() {
        return options.updatePeriod();
    }
}
