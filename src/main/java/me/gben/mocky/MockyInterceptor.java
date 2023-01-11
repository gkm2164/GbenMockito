package me.gben.mocky;

import me.gben.matchers.MatcherDetail;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

public class MockyInterceptor {
    private final OnGoingStubbingPool onGoingStubbingPool;
    private Supplier<Object> latestValue;

    public MockyInterceptor(OnGoingStubbingPool onGoingStubbingPool) {
        this.onGoingStubbingPool = onGoingStubbingPool;
    }

    public Object invoke(Object mock,
                         Method invokedMethod,
                         Object[] arguments,
                         MatcherDetail[] matchers) {
        String methodName = invokedMethod.getName();
        OnGoingStubbing<?> onGoingStubbing = new OnGoingStubbing<>(
                methodName,
                arguments,
                mock.getClass(),
                matchers);

        Optional<OnGoingStubbing<?>> output = onGoingStubbingPool.stream()
                .filter(onGoingStubbing::equals)
                .filter(id -> Arrays.equals(matchers, id.getMatchers()) || id.testParams(arguments))
                .findFirst();

        if (latestValue != null) {
            onGoingStubbing.thenAnswer(latestValue);
            output.ifPresent(stub -> stub.thenAnswer(latestValue));
            this.latestValue = null;
        }

        output.ifPresent(OnGoingStubbing::touch);

        return output.map(OnGoingStubbing::getResult)
                .map(Supplier::get)
                .orElseGet(() -> {
                    onGoingStubbingPool.push(onGoingStubbing);
                    return invokedMethod.getDefaultValue();
                });
    }

    public void setLatestValue(Supplier<Object> latestValue) {
        this.latestValue = latestValue;
    }
}
