package me.gben.matchers;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
class LessThanMatcher<T extends Number & Comparable<T>> extends NumericMatcher<T> {
  public LessThanMatcher(T lt) {
    super(lt);
  }

  @Override
  public boolean compare(T value) {
    return value.compareTo(basis) < 0;
  }
}
