package hr.fer.zemris.optjava.dz9.api;

public interface IMutation<T> {

    T[] mutate(T[] kids);
}
