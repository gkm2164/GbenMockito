package me.gben.matchers;

public class GreaterOrEqualMatcher<T extends Number & Comparable<T>> extends NumericMatcher<T> {

    public GreaterOrEqualMatcher(T ge) {
        super(ge);
    }

    @Override
    public boolean compare(T value) {
        return value.compareTo(basis) >= 0;
    }
}