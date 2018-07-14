package hr.fer.zemris.optjava.dz4.impl;

import java.util.Random;

import hr.fer.zemris.optjava.dz4.api.ISelection;

public class NTournamentDoubleArray implements ISelection<double[]> {

	private int n;
	Random random = new Random();

	public NTournamentDoubleArray(int n) {
		this.n = n;
	}

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
		int indexes[] = randomIndexes(population.length, n);
		int bestIndex = bestIndex(values, indexes);
		return population[bestIndex];

	}

	private int bestIndex(double[] values, int[] indexes) {
		int bestIndex = indexes[0];
		for (int i = 1; i < indexes.length; i++) {
			if (values[indexes[i]] < values[bestIndex]) {
				bestIndex = i;
			}
		}
		return bestIndex;
	}

	private int[] randomIndexes(int length, int n) {
		int[] indexes = new int[n];
		for (int i = 0; i < n; i++) {
			indexes[i] = random.nextInt(length);
		}
		return indexes;
	}

	private double sum(double[] values) {
		double sum = 0;

		for (int i = 0; i < values.length; i++) {
			sum += values[i];
		}

		return sum;
	}

}
