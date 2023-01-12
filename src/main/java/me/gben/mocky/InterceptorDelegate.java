package me.gben.mocky;

import me.gben.matchers.ExactMatcher;
import me.gben.matchers.MatcherDetail;
import me.gben.matchers.Matchers;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Arrays;

public class InterceptorDelegate {
    @RuntimeType
    public static Object intercept(
            @This Object mock,
            @FieldValue("interceptor") MockyInterceptor interceptor,
            @Origin Method invokedMethod,
            @AllArguments final Object[] arguments) throws InterruptedException {
        Thread.sleep(1);
        final MatcherDetail<?>[] matchers;
        if (Matchers.recordedMatcher.size() != 0) {
            if (Matchers.recordedMatcher.size() != arguments.length) {
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
