package me.gben.matchers;

import lombok.EqualsAndHashCode;

/**
 * this is to restrict any matchers with specific types.
 *
 * @param <T> type of actual value.
 */
@EqualsAndHashCode
public class AnyValuesWithinTypeMatcher<T> implements MatcherDetail<T> {
  private final Class<T> clazz;

  public AnyValuesWithinTypeMatcher(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public boolean test(Object value) {
    return value != null && this.clazz.isAssignableFrom(value.getClass());
  }
}
