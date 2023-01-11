package me.gben.mocky;

import java.util.function.Supplier;

public class StartStubbing {
    private final Supplier<Object> result;

    public StartStubbing(Supplier<?> result) {
        this.result = (Supplier<Object>) result;
    }

    public static <T> StartStubbing doReturn(T value) {
        return new StartStubbing(() -> value);
    }

    public <U> U when(U value) {
        ((MockyInterceptable)value).getInterceptor().setLatestValue(this.result);
        return value;
    }
}
