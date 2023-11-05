package jep444;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class VirtualThreads {
    public static void main(String[] args) {

        long start1 = System.nanoTime();
        List<String> fetchedList = fetchEveryoneVirtual(1);
        long end1 = System.nanoTime();
        System.out.println("Elapsed Time in milliseconds: "+ (end1-start1) / 1_000_000);

        long start2 = System.nanoTime();
        fetchedList = fetchEveryoneWithoutThreads(1);
        long end2 = System.nanoTime();
        System.out.println("Elapsed Time in milliseconds: "+ (end2-start2) / 1_000_000);


    }

    private static List<String> fetchEveryoneVirtual(int schoolId) {
        List<String> everyone = new ArrayList<>();
        try (var e = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<List<String>> teachers = e.submit(() -> MockServiceCaller.fetchTeachersAtSchool(schoolId));
            Future<List<String>> students = e.submit(() -> MockServiceCaller.fetchStudentsAtSchool(schoolId));
            everyone.addAll(teachers.get(20L, TimeUnit.SECONDS));
            everyone.addAll(students.get(20L, TimeUnit.SECONDS));
        } catch (ExecutionException e) {
            e.getCause().printStackTrace();
            throw new RuntimeException(e);
        } catch (InterruptedException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        return everyone;
    }

    private static List<String> fetchEveryoneWithoutThreads(int schoolId) {
        List<String> everyone = new ArrayList<>();
        try {
            everyone = new ArrayList<>(MockServiceCaller.fetchTeachersAtSchool(schoolId));
            everyone.addAll(MockServiceCaller.fetchStudentsAtSchool(schoolId));
        } catch (NoStudentsException e) {
            e.printStackTrace();
        }

        return everyone;
    }
}
