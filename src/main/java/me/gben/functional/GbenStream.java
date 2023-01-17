package me.gben.functional;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;
import lombok.experimental.Delegate;

/**
 * A stream wrapper for adding more functional methods.
 *
 * @param <T> type of base element for the stream
 */
public class GbenStream<T> implements Stream<T> {
  @Delegate
  private final Stream<T> stream;

  private GbenStream(Stream<T> stream) {
    this.stream = stream;
  }

  /**
   * Zip operation for streams.
   *
   * @param right to be zipped with
   * @param <U> right side element's type
   * @return zipped result within Pair
   */
  public <U> GbenStream<Pair<T, U>> zip(GbenStream<U> right) {
    Iterator<T> l = this.iterator();
    Iterator<U> r = right.iterator();

    Stream.Builder<Pair<T, U>> builder = Stream.builder();
    while (l.hasNext() && r.hasNext()) {
      builder.add(Pair.of(l.next(), r.next()));
    }

    return GbenStream.gbenStream(builder.build());
  }

  public static <T> GbenStream<T> gbenStream(T[] arrs) {
    return gbenStream(Arrays.stream(arrs));
  }

  public static <T> GbenStream<T> gbenStream(Stream<T> stream) {
    return new GbenStream<>(stream);
  }
}
