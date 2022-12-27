package blue.lhf.takapelastin.model;

import static java.lang.Double.NaN;
import static java.lang.Math.*;

/**
 * A utility wrapper around a {@link Drone}'s position: its <code>positionX</code> and <code>positionY</code>.
 * @see Zone
 * */
public record Position(double x, double y) {
    /**
     * @return The distance between this position and the input position.
     * @param other The input position.
     * */
    public double distance(final Position other) {
        return sqrt(pow(this.x - other.x, 2) + pow(this.y - other.y, 2));
    }

    public static Position withX(final double x, final Position old) {
        if (old == null) return new Position(x, NaN);
        return new Position(x, old.y);
    }

    public static Position withY(final double y, final Position old) {
        if (old == null) return new Position(NaN, y);
        return new Position(old.x, y);
    }
}
