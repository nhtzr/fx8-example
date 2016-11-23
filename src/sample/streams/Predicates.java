package sample.streams;

import java.util.function.Predicate;

/**
 * Created by e.rosas.garcia on 18/11/2016.
 */
public class Predicates {
    public static <T> Predicate<T> not(Predicate<T> p) {
        return s -> !p.test(s);
    }
}
