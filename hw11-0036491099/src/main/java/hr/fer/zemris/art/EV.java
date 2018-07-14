package hr.fer.zemris.art;

import hr.fer.zemris.generic.ga.Evaluator;

public class EV {
    private static EvaluatorProvider provider;


    static {
        EV.provider = new ThreadBoundEvaluatorProvider();
    }

    public static Evaluator getEvaluator() {
        return provider.getEvaluator();
    }
}
