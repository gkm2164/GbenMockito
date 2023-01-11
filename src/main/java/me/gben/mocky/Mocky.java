package me.gben.mocky;

public class Mocky {
    static final RealMocky realMocky = new RealMocky();

    public static <T> T mock(Class<T> t) {
        return realMocky.mock(t);
    }

    public static <T> OnGoingStubbing<T> when(T methodCall) {
        return realMocky.when(methodCall);
    }
}
