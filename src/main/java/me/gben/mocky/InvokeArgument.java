package me.gben.mocky;

import java.util.ArrayList;
import java.util.List;

public class InvokeArgument {
    private final List<?> thisList;

    public InvokeArgument(List<Object> values) {
        this.thisList = new ArrayList<>(values);
    }

    public <U> U get(int n) {
        return (U)this.thisList.get(n);
    }
}
