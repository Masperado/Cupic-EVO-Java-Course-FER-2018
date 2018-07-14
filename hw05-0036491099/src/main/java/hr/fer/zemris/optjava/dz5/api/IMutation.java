package hr.fer.zemris.optjava.dz5.api;

public interface IMutation<T> {

	T[] mutate(T[] kids);
}
