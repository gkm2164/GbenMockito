package me.gben.matchers;

import lombok.EqualsAndHashCode;

/**
 * Matchers for any values.
 *
 * @param <T> type of value
 */
@EqualsAndHashCode
public class AnyMatcher<T> implements MatcherDetail<T> {
  @Override
  public boolean test(Object value) {
    return true;
  }
}
