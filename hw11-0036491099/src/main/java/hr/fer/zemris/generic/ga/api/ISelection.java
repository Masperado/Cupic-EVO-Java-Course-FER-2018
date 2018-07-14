package hr.fer.zemris.generic.ga.api;

import java.util.List;

public interface ISelection<T> {

    T[] select(List<T> population);

}
