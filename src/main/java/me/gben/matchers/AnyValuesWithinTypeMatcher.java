package me.gben.matchers;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
class AnyValuesWithinTypeMatcher<T> implements MatcherDetail<T> {
  private final Class<T> clazz;

  public AnyValuesWithinTypeMatcher(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public boolean test(Object value) {
    return value != null && this.clazz.isAssignableFrom(value.getClass());
  }
}
