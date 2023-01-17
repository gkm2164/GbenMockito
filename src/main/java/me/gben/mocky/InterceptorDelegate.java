package me.gben.mocky;

import java.lang.reflect.Method;
import me.gben.matchers.ExactMatcher;
import me.gben.matchers.MatcherDetail;
import me.gben.matchers.Matchers;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;


/**
 * mocked class's methods will call this anyway.
 */
public class InterceptorDelegate {
  /** intercepting methods. Would be called in runtime.
   *
   * @param mock mocked object.
   * @param interceptor interceptor fields defined by bytebuddy
   * @param invokedMethod a method that is called.
   * @param arguments parameters given.
   * @return return value from intercepted mock.
   * @throws Throwable any exceptions thrown
   */
  @RuntimeType
  public static Object intercept(
      @This Object mock,
      @FieldValue("interceptor") MockyInterceptor interceptor,
      @Origin Method invokedMethod,
      @AllArguments final Object[] arguments) throws Throwable {
    Thread.sleep(1);
    final MatcherDetail<?>[] matchers;
    if (Matchers.recordedMatcher.size() != 0) {
      if (Matchers.recordedMatcher.size() != arguments.length) {
        Matchers.recordedMatcher.clear();
        throw new IllegalStateException("Matchers are not matches with arguments");
      } else {
        matchers = new MatcherDetail[arguments.length];
        for (int i = arguments.length - 1; i >= 0; i--) {
          matchers[i] = Matchers.recordedMatcher.pop();
        }
      }
    } else if (arguments.length != 0) {
      matchers = new MatcherDetail[arguments.length];
      for (int i = 0; i < matchers.length; i++) {
        matchers[i] = new ExactMatcher<>(arguments[i]);
      }
    } else {
      matchers = new MatcherDetail[0];
    }

    return interceptor.invoke(mock, invokedMethod, arguments, matchers);
  }
}
