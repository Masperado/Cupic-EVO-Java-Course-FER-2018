package hr.fer.zemris.optjava.rng.rngimpl;

import hr.fer.zemris.optjava.rng.IRNG;

import java.util.Random;

public class RNGRandomImpl implements IRNG {

    private Random random;

    public RNGRandomImpl() {
        this.random = new Random();
    }

    @Override
    public double nextDouble() {
        return random.nextDouble();
    }

    @Override
    public double nextDouble(double min, double max) {
        return min + random.nextDouble() * (max - min);
    }

    @Override
    public float nextFloat() {
        return random.nextFloat();
    }

    @Override
    public float nextFloat(float min, float max) {
        return min + random.nextFloat() * (max - min);
    }

    @Override
    public int nextInt() {
        return random.nextInt();
    }

    @Override
    public int nextInt(int min, int max) {
        return min + random.nextInt(max - min);
    }

    @Override
    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    @Override
    public double nextGaussian() {
        return random.nextGaussian();
    }
}
