package me.gben.matchers;

import org.intellij.lang.annotations.Language;

/**
 * A utility class for aggregating different kind of matchers.
 */
public class Matchers {
  public static RecordedMatcherPool recordedMatcher = new RecordedMatcherPool();

  public static <T> T any() {
    recordedMatcher.push(new AnyMatcher<T>());
    return null;
  }

  public static <T> T any(Class<T> clazz) {
    recordedMatcher.push(new AnyValuesWithinTypeMatcher<>(clazz));
    return null;
  }

  public static String anyString() {
    recordedMatcher.push(new AnyValuesWithinTypeMatcher<>(String.class));
    return null;
  }

  public static String contains(String part) {
    recordedMatcher.push(new StringContainsMatcher(part));
    return null;
  }

  public static String regex(@Language("RegExp") String regex) {
    recordedMatcher.push(new StringRegexMatcher(regex));
    return null;
  }

  public static int anyInteger() {
    recordedMatcher.push(new AnyValuesWithinTypeMatcher<>(Integer.class));
    return 0;
  }

  public static <T extends Number & Comparable<T>> T lt(T lt) {
    recordedMatcher.push(new LessThanMatcher<>(lt));
    return null;
  }

  public static <T extends Number & Comparable<T>> T ge(T ge) {
    recordedMatcher.push(new GreaterOrEqualMatcher<>(ge));
    return null;
  }

  public static <T extends Number & Comparable<T>> T le(T le) {
    recordedMatcher.push(new LessOrEqualMatcher<>(le));
    return null;
  }

  public static <T extends Number & Comparable<T>> T gt(T gt) {
    recordedMatcher.push(new GreaterThanMatcher<>(gt));
    return null;
  }

  /**
   * or matcher definition.
   *
   * @param left used as type hint for left value
   * @param right used as type hint for right value
   * @param <T> type of value
   * @param <U> type of left value
   * @param <V> type of right value
   * @return null
   */
  @SuppressWarnings("unused")
  public static <T> T or(T left, T right) {
    MatcherDetail<T> leftMatcher = recordedMatcher.pop();
    MatcherDetail<T> rightMatcher = recordedMatcher.pop();
    recordedMatcher.push(new OrMatcher<>(leftMatcher, rightMatcher));
    return null;
  }

  /** matchers for not condition.
   * example: not(anyString()) -> other than string.
   *
   * @param toNegate a matcher placeholder to give type hint.
   * @param <T> type of actual value.
   * @param <U> type of negation condition
   * @return null, but, will push a matcher inside the record.
   */
  @SuppressWarnings("unused")
  public static <T, U> T not(U toNegate) {
    MatcherDetail<U> matcher = recordedMatcher.pop();
    recordedMatcher.push(new NotMatcher<>(matcher));
    return null;
  }

  /** and operation for the matchers.
   * example) and(contains("A"), contains("B")) -> matches when the value contains "A" and "B"
   *
   * @param left left side matcher.
   * @param right right side matcher.
   * @param <T> type of the matcher holders
   * @param <U> type of left values.
   * @param <V> type of right values.
   * @return null, but, will push matchers inside recorded matchers.
   */
  @SuppressWarnings("unused")
  public static <T> T and(T left, T right) {
    MatcherDetail<T> leftMatcher =  recordedMatcher.pop();
    MatcherDetail<T> rightMatcher = recordedMatcher.pop();
    recordedMatcher.push(new AndMatcher<>(leftMatcher, rightMatcher));
    return null;
  }

  public static <T> T eq(T value) {
    recordedMatcher.push(new EqualsMatcher<>(value));
    return null;
  }
}
