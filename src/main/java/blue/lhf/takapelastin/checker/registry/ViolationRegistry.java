package blue.lhf.takapelastin.checker.registry;

import blue.lhf.takapelastin.checker.model.*;

import java.time.*;
import java.util.*;

public class ViolationRegistry extends HashMap<Pilot, Violation> {
    public void removeOlderThan(final Duration maximumAge, Instant now) {
        entrySet().removeIf(entry -> {
            final Duration age = Duration.between(entry.getValue().timestamp(), now);
            return age.compareTo(maximumAge) >= 0;
        });
    }

}
