package me.gben.mocky;

import me.gben.functional.GbenStream;

import java.util.Arrays;
import java.util.Objects;

public class InvocationDetail<T> {
    private final Class<?> attachedClass;
    private final String methodName;
    private final Object[] arguments;
    private final MatcherDetail[] matcherDetails;
    private T result;

    public InvocationDetail(
            String methodName,
            Object[] arguments,
            Class<?> attachedClass,
            MatcherDetail[] matcherDetails
    ) {
        this.methodName = methodName;
        this.arguments = arguments;
        this.matcherDetails = matcherDetails;
        this.attachedClass = attachedClass;
    }

    public InvocationDetail(
            String methodName,
            Object[] arguments,
            Class<?> attachedClass,
            MatcherDetail[] matcherDetails,
            T result
    ) {
        this.methodName = methodName;
        this.arguments = arguments;
        this.matcherDetails = matcherDetails;
        this.attachedClass = attachedClass;
        this.result = result;
    }

    public boolean testParams(Object[] arguments) {
        assert arguments.length == this.matcherDetails.length;

        GbenStream<MatcherDetail> matchers = GbenStream.of(Arrays.stream(matcherDetails));
        GbenStream<Object> argsStream = GbenStream.of(Arrays.stream(arguments));

        return matchers.zip(argsStream)
                .allMatch(pair -> {
            final MatcherDetail left = pair.getLeft();
            final Object arg = pair.getRight();
            return left.test(arg);
        });
    }

    public void thenReturn(T t) {
        this.result = t;
    }

    public Object getResult() {
        return this.result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvocationDetail<?> that = (InvocationDetail<?>) o;
        return Objects.equals(attachedClass, that.attachedClass)
                && Objects.equals(methodName, that.methodName)
                && Objects.equals(arguments.length, that.arguments.length);
    }

    public void setResult(Object v) {
        this.result = (T) v;
    }
}
