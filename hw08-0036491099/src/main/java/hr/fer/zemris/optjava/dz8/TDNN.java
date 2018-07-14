package hr.fer.zemris.optjava.dz8;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import hr.fer.zemris.optjava.dz8.api.INeuralNetwork;
import hr.fer.zemris.optjava.dz8.api.IReadOnlyDataset;
import hr.fer.zemris.optjava.dz8.api.ITransferFunction;

public class TDNN implements INeuralNetwork {

	private int[] layers;
	private ITransferFunction[] functions;
	private IReadOnlyDataset dataset;

	public TDNN(int[] layers, ITransferFunction[] functions, IReadOnlyDataset dataset) {
		this.layers = layers;
		this.functions = functions;
		this.dataset = dataset;

		if ((layers.length - 1) != functions.length) {
			throw new RuntimeException("Layers and functions don't match");
		}
	}

	public int getWeightsCount() {
		int weightsCount = 0;

		for (int i = 0; i < layers.length; i++) {
			weightsCount += layers[i];

			if (i != layers.length - 1) {
				weightsCount += layers[i] * layers[i + 1];
			}
		}

		weightsCount -= layers[0];

		return weightsCount;
	}

	public double[] calcOutputs(double[] inputs, double[] weights) {
		if (weights.length != this.getWeightsCount()) {
			throw new RuntimeException("Weights don't match neural network");
		}

		List<Double> nets = new ArrayList<>();
		List<Double> functionValues = DoubleStream.of(inputs).boxed().collect(Collectors.toCollection(ArrayList::new));
		int weightsCounter = 0;

		for (int i = 0; i < layers.length - 1; i++) {
			for (int j = 0; j < layers[i + 1]; j++) {
				double netValue = 0;
				for (Double k : functionValues) {
					netValue += k * weights[weightsCounter];
					weightsCounter++;
				}
				netValue += weights[weightsCounter];
				weightsCounter++;
				nets.add(netValue);
			}
			functionValues.clear();
			for (Double net : nets) {
				functionValues.add(functions[i].valueAt(net.doubleValue()));
			}
			nets.clear();
		}

		double[] outputs = functionValues.stream().mapToDouble(Double::doubleValue).toArray();

		return outputs;
	}

	public double errorFunction(double[] weights) {
		double error = 0;
		double[] outputs = new double[layers[layers.length - 1]];

		for (double[] inputs : dataset.getTestDataset()) {
			outputs = calcOutputs(inputs, weights);
			double[] wantedOutputs = dataset.getOutput(inputs);
			for (int i = 0; i < outputs.length; i++) {
				error += Math.pow(outputs[i] - wantedOutputs[i], 2);
			}

		}
		return error / dataset.numberOfSamples();
	}

	public void statistics(double[] weights) {
		int good = 0;
		int bad = 0;

		double[] outputs = new double[layers[layers.length - 1]];

		for (double[] inputs : dataset.getTestDataset()) {
			outputs = calcOutputs(inputs, weights);

			double[] wantedOutputs = dataset.getOutput(inputs);

			boolean goodOutput = true;
			for (int i = 0; i < outputs.length; i++) {
				if (untransform(outputs[i]) != untransform(wantedOutputs[i])) {
					bad++;
					goodOutput = false;
					break;
				}
			}
			if (goodOutput) {
				good++;
			}

			System.out.printf("UZORAK: ");
			for (int i = 0; i < inputs.length; i++) {
				System.out.printf(untransform(inputs[i]) + " ");
			}
			System.out.printf("DOBIVENI IZLAZ: ");
			for (int i = 0; i < outputs.length; i++) {
				System.out.printf(untransform(outputs[i]) + " ");
			}
			System.out.printf("TRAŽENI IZLAZ: ");
			for (int i = 0; i < outputs.length; i++) {
				System.out.printf(untransform(wantedOutputs[i]) + " ");
			}
			System.out.println();
		}
		System.out.println("DOBRIH: " + good);
		System.out.println("LOŠIH: " + bad);
	}

	private int untransform(double x) {
		int min = dataset.getMin();
		int max = dataset.getMax();

		return (int) (min + (x + 1) * (max - min) / 2);
	}

}
