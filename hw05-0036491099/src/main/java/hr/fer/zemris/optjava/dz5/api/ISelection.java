package hr.fer.zemris.optjava.dz5.api;

public interface ISelection<T> {

	T[] select(T[] population, double[] values);

}
