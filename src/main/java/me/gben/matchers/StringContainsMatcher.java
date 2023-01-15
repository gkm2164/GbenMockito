package me.gben.matchers;

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
