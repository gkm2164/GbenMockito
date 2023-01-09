package me.gben.mocky;

import java.util.function.Supplier;

public class StubbingStart<T> {
    private final T result;

    public StubbingStart(T result) {
        this.result = result;
    }

    public static <T> StubbingStart<T> doReturn(T value) {
        return new StubbingStart<>(value);
    }

    public <U> U when(U value) {
        ((MockyInterceptable)value).getInterceptor().setValue(this.result);
        return value;
    }
}
