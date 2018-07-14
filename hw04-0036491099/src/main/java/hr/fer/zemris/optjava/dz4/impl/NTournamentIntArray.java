package hr.fer.zemris.optjava.dz4.impl;

import java.util.Random;

import hr.fer.zemris.optjava.dz4.api.ISelection;

public class NTournamentIntArray implements ISelection<int[]> {

	private int n;
	Random random = new Random();

	public NTournamentIntArray(int n) {
		this.n = n;
	}

	@Override
	public int[][] select(int[][] population, double[] values) {
		int[] parent1 = selectParent(population, values);
		int[] parent2 = selectParent(population, values);
		int countNumber = 0;

		while (sum(parent1) == sum(parent2)) {
			parent2 = selectParent(population, values);
			countNumber++;
			if (countNumber == 3) {
				countNumber = 0;
				parent2 = population[random.nextInt(population.length)];
				break;
			}
		}

		return new int[][] { parent1, parent2 };
	}

	public int[] selectParent(int[][] population, double[] values) {

		int indexes[] = randomIndexes(population.length, n);
		int bestIndex = bestIndex(values, indexes);
		return population[bestIndex];

	}

	private int bestIndex(double[] values, int[] indexes) {
		int bestIndex = indexes[0];
		for (int i = 1; i < indexes.length; i++) {
			if (values[indexes[i]] < values[bestIndex]) {
				bestIndex = indexes[i];
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

	private int sum(int[] values) {
		int sum = 0;

		for (int i = 0; i < values.length; i++) {
			sum += values[i] * i;
		}

		return sum;
	}

}
