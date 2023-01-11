package me.gben.mocky;

public class RealMocky {
    private final OnGoingStubbingPool onGoingStubbingPool = new OnGoingStubbingPool();
    private final MockCreator mockCreator = new ByteBuddyMockCreator();

    public <T> T mock(Class<T> mockTargetClass) {
        return mockCreator.createMock(mockTargetClass, onGoingStubbingPool);
    }

    public <T> OnGoingStubbing<T> when(T methodCall) {
        return onGoingStubbingPool.peek();
    }
}
