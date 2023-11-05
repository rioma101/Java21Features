package upav;

public record Circle(Point S, double radius) implements HasLength {
    @Override
    public double length() {
        return 2 * radius * Math.PI;
    }
}
