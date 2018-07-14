package hr.fer.zemris.optjava.rng.rngimpl;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.IRNGProvider;

public class ThreadBoundRNGProvider implements IRNGProvider {


    @Override
    public IRNG getRNG() {
        return ((EVOThread) Thread.currentThread()).getRNG();
    }
}
