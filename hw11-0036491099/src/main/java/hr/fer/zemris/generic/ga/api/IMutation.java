package hr.fer.zemris.generic.ga.api;

public interface IMutation<T> {

    T[] mutate(T[] kids);
}
