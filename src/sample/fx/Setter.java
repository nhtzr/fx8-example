package sample.fx;

/**
 * Created by e.rosas.garcia on 18/11/2016.
 */
public interface Setter<T, S> {
    void invoke(T value, S newValue);
}
