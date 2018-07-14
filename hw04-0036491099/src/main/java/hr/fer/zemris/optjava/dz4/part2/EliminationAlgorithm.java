package hr.fer.zemris.optjava.dz4.part2;

import hr.fer.zemris.optjava.dz4.api.ICrossing;
import hr.fer.zemris.optjava.dz4.api.IFunction;
import hr.fer.zemris.optjava.dz4.api.IMutation;
import hr.fer.zemris.optjava.dz4.api.ISelection;

public class EliminationAlgorithm<T> {

	private final T[] startingPopulation;
	private final IFunction<T> function;
	private final ICrossing<T> crossing;
	private final IMutation<T> mutation;
	private final ISelection<T> selectionParents;
	private final ISelection<T> selectionElimination;
	private final int maxIterations;
	private final int minSize;
	private final boolean p;

	public EliminationAlgorithm(T[] startingPopulation, IFunction<T> function, ICrossing<T> crossing,
			IMutation<T> mutation, ISelection<T> selectionParents, ISelection<T> selectionElimination,
			int maxIterations, int minSize, boolean p) {
		this.startingPopulation = startingPopulation;
		this.function = function;
		this.crossing = crossing;
		this.mutation = mutation;
		this.selectionParents = selectionParents;
		this.selectionElimination = selectionElimination;
		this.maxIterations = maxIterations;
		this.minSize = minSize;
		this.p = p;
	}

	public void run() {
		T[] population = startingPopulation;
		int iterationNumber = 0;
		T bestSolution = population[0];
		System.out.println("Rješenje: ");
		print(bestSolution);
		System.out.println("Duljina: ");
		System.out.println((int) function.valueAt(bestSolution));
		System.out.println();
		double[] values;
		while (iterationNumber < maxIterations && function.valueAt(bestSolution) >= minSize) {
			values = generateValues(population);
			T[] parents = selectionParents.select(population, values);

			T[] kids = crossing.cross(parents[0], parents[1]);

			T mutant = bestMutant(mutation.mutate(kids));

			T candidate = worseCandidate(selectionElimination.select(population, values));
			if (p) {
				if (function.valueAt(mutant) < function.valueAt(candidate)) {
					exchange(population, mutant, candidate);
				}
			} else {
				exchange(population, mutant, candidate);
			}
			iterationNumber++;
			bestSolution = getBestSolution(bestSolution, population);
		}

	}

	private T worseCandidate(T[] candidates) {
		if (function.valueAt(candidates[0]) < function.valueAt(candidates[1])) {
			return candidates[1];
		} else {
			return candidates[0];
		}
	}

	private T bestMutant(T[] mutants) {
		if (function.valueAt(mutants[0]) > function.valueAt(mutants[1])) {
			return mutants[1];
		} else {
			return mutants[0];
		}
	}

	private T getBestSolution(T bestSolution, T[] population) {
		for (int i = 0; i < population.length; i++) {
			if (function.valueAt(population[i]) < function.valueAt(bestSolution)) {
				System.out.println("Rješenje: ");
				print(population[i]);
				System.out.println("Udaljenost: ");
				System.out.println((int) function.valueAt(bestSolution));
				System.out.println();
				return population[i];
			}
		}
		return bestSolution;
	}

	private void exchange(T[] population, T mutant, T candidate) {
		for (int i = 0; i < population.length; i++) {
			if (population[i] == candidate) {
				population[i] = mutant;
				break;
			}
		}
	}

	private void print(T child) {
		if (child.getClass() == int[].class) {
			int[] solution = (int[]) child;
			for (int i = 0; i < solution.length; i++) {
				System.out.printf(solution[i] + " ");
			}
			System.out.println();
		}
	}

	private double[] generateValues(T[] population) {
		double[] newValues = new double[population.length];
		for (int i = 0; i < newValues.length; i++) {
			newValues[i] = function.valueAt(population[i]);
		}
		return newValues;
	}
}
