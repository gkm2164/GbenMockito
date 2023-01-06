package me.gben.mocky;

import java.util.function.Predicate;

public class MatcherDetail<T> {
    private final Predicate<T> tester;

    public MatcherDetail(Predicate<T> tester) {
        this.tester = tester;
    }

    public boolean test(T value) {
        return this.tester.test(value);
    }
}
