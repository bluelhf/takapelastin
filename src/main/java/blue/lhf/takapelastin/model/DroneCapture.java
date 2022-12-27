package blue.lhf.takapelastin.model;

import blue.lhf.takapelastin.http.adapters.*;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.*;

import java.time.*;
import java.util.*;

import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;

/**
 * Represents a capture of several drones, with a timestamp.
 * */
@SuppressWarnings("unused")
@XmlRootElement(name = "capture")
@XmlAccessorType(FIELD)
public class DroneCapture {
    @XmlAttribute(name = "snapshotTimestamp")
    @XmlJavaTypeAdapter(value = XMLInstantAdapter.class)
    private Instant timestamp;

    @XmlElement(name = "drone")
    private List<Drone> drones;

    public List<Drone> dronesWithin(final Zone zone) {
        return drones.stream().filter(drone -> zone.contains(drone.position())).toList();
    }

    public Instant timestamp() {
        return timestamp;
    }

    public List<Drone> drones() {
        return drones;
    }
}
