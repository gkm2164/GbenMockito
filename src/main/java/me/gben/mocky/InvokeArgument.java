package me.gben.mocky;

import java.util.ArrayList;
import java.util.List;

/**
 * When stubbing with doAnswer, later when the mocked method called,
 * the arguments are wrapped with belows.
 */
public class InvokeArgument {
  private final List<?> thisList;

  public InvokeArgument(List<Object> values) {
    this.thisList = new ArrayList<>(values);
  }

  /**
   * get n th argument.
   *
   * @param n index number.
   * @param <U> type of value to be gotten.
   * @return value.
   */
  public <U> U get(int n) {
    @SuppressWarnings("unchecked")
    U ret = (U) this.thisList.get(n);
    return ret;
  }
}
