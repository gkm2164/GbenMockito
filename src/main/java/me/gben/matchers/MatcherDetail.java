package me.gben.matchers;

/**
 *
 * @param <T>
 */
public interface MatcherDetail<T> {
  boolean test(Object value);
}
