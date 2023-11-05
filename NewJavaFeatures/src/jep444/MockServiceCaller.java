package jep444;

import java.time.Duration;
import java.util.List;

import static java.lang.Thread.sleep;

public class MockServiceCaller {
    private static final boolean throwException = false;
    public static List<String> fetchTeachersAtSchool(int schoolId) {
        try {
            sleep(Duration.ofSeconds(2));
            return List.of("Josip", "Josipa");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<String> fetchStudentsAtSchool(int schoolId) throws NoStudentsException {
        try {
            sleep(Duration.ofSeconds(2));
            if (throwException) {
                throw new NoStudentsException();
            }
            return List.of("Marko", "Mario");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
