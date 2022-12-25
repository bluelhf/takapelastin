package blue.lhf.takapelastin.checker.registry;

import java.time.*;

public record Violation(String droneSerialNumber, Instant timestamp, long tick, double closestDistance) {
    public Violation mergeDistance(final Violation toMergeWith) {
        return new Violation(droneSerialNumber, timestamp, tick,
            Math.min(closestDistance, toMergeWith.closestDistance));
    }
}
