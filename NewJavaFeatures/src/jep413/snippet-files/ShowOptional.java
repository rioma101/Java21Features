import jep413.Test;

import java.util.Optional;

public class ShowOptional {
    void show(Optional<String> v) {
        // @start region="example"
        if (v.isPresent()) {
            System.out.println("v: " + Test.testFunction(););
        }
        // @end
    }
}