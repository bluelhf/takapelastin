package blue.lhf.takapelastin.checker;

import blue.lhf.takapelastin.model.*;

import java.net.*;
import java.time.*;

/**
 * Various properties of the problem, such as the no-drone-zone configuration and violation lifetime.
 * @see CheckerOptions#builder()
 * */
public record CheckerOptions(
    URI droneURI, URI pilotBaseURI, Zone noDroneZone,
    Duration violationLifetime, Duration updatePeriod) {

    /**
     * Constructs a {@link Builder} to help with configuring a {@link ViolationChecker}
     * */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A simple builder for constructing {@link CheckerOptions}.
     * @see CheckerOptions#builder()
     * */
    public static class Builder {
        private Builder() {
        }

        private Duration updatePeriod;

        private URI droneURI;
        private URI pilotBaseURI;
        private Zone noDroneZone;
        private Duration violationLifetime;

        public Builder setViolationLifetime(Duration violationLifetime) {
            this.violationLifetime = violationLifetime;
            return this;
        }

        public Builder setDroneEndpoint(URI droneURI) {
            this.droneURI = droneURI;
            return this;
        }

        public Builder setUpdatePeriod(Duration updatePeriod) {
            this.updatePeriod = updatePeriod;
            return this;
        }

        public Builder setPilotEndpoint(URI pilotBaseURI) {
            this.pilotBaseURI = pilotBaseURI;
            return this;
        }

        public Builder setNoDroneZone(Zone noDroneZone) {
            this.noDroneZone = noDroneZone;
            return this;
        }

        public CheckerOptions build() {
            return new CheckerOptions(droneURI, pilotBaseURI, noDroneZone, violationLifetime, updatePeriod);
        }
    }
}
