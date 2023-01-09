package me.gben.mocky;

import java.util.function.Predicate;

public class MatcherDetail {
    private final Predicate tester;

    public MatcherDetail(Predicate tester) {
        this.tester = tester;
    }

    public boolean test(Object value) {
        return this.tester.test(value);
    }
}
