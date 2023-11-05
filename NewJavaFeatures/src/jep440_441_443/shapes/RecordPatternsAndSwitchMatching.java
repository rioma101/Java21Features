package jep440_441_443.shapes;

import jep440_441_443.shapes.Circle;
import jep440_441_443.shapes.HasLength;
import jep440_441_443.shapes.Longitude;
import jep440_441_443.shapes.Point;

public class RecordPatternsAndSwitchMatching {
    private static Point calculateMiddlePoint(HasLength shape) {
        return switch (shape) {
            case Longitude(Point(double x1, double y1), Point(double x2, double y2)) ->
                    new Point((x1 + x2) / 2, (y1 + y2) / 2);
            case Circle(Point p, _) -> p;
        };
    }

    public static void main(String[] args) {
        Point middlePoint = calculateMiddlePoint(new Circle(new Point(2, 5), 5));
        Point middlePointOfLongitude = calculateMiddlePoint(new Longitude(new Point(1,4), new Point(3, 8)));
        System.out.println(STR."Middle point of the circle is (\{middlePoint.x()}, \{middlePoint.y()})");
        System.out.println(STR."Middle point of the line is (\{middlePointOfLongitude.x()}, \{middlePointOfLongitude.y()})");
    }
}
