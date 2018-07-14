package hr.fer.zemris.generic.ga.api;

public interface IGAEvaluator<T> {
    void evaluate(GASolution<T> p);
}