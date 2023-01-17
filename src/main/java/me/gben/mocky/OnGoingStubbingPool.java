package me.gben.mocky;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Stream;

public class OnGoingStubbingPool {
    private final LinkedList<OnGoingStubbing<?>> onGoingStubbingLinkedList = new LinkedList<>();

    public <T> OnGoingStubbing<T> peek() {
        Optional<OnGoingStubbing<?>> retOpt = onGoingStubbingLinkedList.stream()
                .max(Comparator.comparing(OnGoingStubbing::getLatestTimestamp));

        if (retOpt.isEmpty()) {
            throw new IllegalArgumentException("There's no proper mock going on!");
        }

        @SuppressWarnings("unchecked")
        OnGoingStubbing<T> ret = (OnGoingStubbing<T>) retOpt.get();
        return ret;
    }

    public Stream<OnGoingStubbing<?>> stream() {
        return onGoingStubbingLinkedList.stream();
    }

    public void push(OnGoingStubbing<?> onGoingStubbing) {
        onGoingStubbingLinkedList.offerLast(onGoingStubbing);
    }
}
