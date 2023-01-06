package me.gben.mocky;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;

public class InterceptorDelegate {
    @RuntimeType
    public static Object intercept(
            @This Object mock,
            @FieldValue("interceptor") MockyInterceptor interceptor,
            @Origin Method invokedMethod,
            @AllArguments Object[] arguments) {
        MatcherDetail<?>[] matchers = new MatcherDetail<?>[arguments.length];
        if (Matchers.recordedMatcher.size() >= arguments.length) {
            for (int i = arguments.length - 1; i >= 0; i--) {
                matchers[i] = Matchers.recordedMatcher.pop();
            }
        }
        return interceptor.invoke(mock, invokedMethod, arguments, matchers);
    }
}
