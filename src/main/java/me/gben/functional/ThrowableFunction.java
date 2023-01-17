package me.gben.functional;

/**
 * Function type that can throw any exceptions.
 *
 * @param <T> argument types
 * @param <R> return types
 */
public interface ThrowableFunction<T, R> {
  R apply(T args) throws Throwable;
}
