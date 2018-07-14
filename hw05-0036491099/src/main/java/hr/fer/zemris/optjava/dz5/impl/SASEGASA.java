package hr.fer.zemris.optjava.dz5.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.optjava.dz5.api.ICrossing;
import hr.fer.zemris.optjava.dz5.api.IFunction;
import hr.fer.zemris.optjava.dz5.api.IMutation;
import hr.fer.zemris.optjava.dz5.api.ISelection;

public class SASEGASA<T> {

	private final T[] startingPopulation;
	private final IFunction<T> function;
	private final ICrossing<T> crossing;
	private final IMutation<T> mutation;
	private final ISelection<T> selection;
	private double compFactor;
	private int noOfVilages;
	private final int maxGenerations;

	public SASEGASA(T[] startingPopulation, IFunction<T> function, ICrossing<T> crossing, IMutation<T> mutation,
			ISelection<T> selection, double compFactor, int noOfVilages, int maxGenerations) {
		this.startingPopulation = startingPopulation;
		this.function = function;
		this.crossing = crossing;
		this.mutation = mutation;
		this.selection = selection;
		this.compFactor = compFactor;
		this.noOfVilages = noOfVilages;
		this.maxGenerations = maxGenerations;
	}

	public void run() {
		List<T[]> population = generatePopulation(startingPopulation, noOfVilages);
		double[] values;
		List<T> newPopulation = new ArrayList<>();
		T bestSolution = population.get(0)[0];
		System.out.println("Početno rješenje: ");
		print(bestSolution);
		System.out.println("Vrijednost: ");
		System.out.println(function.valueAt(bestSolution));
		System.out.println();

		while (noOfVilages > 1) {

			for (int j = 0; j < noOfVilages; j++) {
				values = generateValues(population.get(j));
				for (int i = 0; i < maxGenerations; i++) {

					T[] parents = sort(selection.select(population.get(j), values));
					T[] kids = crossing.cross(parents[0], parents[1]);
					T[] mutants = mutation.mutate(kids);

					T bestKid = bestKid(mutants);

					if (function.valueAt(bestKid) < ((function.valueAt(parents[1])
							+ (function.valueAt(parents[0]) - function.valueAt(parents[1])) * compFactor))) {
						newPopulation.add(bestKid);
					}
					if (newPopulation.size() == 0) {
						break;
					}
					population.set(j, newPopulation.toArray(Arrays.copyOfRange(population.get(j), 0, 1)));
					newPopulation.clear();

				}

			}

			for (int j = 0; j < noOfVilages; j++) {
				bestSolution = getBestSolution(bestSolution, population.get(j));
			}

			noOfVilages--;
			population = generateNewPopulation(population, noOfVilages);
		}

		System.out.println("KRAJ");
	}

	private List<T[]> generateNewPopulation(List<T[]> population, int noOfVilages) {
		List<T> completePopulation = new ArrayList<>();
		for (int j = 0; j < population.size(); j++) {
			for (int i = 0; i < population.get(j).length; i++) {
				completePopulation.add(population.get(j)[i]);
			}
		}
		Collections.shuffle(completePopulation);
		T[] populationArray = completePopulation.toArray(Arrays.copyOfRange(population.get(0), 0, 1));
		return generatePopulation(populationArray, noOfVilages);

	}

	private List<T[]> generatePopulation(T[] population, int noOfVilages) {
		List<T[]> populationList = new ArrayList<>(noOfVilages);

		for (int i = 0; i < noOfVilages; i++) {
			populationList.add(Arrays.copyOfRange(population, i * population.length / noOfVilages,
					(i + 1) * population.length / noOfVilages));
		}

		return populationList;
	}

	private void print(T bestSolution) {
		int[] array = (int[]) bestSolution;

		for (int i = 0; i < array.length; i++) {
			System.out.print((array[i] + 1) + " ");
		}
		System.out.println();
	}

	private T getBestSolution(T bestSolution, T[] population) {
		for (int i = 0; i < population.length; i++) {
			if (function.valueAt(population[i]) < function.valueAt(bestSolution)) {
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
