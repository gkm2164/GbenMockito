package me.gben.mocky;

interface MockCreator {
  <T> T createMock(Class<T> mockTargetClass, OnGoingStubbingPool onGoingStubbingPool);
}
