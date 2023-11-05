package upav;

public class UnnamedPatternsAndVariables {
    private static Point calculateMiddlePoint(HasLength shape) {
        return switch (shape) {
            case Longitude(Point(double x1, double y1), Point(double x2, double y2)) ->
                    new Point((x1 + x2) / 2, (y1 + y2) / 2);
            case Circle(Point p, _) -> p;
        };
    }
}
