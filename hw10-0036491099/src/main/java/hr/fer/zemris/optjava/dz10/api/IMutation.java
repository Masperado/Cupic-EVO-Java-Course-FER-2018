package hr.fer.zemris.optjava.dz10.api;

public interface IMutation<T> {

    T[] mutate(T[] kids);
}
