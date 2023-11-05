package jep440_441_443.exhausting;

class A {
}

class B extends A {
}

sealed interface I permits C, D {
}

final class C implements I {
}

final class D implements I {
}

record Pair<T>(T x, T y) {
}

public class SwitchExhausting {
    public static void main(String[] args) {

        Pair<A> p1 = new Pair<>(new A(), new B());
        int s1 = switch (p1) {
            case Pair<A>(A a, B b) -> 5;
            case Pair<A>(B b, A a) -> 6;
            case Pair<A>(A x, A y) -> 0;
        };

        Pair<I> p2 = new Pair<>(new C(), new D());

        int s2 = switch (p2) {
            case Pair<I>(C c, I i) -> 1;
            case Pair<I>(D d, C c) -> 2;
            case Pair<I>(D d1, D d2) -> 3;
        };

        int s3 = switch (p2) {
            case Pair<I>(C c, D i) -> 1;
            case Pair<I>(D d, C c) -> 2;
            case Pair<I>(C d, C c) -> 3;
            case Pair<I>(D d1, D d2) -> 4;
        };

        System.out.println(STR.
                "Results are: (\{ s1 }, \{ s2 }, \{ s3 })" );
    }
}
