package hr.fer.zemris.optjava.dz3;

import java.util.Random;

public class DoubleArraySolution extends SingleObjectiveSolution {

	private double[] values;

	public DoubleArraySolution(double[] values) {
		this.values = values;
	}

	public void randomize(Random rand, double d, double e) {
		for (int i = 0; i < values.length; i++) {
			values[i] += d + Math.random() * (-d + e);
		}

	}

	public DoubleArraySolution duplicate() {
		return new DoubleArraySolution(values.clone());
	}

	public double[] getValues() {
		return values;
	}

	@Override
	public void calculateFitness(IDecoder decoder, IFunction function) {
		this.fitness = function.valueAt(decoder.decode(this));
	}

	@Override
	public String stringRepresentation(IDecoder decoder) {
		StringBuilder sb = new StringBuilder();
		sb.append("A = " + values[0] + "\n");
		sb.append("B = " + values[1] + "\n");
		sb.append("C = " + values[2] + "\n");
		sb.append("D = " + values[3] + "\n");
		sb.append("E = " + values[4] + "\n");
		sb.append("F = " + values[5] + "\n");

		return sb.toString();
	}


}
