package hr.fer.zemris.optjava.rng.rngimpl;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.IRNGProvider;

public class ThreadLocalRNGProvider implements IRNGProvider {

    private ThreadLocal<IRNG> threadLocalRNG = new ThreadLocal<>();

    @Override
    public IRNG getRNG() {
        if (threadLocalRNG.get() == null) {
            threadLocalRNG.set(new RNGRandomImpl());
        }
        return threadLocalRNG.get();
    }
}
