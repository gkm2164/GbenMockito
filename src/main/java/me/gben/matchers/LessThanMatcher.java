package me.gben.matchers;

public class LessThanMatcher<U extends Number & Comparable> implements MatcherDetail<U> {
    private final U basis;

    public LessThanMatcher(U lessThan) {
        this.basis = lessThan;
    }

    @Override
    public boolean test(U value) {
        if (value == null) {
            return false;
        }
        return value.compareTo(basis) < 0;
    }
}
