package me.gben.matchers;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
class AnyMatcher<T> implements MatcherDetail<T> {
  @Override
  public boolean test(Object value) {
    return true;
  }
}
