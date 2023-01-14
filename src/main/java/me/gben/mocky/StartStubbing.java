package me.gben.mocky;

import java.util.function.Function;
import java.util.function.Supplier;

public class StartStubbing {
    private final Function<InvokeArgument, ?> result;

    public StartStubbing(Supplier<?> result) {
        this.result = (args) -> result.get();
    }

    public <T> StartStubbing(Function<InvokeArgument, T> behavior) {
        this.result = behavior;
    }

    public static <T> StartStubbing doReturn(T value) {
        return new StartStubbing(() -> value);
    }

    public static <T> StartStubbing doAnswer(Function<InvokeArgument, T> behavior) {
        return new StartStubbing(behavior);
    }

    public <U> U when(U value) {
        ((MockyInterceptable)value).getInterceptor().setLatestValue(this.result);
        return value;
    }
}
