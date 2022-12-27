package blue.lhf.takapelastin.model;

import jakarta.xml.bind.annotation.*;

import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;

/**
 * Represents a report from the drone detection equipment, though only the drone capture is included: the rest is unused.
 * */
@SuppressWarnings("unused")
@XmlRootElement(name = "report")
@XmlAccessorType(FIELD)
public class DroneReport {
    private DroneCapture capture;

    public DroneCapture getCapture() {
        return capture;
    }
}
