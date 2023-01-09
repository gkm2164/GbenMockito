package me.gben.functional;

import lombok.Data;

@Data
public class Pair<T, U> {
    private final T left;
    private final U right;

    public static <T, U> Pair<T, U> of(T left, U right) {
        return new Pair<>(left, right);
    }
}
