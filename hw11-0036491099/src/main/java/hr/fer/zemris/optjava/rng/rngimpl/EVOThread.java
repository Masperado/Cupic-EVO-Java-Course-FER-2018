package hr.fer.zemris.optjava.rng.rngimpl;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.Evaluator;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.IRNGProvider;


public class EVOThread extends Thread implements IRNGProvider {
    private IRNG rng = new RNGRandomImpl();
    private GrayScaleImage image;
    private Evaluator evaluator;

    public EVOThread() {
    }

    public EVOThread(Runnable target) {
        super(target);
    }

    public EVOThread(Runnable target, GrayScaleImage image) {
        super(target);
        this.image = new GrayScaleImage(image.getWidth(), image.getHeight());
        this.evaluator = new Evaluator(image);
    }

    public EVOThread(String name) {
        super(name);
    }

    public EVOThread(ThreadGroup group, Runnable target) {
        super(group, target);
    }

    public EVOThread(ThreadGroup group, String name) {
        super(group, name);
    }

    public EVOThread(Runnable target, String name) {
        super(target, name);
    }

    public EVOThread(ThreadGroup group, Runnable target, String name) {
        super(group, target, name);
    }

    public EVOThread(ThreadGroup group, Runnable target, String name,
                     long stackSize) {
        super(group, target, name, stackSize);
    }

    @Override
    public IRNG getRNG() {
        return rng;
    }

    public GrayScaleImage getImage() {
        return image;
    }

    public Evaluator getEvaluator() {
        return evaluator;
    }
}