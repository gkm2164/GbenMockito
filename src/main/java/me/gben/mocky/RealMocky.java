package me.gben.mocky;

import java.util.Stack;

public class RealMocky {
    private final Stack<InvocationDetail<?>> invocationDetailList = new Stack<>();

    private final MockCreator mockCreator = new ByteBuddyMockCreator();
    public <T> T mock(Class<T> mockTargetClass) {
        return mockCreator.createMock(mockTargetClass, invocationDetailList);
    }

    public <T> InvocationDetail<T> when(T methodCall) {
        @SuppressWarnings("unchecked")
        InvocationDetail<T> ret = (InvocationDetail<T>) invocationDetailList.peek();
        return ret;
    }
}
