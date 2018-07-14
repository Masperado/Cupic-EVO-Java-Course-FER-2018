package hr.fer.zemris.optjava.dz5.impl;

import java.util.Random;

import hr.fer.zemris.optjava.dz5.api.ISelection;

public class NTournamentRandomSelection implements ISelection<boolean[]> {

	private int n;
	Random random = new Random();

	public NTournamentRandomSelection(int n) {
		this.n = n;
	}

	@Override
	public boolean[][] select(boolean[][] population, double[] values) {
		boolean[] parent1 = selectParent(population, values);
		boolean[] parent2 = population[random.nextInt(population.length)];

		return new boolean[][] { parent1, parent2 };
	}

	public boolean[] selectParent(boolean[][] population, double[] values) {

		int[] indexes = randomIndexes(population.length, n);
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

}
