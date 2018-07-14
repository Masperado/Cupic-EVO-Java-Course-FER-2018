package hr.fer.zemris.optjava.dz7.api;

import java.util.Set;

public interface IReadOnlyDataset {

	public int[] getOutput(double[] input);

	public Set<double[]> getTestDataset();

	public int numberOfSamples();
}
