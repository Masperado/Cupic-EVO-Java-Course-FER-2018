package hr.fer.zemris.optjava.dz8.api;

import java.util.Set;

public interface IReadOnlyDataset {

	public double[] getOutput(double[] input);

	public Set<double[]> getTestDataset();

	public int numberOfSamples();

	int getMin();

	int getMax();
}
