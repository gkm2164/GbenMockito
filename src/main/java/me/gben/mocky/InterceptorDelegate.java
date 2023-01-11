package me.gben.mocky;

import me.gben.matchers.MatcherDetail;
import me.gben.matchers.Matchers;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;

public class InterceptorDelegate {
    @RuntimeType
    public static Object intercept(
            @This Object mock,
            @FieldValue("interceptor") MockyInterceptor interceptor,
            @Origin Method invokedMethod,
            @AllArguments final Object[] arguments) {
        MatcherDetail[] matchers = null;
        if (Matchers.recordedMatcher.size() != 0 && Matchers.recordedMatcher.size() != arguments.length) {
            throw new IllegalStateException("Matchers are not matches with arguments");
        }

        if (Matchers.recordedMatcher.size() == arguments.length) {
            matchers = new MatcherDetail[arguments.length];
            for (int i = arguments.length - 1; i >= 0; i--) {
                matchers[i] = Matchers.recordedMatcher.pop();
            }
        }

        return interceptor.invoke(mock, invokedMethod, arguments, matchers);
    }
}
