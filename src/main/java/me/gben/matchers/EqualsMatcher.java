package me.gben.matchers;

import lombok.EqualsAndHashCode;

/**
 * Match with exact value.
 *
 * @param <T> type of value
 */
@EqualsAndHashCode
public class EqualsMatcher<T> implements MatcherDetail<T> {
  private final T value;

  public EqualsMatcher(T value) {
    this.value = value;
  }
  @Override
  public boolean test(Object value) {
    return this.value.equals(value);
  }
}
