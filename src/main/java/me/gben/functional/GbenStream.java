package me.gben.functional;

import lombok.experimental.Delegate;

import java.util.Iterator;
import java.util.stream.Stream;

public class GbenStream<T> implements Stream<T> {
    @Delegate
    private final Stream<T> stream;

    private GbenStream(Stream<T> stream) {
        this.stream = stream;
    }

    public <U> GbenStream<Pair<T, U>> zip(GbenStream<U> right) {
        Iterator<T> l = this.iterator();
        Iterator<U> r = right.iterator();

        Stream.Builder<Pair<T, U>> builder = Stream.builder();
        while (l.hasNext() && r.hasNext()) {
            builder.add(Pair.of(l.next(), r.next()));
        }

        return GbenStream.of(builder.build());
    }

    public static <T> GbenStream<T> of(Stream<T> stream) {
        return new GbenStream<>(stream);
    }
}