package me.gben.matchers;

import lombok.EqualsAndHashCode;

/**
 * Numeric matcher for Greater or Equals.
 *
 * @param <T> type of numeric value
 */
@EqualsAndHashCode(callSuper = false)
public class GreaterThanMatcher<T extends Number & Comparable<T>> extends NumericMatcher<T> {
    public GreaterThanMatcher(T gt) {
        super(gt);
    }

    @Override
    public boolean compare(T value) {
        return value.compareTo(basis) > 0;
    }
}