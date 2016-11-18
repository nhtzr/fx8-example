package sample.fx;

/**
 * Created by e.rosas.garcia on 18/11/2016.
 */
public interface Getter<T, S> {
    S invoke(T value);
}
