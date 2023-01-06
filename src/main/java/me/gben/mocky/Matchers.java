package me.gben.mocky;

import java.util.Stack;

public class Matchers {
    static Stack<MatcherDetail<?>> recordedMatcher = new Stack<>();
    public static <T> T any() {
        recordedMatcher.push(new MatcherDetail<T>((t) -> true));
        return null;
    }

    public static <T> T eq(T value) {
        recordedMatcher.push(new MatcherDetail<>(value::equals));
        return null;
    }

    public static <T> T any(Class<T> clazz) {
        recordedMatcher.push(new MatcherDetail<>(v -> v != null && clazz.isAssignableFrom(v.getClass())));
        return null;
    }
}
