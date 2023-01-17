package me.gben.mocky;

import java.util.function.Supplier;
import me.gben.functional.ThrowableFunction;
import org.objenesis.ObjenesisStd;

/**
 * Different with general mock mechanism, this is to declare value or behavior first,
 * and then choose where to stub.
 */
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

  /** Defining a stub which throws given exceptions.
   *
   * @param throwableClass type of exception
   * @param <T> type of exceptions
   * @return stubbed object.
   */
  public static <T extends Throwable> StartStubbing doThrow(Class<T> throwableClass) {
    ObjenesisStd objenesisStd = new ObjenesisStd();
    T throwable = objenesisStd.newInstance(throwableClass);
    return new StartStubbing((args) -> {
      throw throwable;
    });
  }

  public <U> U when(U value) {
    ((MockyInterceptable) value).getInterceptor().setLatestValue(this.result);
    return value;
  }
}
