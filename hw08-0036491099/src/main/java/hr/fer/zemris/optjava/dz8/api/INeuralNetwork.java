package hr.fer.zemris.optjava.dz8.api;

public interface INeuralNetwork {

	public int getWeightsCount();

	public double[] calcOutputs(double[] inputs, double[] weights);

	public double errorFunction(double[] weights);

	public void statistics(double[] weights);
}
