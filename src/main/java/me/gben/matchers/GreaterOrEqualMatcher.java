package me.gben.matchers;

public class GreaterOrEqualMatcher<U extends Number & Comparable> implements MatcherDetail<U> {
    private final U basis;

    public GreaterOrEqualMatcher(U lessThen) {
        this.basis = lessThen;
    }

    @Override
    public boolean test(U value) {
        if (value == null) {
            return false;
        }

        return value.compareTo(basis) >= 0;
    }
}