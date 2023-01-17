package me.gben.matchers;

import lombok.EqualsAndHashCode;

/**
 * Numeric matcher for Greater or Equals.
 *
 * @param <T> type of numeric value
 */
@EqualsAndHashCode(callSuper = false)
public class GreaterOrEqualMatcher<T extends Number & Comparable<T>> extends NumericMatcher<T> {

  public GreaterOrEqualMatcher(T ge) {
    super(ge);
  }

  @Override
  public boolean compare(T value) {
    return value.compareTo(basis) >= 0;
  }
}