package blue.lhf.takapelastin.checker.model;

import static java.lang.Math.*;

public record Position(double x, double y) {
    public double distance(final Position other) {
        return sqrt(pow(this.x - other.x, 2) + pow(this.y - other.y, 2));
    }

    @Override
    public String toString() {
        return "(X: %010.3f, Y: %010.3f)".formatted(x, y);
    }
}
