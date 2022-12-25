package blue.lhf.takapelastin.checker.model;

import jakarta.xml.bind.annotation.*;

@SuppressWarnings("unused")
@XmlRootElement(name = "report")
@XmlAccessorType(XmlAccessType.FIELD)
public class DroneReport {
    private DroneCapture capture;

    public DroneCapture getCapture() {
        return capture;
    }
}
