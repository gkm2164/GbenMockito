package me.gben.matchers;

import java.util.Stack;

/** records that keep track of matchers call.
 *
 */
public class RecordedMatcherPool {
  private final Stack<MatcherDetail<?>> stack = new Stack<>();

  public <T> void push(MatcherDetail<T> matcherDetail) {
    stack.push(matcherDetail);
  }

  /**
   * get recent pushed matcher.
   *
   * @param <T> type of values to be tested.
   * @return matcher that pushed lately
   */
  public <T> MatcherDetail<T> pop() {
    @SuppressWarnings("unchecked")
    MatcherDetail<T> ret = (MatcherDetail<T>) stack.pop();
    return ret;
  }

  public int size() {
    return stack.size();
  }

  public void clear() {
    stack.clear();
  }
}
