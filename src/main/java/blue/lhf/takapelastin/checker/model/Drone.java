package blue.lhf.takapelastin.checker.model;

import jakarta.xml.bind.annotation.*;

import static java.lang.Double.NaN;

@SuppressWarnings("unused")
@XmlRootElement(name = "drone")
@XmlType(propOrder = {"serialNumber", "model", "manufacturer", "mac", "ipv4", "ipv6", "firmware", "altitude", "x", "y"})
public final class Drone {
    private String serialNumber;
    private Position position = new Position(NaN, NaN);

    private String model;
    private String manufacturer;
    private String mac;
    private String ipv4;
    private String ipv6;
    private String firmware;
    private double altitude;

    public String getModel() {
        return model;
    }

    @XmlElement(name = "model")
    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    @XmlElement(name = "manufacturer")
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMac() {
        return mac;
    }

    @XmlElement(name = "mac")
    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIpv4() {
        return ipv4;
    }

    @XmlElement(name = "ipv4")
    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public String getIpv6() {
        return ipv6;
    }

    @XmlElement(name = "ipv6")
    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
    }

    public String getFirmware() {
        return firmware;
    }

    @XmlElement(name = "firmware")
    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    public double getAltitude() {
        return altitude;
    }

    @XmlElement(name = "altitude")
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public Position getPosition() {
        return position;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    @XmlElement(name = "serialNumber")
    public void setSerialNumber(final String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public double getX() {
        return this.position.x();
    }

    @XmlElement(name = "positionX")
    public void setX(final double x) {
        this.position = new Position(x, position.y());
    }

    public double getY() {
        return this.position.y();
    }

    @XmlElement(name = "positionY")
    public void setY(final double y) {
        this.position = new Position(position.x(), y);
    }
}
