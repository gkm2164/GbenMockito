package me.gben.matchers;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AnyValueWithinClassMatcher<T> implements MatcherDetail<T> {
    private final Class<T> clazz;

    public AnyValueWithinClassMatcher(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean test(T value) {
        return value != null && this.clazz.isAssignableFrom(value.getClass());
    }
}
