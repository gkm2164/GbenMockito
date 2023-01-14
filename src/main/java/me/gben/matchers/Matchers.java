package me.gben.matchers;

import java.util.Stack;

public class Matchers {
    public static Stack<MatcherDetail> recordedMatcher = new Stack<>();

    public static <T> T any() {
        recordedMatcher.push(new AnyMatcher<T>());
        return null;
    }

    public static String anyString() {
        recordedMatcher.push(new AnyValuesWithinTypeMatcher<>(String.class));
        return "";
    }


    public static int anyInteger() {
        recordedMatcher.push(new AnyValuesWithinTypeMatcher<>(Integer.class));
        return 0;
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
