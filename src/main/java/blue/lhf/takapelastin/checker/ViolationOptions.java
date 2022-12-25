package blue.lhf.takapelastin.checker;

import blue.lhf.takapelastin.checker.model.*;

import java.net.*;
import java.time.*;

public record ViolationOptions(
    URI droneURI,
    URI pilotBaseURI,
    Zone noDroneZone,
    Duration violationLifetime,
    Duration updatePeriod
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Builder() {
        }


        private Duration updatePeriod;

        private URI droneURI;
        private URI pilotBaseURI;
        private Zone noDroneZone;
        private Duration violationLifetime;

        public Builder withViolationLifetime(Duration violationLifetime) {
            this.violationLifetime = violationLifetime;
            return this;
        }

        public Builder withDroneEndpoint(URI droneURI) {
            this.droneURI = droneURI;
            return this;
        }

        public Builder withUpdatePeriod(Duration updatePeriod) {
            this.updatePeriod = updatePeriod;
            return this;
        }

        public Builder withPilotEndpoint(URI pilotBaseURI) {
            this.pilotBaseURI = pilotBaseURI;
            return this;
        }

        public Builder withNoDroneZone(Zone noDroneZone) {
            this.noDroneZone = noDroneZone;
            return this;
        }

        public ViolationOptions build() {
            return new ViolationOptions(droneURI, pilotBaseURI, noDroneZone, violationLifetime, updatePeriod);
        }
    }
}
