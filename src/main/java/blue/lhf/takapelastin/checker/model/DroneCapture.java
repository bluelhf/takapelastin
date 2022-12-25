package blue.lhf.takapelastin.checker.model;

import blue.lhf.takapelastin.http.adapters.*;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.*;

import java.time.*;
import java.util.*;

@SuppressWarnings("unused")
@XmlRootElement(name = "capture")
@XmlType(propOrder = {"timestamp", "drones"})
@XmlAccessorType(XmlAccessType.FIELD)
public class DroneCapture {
    @XmlAttribute(name = "snapshotTimestamp")
    @XmlJavaTypeAdapter(value = XMLInstantAdapter.class)
    private Instant timestamp;

    @XmlElement(name = "drone")
    private List<Drone> drones;

    public List<Drone> withinZone(final Zone zone) {
        return drones.stream().filter(drone -> zone.contains(drone.getPosition())).toList();
    }

    public Instant timestamp() {
        return timestamp;
    }

    public List<Drone> drones() {
        return drones;
    }
}
