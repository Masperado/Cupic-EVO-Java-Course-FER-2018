package hr.fer.zemris.optjava.dz4.impl;

import hr.fer.zemris.optjava.dz4.api.ISelection;

public class RouletteWheel implements ISelection<double[]> {

	@Override
	public double[][] select(double[][] population, double[] values) {
		double[] parent1 = selectParent(population, values);
		double[] parent2 = selectParent(population, values);

		while (sum(parent1) == sum(parent2)) {
			parent2 = selectParent(population, values);
		}

		return new double[][] { parent1, parent2 };
	}

	public double[] selectParent(double[][] population, double[] values) {
		int worstIndex = findWorst(values);
		double[] rouletteValues = scale(values.clone(), values[worstIndex]);
		double sum = sum(rouletteValues);
		double hit = Math.random() * sum;
		int hitIndex = findIndex(rouletteValues, hit);
		if (hitIndex == 6) {
			hitIndex = 5;
		}
		return population[hitIndex];

	}

	private int findIndex(double[] rouletteValues, double hit) {
		int index = 0;
		double sum = 0;

		for (int i = 0; i < rouletteValues.length; i++) {

			sum += rouletteValues[i];
			if (sum > hit) {
				break;
			} else {
				index++;
			}
		}

		return index;
	}

	private double sum(double[] values) {
		double sum = 0;

		for (int i = 0; i < values.length; i++) {
			sum += values[i];
		}

		return sum;
	}

	private double[] scale(double[] values, double worstValue) {
		for (int i = 0; i < values.length; i++) {
			values[i] = worstValue - values[i];
		}
		return values;
	}

	private int findWorst(double[] values) {
		int worstIndex = 0;
		for (int i = 0; i < values.length; i++) {
			if (values[i] > values[worstIndex]) {
				worstIndex = i;
			}
		}
		return worstIndex;
	}

}
