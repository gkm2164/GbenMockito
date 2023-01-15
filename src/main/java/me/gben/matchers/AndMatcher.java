package me.gben.matchers;

public class AndMatcher<T> implements MatcherDetail<T> {
    private final MatcherDetail<T> left;
    private final MatcherDetail<T> right;

    public AndMatcher(MatcherDetail<T> left, MatcherDetail<T> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean test(Object value) {
        return left.test(value) && right.test(value);
    }
}
