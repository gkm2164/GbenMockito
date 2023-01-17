package me.gben.matchers;

import lombok.EqualsAndHashCode;

/**
 * Use of this matchers is to combine 2 different matchers into 1 single condition.
 *
 * @param <T> value's type
 */
@EqualsAndHashCode
public class AndMatcher<T> implements MatcherDetail<T> {
  private final MatcherDetail<?> left;
  private final MatcherDetail<?> right;

  public AndMatcher(MatcherDetail<?> left, MatcherDetail<?> right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public boolean test(Object value) {
    return left.test(value) && right.test(value);
  }
}
