package me.gben.matchers;

public interface MatcherDetail<T> {
    boolean test(T value);
}
