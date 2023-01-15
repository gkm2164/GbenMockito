package me.gben.matchers;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class NotMatcher<T> implements MatcherDetail<T> {
    private final MatcherDetail<T> matcher;

    public NotMatcher(MatcherDetail<T> matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean test(Object value) {
        return !matcher.test(value);
    }
}
