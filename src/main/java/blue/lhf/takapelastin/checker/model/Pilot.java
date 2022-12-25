package blue.lhf.takapelastin.checker.model;

public record Pilot(String pilotId,
                    String firstName, String lastName,
                    String phoneNumber, String email) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pilot pilot = (Pilot) o;

        return pilotId.equals(pilot.pilotId);
    }

    @Override
    public int hashCode() {
        return pilotId.hashCode();
    }
}
