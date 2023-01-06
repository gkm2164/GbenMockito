package me.gben.mocky;

import java.util.Arrays;
import java.util.Objects;

public class InvocationDetail<T> {
    private final Class<?> attachedClass;
    private final String methodName;
    private final Object[] arguments;
    private final MatcherDetail[] matcherDetails;
    private Object result;

    public InvocationDetail(
            String methodName,
            Object[] arguments,
            Class<?> attachedClass,
            MatcherDetail<?>[] matcherDetails
    ) {
        this.methodName = methodName;
        this.arguments = arguments;
        this.matcherDetails = matcherDetails;
        this.attachedClass = attachedClass;
    }

    public boolean testParams(Object[] arguments) {
        for (int i = 0; i < arguments.length; i++) {
            if (!this.matcherDetails[i].test(arguments[i])) {
                return false;
            }
        }
        return true;
    }

    public void thenReturn(T t) {
        this.result = t;
    }

    public Object getResult() {
        return result;
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

    @Override
    public int hashCode() {
        int result = Objects.hash(attachedClass, methodName);
        result = 31 * result + Arrays.hashCode(arguments);
        return result;
    }
}
