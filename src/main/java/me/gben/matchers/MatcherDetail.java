package me.gben.matchers;

/** Declare any matchers within this interfaces.
 *
 * @param <T> the type of value.
 */
public interface MatcherDetail<T> {
  boolean test(Object value);
}
