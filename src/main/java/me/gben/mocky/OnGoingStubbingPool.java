package me.gben.mocky;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Stream;

public class OnGoingStubbingPool {
    private final LinkedList<OnGoingStubbing<?>> onGoingStubbingLinkedList = new LinkedList<>();

    public <T> OnGoingStubbing<T> peek() {
        return (OnGoingStubbing<T>) onGoingStubbingLinkedList.stream()
                .max(Comparator.comparing(OnGoingStubbing::getLatestTimestamp))
                .get();
    }

    public Stream<OnGoingStubbing<?>> stream() {
        return onGoingStubbingLinkedList.stream();
    }

    public void push(OnGoingStubbing<?> onGoingStubbing) {
        onGoingStubbingLinkedList.offerLast(onGoingStubbing);
    }
}
