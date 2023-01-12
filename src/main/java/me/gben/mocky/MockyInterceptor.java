package me.gben.mocky;

import me.gben.matchers.MatcherDetail;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MockyInterceptor {
    private final OnGoingStubbingPool onGoingStubbingPool;
    private Supplier<?> latestValue;

    public MockyInterceptor(OnGoingStubbingPool onGoingStubbingPool) {
        this.onGoingStubbingPool = onGoingStubbingPool;
    }

    public Object invoke(Object mock,
                         Method invokedMethod,
                         Object[] arguments,
                         MatcherDetail<?>[] matchers) {
        String methodName = invokedMethod.getName();
        OnGoingStubbing<?> onGoingStubbing = new OnGoingStubbing<>(
                methodName,
                arguments,
                mock.getClass(),
                matchers);

        List<OnGoingStubbing<?>> outputList = onGoingStubbingPool.stream()
                .filter(onGoingStubbing::equals)
                .filter(id -> Arrays.equals(matchers, id.getMatchers()) || id.testParams(arguments))
                .collect(Collectors.toList());

        Optional<OnGoingStubbing<?>> output = outputList.stream().findFirst();

        if (latestValue != null) {
            final Supplier<?> value = latestValue;
            onGoingStubbing.thenAnswer(value::get);
            output.ifPresent(stub -> stub.thenAnswer(value::get));
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

    public void setLatestValue(Supplier<?> latestValue) {
        this.latestValue = latestValue;
    }
}
