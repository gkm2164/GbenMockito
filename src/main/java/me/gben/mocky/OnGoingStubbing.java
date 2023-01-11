package me.gben.mocky;

import lombok.ToString;
import me.gben.matchers.MatcherDetail;

import java.time.Instant;
import java.util.Objects;
import java.util.function.Supplier;

import static me.gben.functional.GbenStream.gbenStream;
import static me.gben.functional.Pair.func;

@ToString
public class OnGoingStubbing<T> {
    private final Class<?> attachedClass;
    private final String methodName;
    private final Object[] arguments;
    private final MatcherDetail[] matcherDetails;
    private Instant latestTimestamp;
    private Supplier<Object> result;

    public OnGoingStubbing(
            String methodName,
            Object[] arguments,
            Class<?> attachedClass,
            MatcherDetail[] matcherDetails
    ) {
        this.methodName = methodName;
        this.arguments = arguments;
        this.matcherDetails = matcherDetails;
        this.attachedClass = attachedClass;
        this.latestTimestamp = Instant.now();
    }

    public boolean testParams(Object[] arguments) {
        if (this.matcherDetails == null) {
            return gbenStream(arguments).zip(gbenStream(this.arguments))
                    .allMatch(func(Objects::equals));
        }

        return gbenStream(matcherDetails).zip(gbenStream(arguments))
                .allMatch(func(MatcherDetail::test));
    }

    public void thenAnswer(Supplier<Object> supplier) {
        this.result = supplier;
    }

    public void thenReturn(T t) {
        this.result = () -> t;
    }

    Supplier<Object> getResult() {
        return this.result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OnGoingStubbing<?> that = (OnGoingStubbing<?>) o;
        return Objects.equals(attachedClass, that.attachedClass)
                && Objects.equals(methodName, that.methodName)
                && arguments.length == that.arguments.length;
    }

    public Instant getLatestTimestamp() {
        return latestTimestamp;
    }

    public void touch() {
        this.latestTimestamp = Instant.now();
    }

    public MatcherDetail[] getMatchers() {
        return matcherDetails;
    }
}
