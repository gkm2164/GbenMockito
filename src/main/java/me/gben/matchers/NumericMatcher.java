package me.gben.matchers;

abstract class NumericMatcher<T extends Number & Comparable<T>> implements MatcherDetail<T> {
  protected final T basis;

  public NumericMatcher(T basis) {
    this.basis = basis;
  }

  @Override
  public final boolean test(Object value) {
    if (value == null) {
      return false;
    }

    if (!basis.getClass().isAssignableFrom(value.getClass())) {
      return false;
    }

    @SuppressWarnings("unchecked")
    T val = (T) value;
    return compare(val);
  }

  public abstract boolean compare(T value);
}
