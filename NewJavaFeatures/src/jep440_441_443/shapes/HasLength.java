package jep440_441_443.shapes;

public sealed interface HasLength permits Circle, Longitude {
    double length();
}
