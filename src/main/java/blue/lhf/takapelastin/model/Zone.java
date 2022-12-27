package blue.lhf.takapelastin.model;

/**
 * Represents a circular zone around a centre point.
 * @param centre The centre point.
 * @param radius The radius: the distance from the centre point to the edge of the zone.
 * */
public record Zone(Position centre, double radius) {
    public static Zone centredAt(double x, double y) {
        return new Zone(new Position(x, y), 0);
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
