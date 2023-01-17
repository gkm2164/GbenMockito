package me.gben.matchers;

import lombok.EqualsAndHashCode;

/**
 * Numeric matcher for Greater or Equals.
 *
 * @param <T> type of numeric value
 */
@EqualsAndHashCode(callSuper = false)
public class LessThanMatcher<T extends Number & Comparable<T>> extends NumericMatcher<T> {
  public LessThanMatcher(T lt) {
    super(lt);
  }

  @Override
  public boolean compare(T value) {
    return value.compareTo(basis) < 0;
  }
}
