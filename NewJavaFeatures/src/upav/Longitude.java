package upav;

public record Longitude(Point A, Point B) implements HasLength {
    @Override
    public double length() {
        return Math.sqrt((A.x() - B.x()) * (A.x() - B.x()) + (A.y() - B.y()) * (A.y() - B.y()));
    }
}
