package hr.fer.zemris.generic.ga.api;

public interface ICrossing<T> {

    T[] cross(T parent1, T parent2);

}
