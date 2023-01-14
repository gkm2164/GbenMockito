package me.gben.functional;

public interface ThrowableFunction<T, R> {
    R apply(T args) throws Throwable;
}
