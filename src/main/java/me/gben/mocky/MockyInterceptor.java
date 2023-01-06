package me.gben.mocky;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Stack;

public class MockyInterceptor {
    private final Stack<InvocationDetail<?>> recordedInvocationDetails;

    public MockyInterceptor(Stack<InvocationDetail<?>> recordedInvocationDetails) {
        this.recordedInvocationDetails = recordedInvocationDetails;
    }

    public Object invoke(Object mock, Method invokedMethod, Object[] arguments, MatcherDetail<?>[] matchers) {
        String methodName = invokedMethod.getName();

        InvocationDetail<?> invocationDetail = new InvocationDetail<>(methodName, arguments, mock.getClass(), matchers);
        return recordedInvocationDetails.stream()
                .filter(invocationDetail::equals)
                .filter(id -> id.testParams(arguments))
                .findFirst()
                .map(InvocationDetail::getResult)
                .orElseGet(() -> {
                    recordedInvocationDetails.push(invocationDetail);
                    return invokedMethod.getDefaultValue();
                });
    }
}
