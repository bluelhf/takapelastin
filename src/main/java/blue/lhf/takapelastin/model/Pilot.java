package blue.lhf.takapelastin.model;

/**
 * Represents a person who pilots a {@link Drone}.
 * */
public record Pilot(String pilotId, String firstName, String lastName,
                    String phoneNumber, String email) {
}
