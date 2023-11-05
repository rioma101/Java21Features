package vectorapi;

import jdk.incubator.vector.IntVector;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class VectorApi {
    static private int[] addTwoVectorArrays(int[] arr1, int[] arr2) {
        var v1 = IntVector.fromArray(IntVector.SPECIES_PREFERRED, arr1, 0);
        var v2 = IntVector.fromArray(IntVector.SPECIES_PREFERRED, arr2, 0);
        var result = v1.add(v2);
        return result.toArray();
    }

    static private int[] addTwoScalarArrays(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            result[i] = arr1[i] + arr2[i];
        }
        return result;
    }

    static private int[] initRandomArray(final int size) {
        Random rd = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rd.nextInt();
        }
        return arr;
    }

    public static void main(String[] args) {
        final int size1 = 500_000_000;
        final int[] arr1 = initRandomArray(size1);
        final int[] arr2 = initRandomArray(size1);

        final int size2 = 1_000_000;
        final int[] arr3 = initRandomArray(size2);
        final int[] arr4 = initRandomArray(size2);


        var start = Instant.now();
        var resultArray = addTwoScalarArrays(arr1, arr2);
        var end = Instant.now();
        System.out.println(STR. "Adding two big arrays with for loop took: \{ Duration.between(start, end).toMillis() } ms" );

        start = Instant.now();
        resultArray = addTwoVectorArrays(arr1, arr2);
        end = Instant.now();
        System.out.println(STR. "Adding two big arrays using Vector API took: \{ Duration.between(start, end).toMillis() } ms" );

        start = Instant.now();
        resultArray = addTwoScalarArrays(arr3, arr4);
        end = Instant.now();
        System.out.println(STR. "Adding two small arrays with for loop took: \{ Duration.between(start, end).toMillis() } ms" );

        start = Instant.now();
        resultArray = addTwoVectorArrays(arr3, arr4);
        end = Instant.now();
        System.out.println(STR. "Adding two small arrays using Vector API took: \{ Duration.between(start, end).toMillis() } ms" );

    }
}
