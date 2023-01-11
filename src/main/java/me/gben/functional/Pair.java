package me.gben.functional;

import lombok.Data;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

@Data
public class Pair<T, U> {
    private final T left;
    private final U right;

    public static <T1, U1> Predicate<Pair<T1, U1>> func(BiPredicate<T1, U1> func) {
        return (pair) -> func.test(pair.left, pair.right);
    }
    public static <T, U> Pair<T, U> of(T left, U right) {
        return new Pair<>(left, right);
    }
}
