package jep440_441_443.shapes;

public record Circle(Point S, double radius) implements HasLength {
    @Override
    public double length() {
        return 2 * radius * Math.PI;
    }
}
