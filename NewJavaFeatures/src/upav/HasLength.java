package upav;

public sealed interface HasLength permits Circle, Longitude {
    double length();
}
