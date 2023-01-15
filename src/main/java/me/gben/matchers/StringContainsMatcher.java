package me.gben.matchers;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class StringContainsMatcher implements MatcherDetail<String> {
    private final String part;

    public StringContainsMatcher(String part) {
        this.part = part;
    }

    @Override
    public boolean test(Object value) {
        if (!(value instanceof String)) {
            return false;
        }
        return ((String)value).contains(this.part);
    }
}
