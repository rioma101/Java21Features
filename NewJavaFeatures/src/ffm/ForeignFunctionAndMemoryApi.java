package ffm;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.util.Arrays;

public class ForeignFunctionAndMemoryApi {
    public static void main(String[] args) {
        ForeignFunctionAndMemoryApi radixSorter = new ForeignFunctionAndMemoryApi();
        String[] javaStrings = {"mouse", "cat", "dog", "car"};

        System.out.println("radixsort input: " + Arrays.toString(javaStrings));

        // Perform radix sort on input array of strings
        javaStrings = radixSorter.sort(javaStrings);

        System.out.println("radixsort output: " + Arrays.toString(javaStrings));
    }

    private String[] sort(String[] strings) {
        // Find foreign function on the C library path
        Linker linker = Linker.nativeLinker();
        SymbolLookup stdlib = linker.defaultLookup();
        MemorySegment radixSort = stdlib.find("radixsort").orElseThrow();
        MethodHandle methodHandle = linker.downcallHandle(radixSort, FunctionDescriptor.ofVoid(
                ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_CHAR
        ));

        // Use try-with-resources to manage the lifetime of off-heap memory
        try (Arena arena = Arena.ofConfined()) {
            // Allocate a region of off-heap memory to store pointers
            MemorySegment pointers = arena.allocateArray(ValueLayout.ADDRESS, strings.length);

            // Copy the strings from on-heap to off-heap
            for (int i = 0; i < strings.length; i++) {
                MemorySegment cString = arena.allocateUtf8String(strings[i]);
                pointers.setAtIndex(ValueLayout.ADDRESS, i, cString);
            }

            // Sort the off-heap data by calling the foreign function
            methodHandle.invoke(pointers, strings.length, MemorySegment.NULL, '\0');

            // Copy the (reordered) strings from off-heap to on-heap
            for (int i = 0; i < strings.length; i++) {
                MemorySegment cString = pointers.getAtIndex(ValueLayout.ADDRESS, i);
                cString = cString.reinterpret(Long.MAX_VALUE);
                strings[i] = cString.getUtf8String(0);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        return strings;
    }
}