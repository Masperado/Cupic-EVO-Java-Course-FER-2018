package hr.fer.zemris.optjava.dz7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CLONALG {

	private FFANN ffann;
	private double[] globalBest;
	private double globalBestFunctionValue;
	private double[][] population;
	private double[] functionValues;
	private double[][] clones;
	private List<double[]> mutants;
	private int populationSize;
	private double merr;
	private double maxIter;

	public CLONALG(FFANN ffann, int populationSize, int maxIter, double merr) {
		this.ffann = ffann;
		this.populationSize = populationSize;
		this.merr = merr;
		this.maxIter = maxIter;
	}

	public double[] run() {

		initializePopulation();

		int iterCount = 0;
		while (iterCount < maxIter && globalBestFunctionValue > merr) {

			for (int i = 0; i < population.length; i++) {
				functionValues[i] = ffann.errorFunction(population[i]);
			}

			generateClones();

			generateMutants();

			generateNewGeneration();

			if (ffann.errorFunction(population[0]) < globalBestFunctionValue) {
				globalBest = Arrays.copyOf(population[0], population[0].length);
				globalBestFunctionValue = ffann.errorFunction(globalBest);
			} else {
				population[population.length - 1] = Arrays.copyOf(globalBest, globalBest.length);
			}

			iterCount++;
			System.out.println(iterCount);
			System.out.println(globalBestFunctionValue);
		}

		return globalBest;

	}

	private void generateNewGeneration() {
		int weightsCount = ffann.getWeightsCount();
		for (int i = 0; i < (population.length - 5); i++) {
			population[i] = mutants.get(i);
		}
		for (int i = population.length - 5; i < population.length; i++) {
			double[] neuron = new double[weightsCount];
			for (int j = 0; j < weightsCount; j++) {
				neuron[j] = -1 + Math.random() * 2;
			}
			population[i] = neuron;
		}
	}

	private void generateMutants() {
		mutants = new ArrayList<>();
		for (int i = 0; i < clones.length; i++) {
			double[] mutant = clones[i];
			for (int j = 0; j < mutant.length; j++) {
				mutant[j] += -0.1 + Math.random() * 0.2;
			}
			mutants.add(mutant);
		}
		mutants.sort((a, b) -> (Double.compare(ffann.errorFunction(a), ffann.errorFunction(b))));
	}

	private void generateClones() {
		List<double[]> clonesList = new ArrayList<>();
		for (int i = 0; i < population.length; i++) {
			int cloneNumber = (int) Math.floor(1 / (globalBestFunctionValue / functionValues[i]));
			if (cloneNumber < 1) {
				cloneNumber = 1;
			}
			if (cloneNumber > 10) {
				cloneNumber = 10;
			}
			for (int j = 0; j < cloneNumber; j++) {
				clonesList.add(Arrays.copyOf(population[i], population[i].length));
			}
		}
		clones = clonesList.stream().toArray(double[][]::new);

	}

	private void initializePopulation() {
		int weightsCount = ffann.getWeightsCount();
		population = new double[populationSize][weightsCount];
		functionValues = new double[populationSize];
		for (int i = 0; i < populationSize; i++) {
			double[] neuron = new double[weightsCount];
			for (int j = 0; j < weightsCount; j++) {
				neuron[j] = -1 + Math.random() * 2;
			}
			population[i] = neuron;
		}
		globalBest = Arrays.copyOf(population[0], population[0].length);
		globalBestFunctionValue = ffann.errorFunction(globalBest);

	}

}
