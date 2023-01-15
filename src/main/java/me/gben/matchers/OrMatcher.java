package me.gben.matchers;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class OrMatcher<T> implements MatcherDetail<T> {
    private final MatcherDetail<?> left;
    private final MatcherDetail<?> right;

    public OrMatcher(MatcherDetail<?> left, MatcherDetail<?> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean test(Object value) {
        return left.test(value) || right.test(value);
    }
}
