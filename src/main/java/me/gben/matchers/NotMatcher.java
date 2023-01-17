package me.gben.matchers;

import lombok.EqualsAndHashCode;

/** not matcher declaration.
 * Use with `Matchers.not()`.
 *
 * @param <T> type of values for the placeholder.
 */
@EqualsAndHashCode
public class NotMatcher<T> implements MatcherDetail<T> {
  private final MatcherDetail<T> matcher;

  public NotMatcher(MatcherDetail<T> matcher) {
    this.matcher = matcher;
  }

  @Override
  public boolean test(Object value) {
    return !matcher.test(value);
  }
}
