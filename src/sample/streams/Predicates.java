package sample.streams;

import java.util.function.Predicate;

/**
 * Created by e.rosas.garcia on 18/11/2016.
 */
public class Predicates {
    public static Predicate<String> not(Predicate<String> p) {
        return s -> !p.test(s);
    }
}
