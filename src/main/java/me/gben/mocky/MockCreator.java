package me.gben.mocky;

import java.util.Stack;

public interface MockCreator {
    <T> T createMock(Class<T> mockTargetClass, Stack<InvocationDetail<?>> invocationDetailList);

}
