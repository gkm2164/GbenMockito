package me.gben.matchers;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
class LessOrEqualMatcher<T extends Number & Comparable<T>> extends NumericMatcher<T> {
  public LessOrEqualMatcher(T lessThen) {
    super(lessThen);
  }

  @Override
  public boolean compare(T value) {
    return value.compareTo(basis) <= 0;
  }
}
