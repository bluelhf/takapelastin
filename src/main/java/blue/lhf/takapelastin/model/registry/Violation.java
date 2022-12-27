package blue.lhf.takapelastin.model.registry;

import blue.lhf.takapelastin.checker.ViolationChecker;
import blue.lhf.takapelastin.model.*;

import java.time.Instant;

/**
 * Represents a no-drone-zone violation made by a {@link Pilot}.
 * @see ViolationRegistry
 * @see ViolationChecker
 * */
public record Violation(String droneSerialNumber, Instant timestamp, long tick, double closestDistance) {

    public static Violation forDrone(final Drone drone,
                                     final DroneCapture owningCapture,
                                     final Zone noDroneZone,
                                     final long tick) {

        return new Violation(drone.serialNumber(), owningCapture.timestamp(),
            tick, noDroneZone.distance(drone.position()));
    }

    /**
     * Merges two violations, using the timestamp and tick of the input violation, and the minimum
     * of the two violations' observed distances.
     * <p>
     * It should always be ensured that the input violation is the one with the greater {@link Violation#tick}:
     * Otherwise, violations may go back in time.
     *
     * @param newer The input violation.
     * */
    public Violation merge(final Violation newer) {
        return new Violation(droneSerialNumber, newer.timestamp, newer.tick, Math.min(this.closestDistance, newer.closestDistance));
    }
}
