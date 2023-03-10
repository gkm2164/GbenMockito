package me.gben.matchers;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
class EqualsMatcher<T> implements MatcherDetail<T> {
  private final T value;

  public EqualsMatcher(T value) {
    this.value = value;
  }

  @Override
  public boolean test(Object value) {
    return this.value.equals(value);
  }
}
