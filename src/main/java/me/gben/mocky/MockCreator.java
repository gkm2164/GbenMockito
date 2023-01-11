package me.gben.mocky;

public interface MockCreator {
    <T> T createMock(Class<T> mockTargetClass, OnGoingStubbingPool onGoingStubbingPool);

}
