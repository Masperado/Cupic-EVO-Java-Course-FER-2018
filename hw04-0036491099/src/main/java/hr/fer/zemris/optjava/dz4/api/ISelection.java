package hr.fer.zemris.optjava.dz4.api;

public interface ISelection<T> {

	T[] select(T[] population, double[] values);

}
