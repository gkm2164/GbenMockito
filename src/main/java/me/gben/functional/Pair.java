package me.gben.functional;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import lombok.Data;

/**
 * Redefinition of Pair.
 *
 * @param <T> left element's type
 * @param <U> right element's type
 */
@Data
public class Pair<T, U> {
  private final T left;
  private final U right;

  public static <T1, U1> Predicate<Pair<T1, U1>> func(BiPredicate<T1, U1> func) {
    return (pair) -> func.test(pair.left, pair.right);
  }

  public static <T, U> Pair<T, U> of(T left, U right) {
    return new Pair<>(left, right);
  }
}
