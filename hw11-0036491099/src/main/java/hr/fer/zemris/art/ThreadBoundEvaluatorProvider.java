package hr.fer.zemris.art;


import hr.fer.zemris.generic.ga.Evaluator;
import hr.fer.zemris.optjava.rng.rngimpl.EVOThread;

public class ThreadBoundEvaluatorProvider implements EvaluatorProvider {

    @Override
    public Evaluator getEvaluator() {
        return ((EVOThread) Thread.currentThread()).getEvaluator();
    }
}
