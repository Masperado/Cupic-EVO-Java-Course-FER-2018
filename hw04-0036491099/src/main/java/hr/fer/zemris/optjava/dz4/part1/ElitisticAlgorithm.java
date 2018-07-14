package hr.fer.zemris.optjava.dz4.part1;

import hr.fer.zemris.optjava.dz4.api.ICrossing;
import hr.fer.zemris.optjava.dz4.api.IFunction;
import hr.fer.zemris.optjava.dz4.api.IMutation;
import hr.fer.zemris.optjava.dz4.api.ISelection;

public final class ElitisticAlgorithm<T> {

	private final T[] startingPopulation;
	private final IFunction<T> function;
	private final ICrossing<T> crossing;
	private final IMutation<T> mutation;
	private final ISelection<T> selection;
	private final double minError;
	private final int maxIterations;

	public ElitisticAlgorithm(T[] startingPopulation, IFunction<T> function, ICrossing<T> crossing,
			IMutation<T> mutation, ISelection<T> selection, double minError, int maxIterations) {
		this.startingPopulation = startingPopulation;
		this.function = function;
		this.crossing = crossing;
		this.mutation = mutation;
		this.selection = selection;
		this.minError = minError;
		this.maxIterations = maxIterations;
	}

	public void run() {
		T[] population = startingPopulation;
		int iterationNumber = 0;
		double bestValue = Double.MAX_VALUE;
		double[] values;
		while (iterationNumber < maxIterations && minError < bestValue) {
			values = generateValues(population);
			int[] elite = findElite(values);
			T[] newPopulation = population.clone();

			newPopulation[0] = population[elite[0]];
			newPopulation[1] = population[elite[1]];
			bestValue = values[elite[0]];
			for (int i = 2; i < population.length; i += 2) {
				T[] parents = selection.select(population, values);
				T[] kids = crossing.cross(parents[0], parents[1]);
				T[] mutants = mutation.mutate(kids);

				if ((i + 1) == population.length) {
					newPopulation[i] = mutants[0];
				} else {
					newPopulation[i] = mutants[0];
					newPopulation[i + 1] = mutants[1];
				}
			}
			population = newPopulation;
			iterationNumber++;
			System.out.println("ITERACIJA: " + iterationNumber);
			System.out.println("RJEŠENJE: ");
			print(population[0]);
			System.out.println("ODSTUPANJE: " + bestValue);
			System.out.println();
		}

	}

	private void print(T child) {
		if (child.getClass() == double[].class) {
			double[] solution = (double[]) child;
			for (int i = 0; i < solution.length; i++) {
				System.out.printf(solution[i] + " ");
			}
			System.out.println();
		}
	}

	private int[] findElite(double[] values) {
		int indexElite1 = 0;
		int indexElite2 = 0;

		for (int i = 1; i < values.length; i++) {
			if (values[i] < values[indexElite1]) {
				indexElite1 = i;
			}
		}

		if (indexElite1 == indexElite2) {
			indexElite2 = 1;
		}

		for (int i = 0; i < values.length; i++) {
			if (i == indexElite1) {
				continue;
			}
			if (values[i] < values[indexElite2]) {
				indexElite2 = i;
			}
		}

		return new int[] { indexElite1, indexElite2 };

	}

	private double[] generateValues(T[] population) {
		double[] newValues = new double[population.length];
		for (int i = 0; i < newValues.length; i++) {
			newValues[i] = function.valueAt(population[i]);
		}
		return newValues;
	}

}
