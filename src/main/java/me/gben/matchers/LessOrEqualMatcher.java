package me.gben.matchers;

public class LessOrEqualMatcher<U extends Number & Comparable> implements MatcherDetail<U> {
    private final U basis;

    public LessOrEqualMatcher(U lessThen) {
        this.basis = lessThen;
    }

    @Override
    public boolean test(U value) {
        if (value == null) {
            return false;
        }

        return value.compareTo(basis) <= 0;
    }
}