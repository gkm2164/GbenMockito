package me.gben.mocky;

import me.gben.functional.ThrowableFunction;
import org.objenesis.ObjenesisStd;

import java.util.function.Supplier;

public class StartStubbing {
    private final ThrowableFunction<InvokeArgument, ?> result;

    public StartStubbing(Supplier<?> result) {
        this.result = (args) -> result.get();
    }

    public <T> StartStubbing(ThrowableFunction<InvokeArgument, T> behavior) {
        this.result = behavior;
    }

    public static <T> StartStubbing doReturn(T value) {
        return new StartStubbing(() -> value);
    }

    public static <T> StartStubbing doAnswer(ThrowableFunction<InvokeArgument, T> behavior) {
        return new StartStubbing(behavior);
    }

    public static <T extends Throwable> StartStubbing doThrow(Class<T> cls) {
        ObjenesisStd objenesisStd = new ObjenesisStd();
        T throwable = objenesisStd.newInstance(cls);
        return new StartStubbing((args) -> {
            throw throwable;
        });
    }

    public <U> U when(U value) {
        ((MockyInterceptable)value).getInterceptor().setLatestValue(this.result);
        return value;
    }
}
