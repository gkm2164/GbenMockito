package me.gben.matchers;

import lombok.EqualsAndHashCode;

import java.util.Objects;

@EqualsAndHashCode
public class ExactMatcher<T> implements MatcherDetail<T> {
    private final T value;

    public ExactMatcher(T value) {
        this.value = value;
    }

    @Override
    public boolean test(Object value) {
        return Objects.equals(this.value, value);
    }
}
