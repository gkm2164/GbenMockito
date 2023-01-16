package me.gben.mocky;

import me.gben.functional.ThrowableFunction;
import me.gben.matchers.MatcherDetail;
import org.objenesis.ObjenesisStd;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MockyInterceptor {
    private final OnGoingStubbingPool onGoingStubbingPool;
    private ThrowableFunction<InvokeArgument, ?> latestValue;

    public MockyInterceptor(OnGoingStubbingPool onGoingStubbingPool) {
        this.onGoingStubbingPool = onGoingStubbingPool;
    }

    public Object invoke(Object mock,
                         Method invokedMethod,
                         Object[] arguments,
                         MatcherDetail<?>[] matchers) throws Throwable {
        OnGoingStubbing<?> onGoingStubbing = new OnGoingStubbing<>(
                mock.getClass(),
                invokedMethod,
                matchers);

        List<OnGoingStubbing<?>> outputList = onGoingStubbingPool.stream()
                .filter(onGoingStubbing::equals)
                .filter(id -> Arrays.equals(matchers, id.getMatchers()) || id.testParams(arguments))
                .collect(Collectors.toList());

        Optional<OnGoingStubbing<?>> output = outputList.stream().findFirst();

        if (latestValue != null) {
            final ThrowableFunction<InvokeArgument, ?> value = latestValue;
            onGoingStubbing.thenAnswer(value);
            output.ifPresent(stub -> stub.thenAnswer(value));
            this.latestValue = null;
        }

        output.ifPresent(OnGoingStubbing::touch);

        try {
            return output.map(OnGoingStubbing::getResult)
                    .map(result -> {
                        try {
                            return result.apply(new InvokeArgument(Arrays.asList(arguments)));
                        } catch (Throwable e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .orElseGet(() -> {
                        onGoingStubbingPool.push(onGoingStubbing);
                        Class<?> cls = invokedMethod.getReturnType();
                        if (cls.isPrimitive()) {
                            if (boolean.class.isAssignableFrom(cls)) {
                                return false;
                            } else if (void.class.isAssignableFrom(cls)) {
                                return null;
                            }
                        }
                        return new ObjenesisStd().newInstance(invokedMethod.getReturnType());
                    });
        } catch (RuntimeException e) {
            if (e.getCause() != null) {
                throw e.getCause();
            }
            throw e;
        }
    }

    public void setLatestValue(ThrowableFunction<InvokeArgument, ?> latestValue) {
        this.latestValue = latestValue;
    }
}
