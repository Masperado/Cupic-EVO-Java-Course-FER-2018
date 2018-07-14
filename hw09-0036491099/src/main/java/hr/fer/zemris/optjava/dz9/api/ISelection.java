package hr.fer.zemris.optjava.dz9.api;

import java.util.List;

public interface ISelection<T> {

    T[] select(List<T> population);

}
