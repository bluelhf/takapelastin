package blue.lhf.takapelastin.model;

import jakarta.xml.bind.annotation.*;

import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;

/**
 * Represents a drone, with support for JAXB (un)marshalling.
 */
@SuppressWarnings("unused")
@XmlRootElement(name = "drone")
@XmlAccessorType(FIELD)
public final class Drone {
    private String serialNumber, model,
        manufacturer, mac, ipv4, ipv6, firmware;

    private double altitude;

    @XmlTransient
    private Position position;

    @XmlElement(name = "positionX")
    public void setX(final double x) {
        this.position = Position.withX(x, position);
    }

    @XmlElement(name = "positionY")
    public void setY(final double y) {
        this.position = Position.withY(y, position);
    }

    public String serialNumber() {
        return serialNumber;
    }

    public Position position() {
        return position;
    }
}