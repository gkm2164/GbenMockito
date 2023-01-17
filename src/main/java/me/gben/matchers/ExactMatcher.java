package me.gben.matchers;

import java.util.Objects;
import lombok.EqualsAndHashCode;

/**
 * Match with exact value. When no `eq` or other matcher isn't given, default calls to here.
 *
 * @param <T> type of value
 */
@EqualsAndHashCode
public class ExactMatcher<T> implements MatcherDetail<T> {
  private final T value;

  public ExactMatcher(T value) {
    this.value = value;
  }

  @Override
  public boolean test(Object value) {
    return Objects.equals(this.value, value);
  }
}
