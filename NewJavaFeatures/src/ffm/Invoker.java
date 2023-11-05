package ffm;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public class Invoker {
    public static void main(String[] args) {
        try {
            long test = invokeStrlen("12345678");
            System.out.println(test);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    static long invokeStrlen(String s) throws Throwable {

        try (Arena arena = Arena.ofConfined()) {

            // Allocate off-heap memory and
            // copy the argument, a Java string, into off-heap memory
            MemorySegment nativeString = arena.allocateUtf8String(s);

            // Link and call the C function strlen

            // Obtain an instance of the native linker
            Linker linker = Linker.nativeLinker();

            // Locate the address of the C function signature
            SymbolLookup stdLib = linker.defaultLookup();
            MemorySegment strlen_addr = stdLib.find("strlen").get();

            // Create a description of the C function
            FunctionDescriptor strlen_sig =
                    FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS);

            // Create a downcall handle for the C function
            MethodHandle strlen = linker.downcallHandle(strlen_addr, strlen_sig);

            // Call the C function directly from Java
            return (long)strlen.invokeExact(nativeString);
        }
    }
}
