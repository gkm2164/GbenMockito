package me.gben.mocky;

import me.gben.functional.ThrowableFunction;
import me.gben.matchers.MatcherDetail;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Objects;

import static me.gben.functional.GbenStream.gbenStream;
import static me.gben.functional.Pair.func;

public class OnGoingStubbing<T> {
    private final Class<?> attachedClass;
    private final Method invokeMethod;
    private final MatcherDetail<?>[] matcherDetails;
    private Instant latestTimestamp;
    private ThrowableFunction<InvokeArgument, Object> result;

    public OnGoingStubbing(
            Class<?> attachedClass,
            Method invokeMethod,
            MatcherDetail<?>[] matcherDetails
    ) {
        this.invokeMethod = invokeMethod;
        this.matcherDetails = matcherDetails;
        this.attachedClass = attachedClass;
        this.latestTimestamp = Instant.now();
    }

    public boolean testParams(Object[] arguments) {
        return gbenStream(matcherDetails).zip(gbenStream(arguments))
                .allMatch(func(MatcherDetail::test));
    }

    @SuppressWarnings("unchecked")
    public void thenAnswer(ThrowableFunction<InvokeArgument, ?> supplier) {
        this.result = (ThrowableFunction<InvokeArgument, Object>) supplier;
    }

    public void thenReturn(T t) {
        this.result = (args) -> t;
    }

    ThrowableFunction<InvokeArgument, Object> getResult() {
        return this.result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OnGoingStubbing<?> that = (OnGoingStubbing<?>) o;
        return Objects.equals(attachedClass, that.attachedClass)
                && Objects.equals(invokeMethod, that.invokeMethod);
    }

    public Instant getLatestTimestamp() {
        return latestTimestamp;
    }

    public void touch() {
        this.latestTimestamp = Instant.now();
    }

    public MatcherDetail<?>[] getMatchers() {
        return matcherDetails;
    }
}
