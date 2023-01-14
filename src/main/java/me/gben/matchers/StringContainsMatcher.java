package me.gben.matchers;

public class StringContainsMatcher implements MatcherDetail<String> {
    private final String part;

    public StringContainsMatcher(String part) {
        this.part = part;
    }

    @Override
    public boolean test(String value) {
        return value.contains(this.part);
    }
}
