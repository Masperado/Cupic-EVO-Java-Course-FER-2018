package hr.fer.zemris.optjava.dz10.api;

import java.util.List;

public interface ISelection<T> {

    T[] select(List<T> population);

}
