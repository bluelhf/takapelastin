package blue.lhf.takapelastin.checker.model;

public record Zone(Position centre, double radius) {
    public static Zone centredAt(final Position centre) {
        return new Zone(centre, 0);
    }

    public static Zone centredAt(double x, double y) {
        return centredAt(new Position(x, y));
    }

    public Zone withRadius(final double radius) {
        return new Zone(centre, radius);
    }

    public boolean contains(final Position position) {
        return distance(position) <= radius;
    }

    public double distance(final Position position) {
        return centre.distance(position);
    }
}
