package me.gben.matchers;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
class GreaterThanMatcher<T extends Number & Comparable<T>> extends NumericMatcher<T> {
  public GreaterThanMatcher(T gt) {
    super(gt);
  }

  @Override
  public boolean compare(T value) {
    return value.compareTo(basis) > 0;
  }
}
