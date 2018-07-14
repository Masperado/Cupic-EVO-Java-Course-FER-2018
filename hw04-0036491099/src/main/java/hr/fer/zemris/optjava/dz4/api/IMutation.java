package hr.fer.zemris.optjava.dz4.api;

public interface IMutation<T> {

	T[] mutate(T[] kids);
}
