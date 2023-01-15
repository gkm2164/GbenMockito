package me.gben.matchers;

import java.util.Stack;

public class Matchers {
    public static Stack<MatcherDetail<?>> recordedMatcher = new Stack<>();

    public static <T> T any() {
        recordedMatcher.push(new AnyMatcher<T>());
        return null;
    }

    public static String anyString() {
        recordedMatcher.push(new AnyValuesWithinTypeMatcher<>(String.class));
        return "";
    }

    public static String contains(String part) {
        recordedMatcher.push(new StringContainsMatcher(part));
        return "";
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

    public static <T extends Number & Comparable<T>, U extends Number & Comparable<U>> T gt(U gt) {
        recordedMatcher.push(new GreaterThanMatcher<>(gt));
        return null;
    }

    public static <T> T or(T left, T right) {
        MatcherDetail<T> leftMatcher = (MatcherDetail<T>) recordedMatcher.pop();
        MatcherDetail<T> rightMatcher = (MatcherDetail<T>) recordedMatcher.pop();
        recordedMatcher.push(new OrMatcher<>(leftMatcher, rightMatcher));
        return null;
    }

    public static <T, U> T not(U toNegate) {
        MatcherDetail<T> matcher = (MatcherDetail<T>)recordedMatcher.pop();
        recordedMatcher.push(new NotMatcher<>(matcher));
        return null;
    }

    public static <T> T and(T left, T right) {
        MatcherDetail<T> leftMatcher = (MatcherDetail<T>) recordedMatcher.pop();
        MatcherDetail<T> rightMatcher = (MatcherDetail<T>) recordedMatcher.pop();
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
