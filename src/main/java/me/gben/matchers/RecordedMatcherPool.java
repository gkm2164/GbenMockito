package me.gben.matchers;

import java.util.Stack;

public class RecordedMatcherPool {
    private final Stack<MatcherDetail<?>> stack = new Stack<>();

    public <T> void push(MatcherDetail<T> matcherDetail) {
        stack.push(matcherDetail);
    }

    public <T> T pop() {
        @SuppressWarnings("unchecked")
        T ret = (T)stack.pop();
        return ret;
    }

    public int size() {
        return stack.size();
    }

    public void clear() {
        stack.clear();
    }
}
