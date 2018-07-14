package hr.fer.zemris.optjava.dz5.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.optjava.dz5.api.ICrossing;
import hr.fer.zemris.optjava.dz5.api.IFunction;
import hr.fer.zemris.optjava.dz5.api.IMutation;
import hr.fer.zemris.optjava.dz5.api.ISelection;

public final class RAPGA<T> {

	private final T[] startingPopulation;
	private final IFunction<T> function;
	private final ICrossing<T> crossing;
	private final IMutation<T> mutation;
	private final ISelection<T> selection;
	private double compFactor;
	private final int minPopulationSize;
	private final int maxPopulationSize;
	private final int maxIterations;
	private final double minError;

	public RAPGA(T[] startingPopulation, IFunction<T> function, ICrossing<T> crossing, IMutation<T> mutation,
			ISelection<T> selection, double compFactor, int minPopulationSize, int maxPopulationSize, int maxIterations,
			double minError) {
		this.startingPopulation = startingPopulation;
		this.function = function;
		this.crossing = crossing;
		this.mutation = mutation;
		this.selection = selection;
		this.compFactor = compFactor;
		this.minPopulationSize = minPopulationSize;
		this.maxPopulationSize = maxPopulationSize;
		this.maxIterations = maxIterations;
		this.minError = minError;
	}

	public void run() {
		int iterationNumber = 0;
		T[] population = startingPopulation;
		double[] values;
		List<T> newPopulation = new ArrayList<>();
		T bestSolution = population[0];
		System.out.println("Početno rješenje: ");
		print(bestSolution);
		System.out.println("Vrijednost: ");
		System.out.println(function.valueAt(bestSolution));
		System.out.println();

		while (iterationNumber < maxIterations && function.valueAt(bestSolution) < (1 - minError)
				&& population.length > minPopulationSize && population.length < maxPopulationSize) {
			values = generateValues(population);
			for (int i = 0; i < 10000; i++) {

				T[] parents = sort(selection.select(population, values));
				T[] kids = crossing.cross(parents[0], parents[1]);
				T[] mutants = mutation.mutate(kids);

				T bestKid = bestKid(mutants);

				if (function.valueAt(bestKid) > ((function.valueAt(parents[1])
						+ (function.valueAt(parents[0]) - function.valueAt(parents[1])) * compFactor))) {
					newPopulation.add(bestKid);
				}
			}

			if (newPopulation.size() == 0) {
				break;
			}
			population = newPopulation.toArray(Arrays.copyOfRange(population, 0, 1));
			iterationNumber++;
			newPopulation.clear();

			bestSolution = getBestSolution(bestSolution, population);
		}
		System.out.println("KRAJ");
	}

	private void print(T bestSolution) {
		boolean[] array = (boolean[]) bestSolution;

		for (int i = 0; i < array.length; i++) {
			if (array[i]) {
				System.out.print("1");
			} else {
				System.out.print("0");

			}
		}
		System.out.println();
	}

	private T getBestSolution(T bestSolution, T[] population) {
		for (int i = 0; i < population.length; i++) {
			if (function.valueAt(population[i]) > function.valueAt(bestSolution)) {
				System.out.println("Rješenje: ");
				print(population[i]);
				System.out.println("Vrijednost: ");
				System.out.println(function.valueAt(population[i]));
				System.out.println();
				return population[i];
			}
		}
		return bestSolution;
	}

	private T[] sort(T[] select) {
		T[] reverseArray = select;

		if (function.valueAt(select[0]) > function.valueAt(select[1])) {
			return select;
		} else {
			reverseArray[0] = select[1];
			reverseArray[1] = select[0];
			return reverseArray;
		}
	}

	private T bestKid(T[] mutants) {
		double bestValue = 0;
		int index = -1;
		for (int i = 0; i < mutants.length; i++) {
			if (function.valueAt(mutants[i]) > bestValue) {
				bestValue = function.valueAt(mutants[i]);
				index = i;
			}
		}
		if (index == -1) {
			index = 0;
		}

		return mutants[index];

	}

	private double[] generateValues(T[] population) {
		double[] newValues = new double[population.length];
		for (int i = 0; i < newValues.length; i++) {
			newValues[i] = function.valueAt(population[i]);
		}
		return newValues;
	}

}
