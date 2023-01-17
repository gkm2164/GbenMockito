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
  public static <T, U, V> T or(U left, V right) {
    MatcherDetail<U> leftMatcher = recordedMatcher.pop();
    MatcherDetail<V> rightMatcher = recordedMatcher.pop();
    recordedMatcher.push(new OrMatcher<>(leftMatcher, rightMatcher));
    return null;
  }

  @SuppressWarnings("unused")
  public static <T, U> T not(U toNegate) {
    MatcherDetail<U> matcher = recordedMatcher.pop();
    recordedMatcher.push(new NotMatcher<>(matcher));
    return null;
  }

  @SuppressWarnings("unused")
  public static <T, U, V> T and(U left, V right) {
    MatcherDetail<U> leftMatcher =  recordedMatcher.pop();
    MatcherDetail<V> rightMatcher = recordedMatcher.pop();
    recordedMatcher.push(new AndMatcher<>(leftMatcher, rightMatcher));
    return null;
  }

  public static <T> T eq(T value) {
    recordedMatcher.push(new EqualsMatcher<>(value));
    return null;
  }

  public static <T> T any(Class<T> clazz) {
    recordedMatcher.push(new AnyValuesWithinTypeMatcher<>(clazz));
    return null;
  }
}
