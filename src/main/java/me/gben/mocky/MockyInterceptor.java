package me.gben.mocky;

import java.lang.reflect.Method;
import java.util.Stack;

public class MockyInterceptor {
    private final Stack<InvocationDetail<?>> recordedInvocationDetails;
    private Object value;

    public MockyInterceptor(Stack<InvocationDetail<?>> recordedInvocationDetails) {
        this.recordedInvocationDetails = recordedInvocationDetails;
    }

    public Object invoke(Object mock,
                         Method invokedMethod,
                         Object[] arguments,
                         MatcherDetail[] matchers) {
        String methodName = invokedMethod.getName();
        InvocationDetail<?> invocationDetail = new InvocationDetail<>(
                methodName,
                arguments,
                mock.getClass(),
                matchers,
                value);

        return recordedInvocationDetails.stream()
                .filter(invocationDetail::equals)
                .filter(id -> id.testParams(arguments))
                .findFirst()
                .stream()
                .peek(id -> {
                    if (value != null) {
                        id.setResult(value);
                        this.value = null;
                    }
                })
                .map(InvocationDetail::getResult)
                .findFirst()
                .orElseGet(() -> {
                    recordedInvocationDetails.push(invocationDetail);
                    return invokedMethod.getDefaultValue();
                });
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
