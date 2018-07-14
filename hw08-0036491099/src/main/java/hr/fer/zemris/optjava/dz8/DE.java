package hr.fer.zemris.optjava.dz8;

import java.util.Arrays;
import java.util.Random;

import hr.fer.zemris.optjava.dz8.api.INeuralNetwork;

public class DE {

	private INeuralNetwork network;
	private double[] globalBest;
	private double globalBestFunctionValue;
	private double[][] population;
	private double[] functionValues;
	private double[][] kids;
	private double[] kidFunctionValues;
	private double[][] mutants;
	private int populationSize;
	private double merr;
	private double maxIter;
	private Random random;

	public DE(INeuralNetwork network, int populationSize, int maxIter, double merr) {
		this.network = network;
		this.populationSize = populationSize;
		this.merr = merr;
		this.maxIter = maxIter;
		this.random = new Random();
	}

	public double[] run() {

		initializePopulation();

		int iterCount = 0;
		while (iterCount < maxIter && globalBestFunctionValue > merr) {

			for (int i = 0; i < population.length; i++) {
				functionValues[i] = network.errorFunction(population[i]);
			}

			generateMutants();

			generateKids();

			generateNewGeneration();

			if (network.errorFunction(population[0]) < globalBestFunctionValue) {
				globalBest = Arrays.copyOf(population[0], population[0].length);
				globalBestFunctionValue = network.errorFunction(globalBest);
			} else {
				population[population.length - 1] = Arrays.copyOf(globalBest, globalBest.length);
			}

			iterCount++;
			System.out.println(iterCount);
			System.out.println(globalBestFunctionValue);
		}

		return globalBest;

	}

	private void generateKids() {
		kids = new double[population.length][population[0].length];
		kidFunctionValues = new double[population.length];

		for (int i = 0; i < population.length; i++) {
			double[] kid = new double[population[0].length];
			for (int j = 0; j < kid.length; j++) {
				if (Math.random() > 0.8) {
					kid[j] = mutants[i][j];
				} else {
					kid[j] = population[i][j];
				}
			}
			kids[i] = kid;
			kidFunctionValues[i] = network.errorFunction(kid);
		}
	}

	private void generateNewGeneration() {
		for (int i = 0; i < population.length - 1; i++) {
			if (functionValues[i] > kidFunctionValues[i]) {
				population[i] = Arrays.copyOf(kids[i], kids[i].length);
			}
		}
		Arrays.sort(population, (a, b) -> Double.compare(network.errorFunction(a), network.errorFunction(b)));
	}

	private void generateMutants() {
		mutants = new double[population.length][population[0].length];

		for (int i = 0; i < population.length; i++) {
			int r1 = -1;
			int r2 = -1;
			int r3 = -1;

			while (true) {
				r1 = random.nextInt(populationSize);
				if (r1 != i && r1 != r2 && r1 != r3)
					break;
			}

			while (true) {
				r2 = random.nextInt(populationSize);
				if (r2 != i && r2 != r1 && r2 != r3)
					break;
			}

			while (true) {
				r3 = random.nextInt(populationSize);
				if (r3 != i && r3 != r1 && r3 != r2)
					break;
			}

			double[] mutant = new double[population[0].length];
			for (int j = 0; j < mutant.length; j++) {
				mutant[j] = population[r1][j] + 1 * (population[r2][j] - population[r3][j]);
			}
			mutants[i] = mutant;
		}
	}

	private void initializePopulation() {
		int weightsCount = network.getWeightsCount();
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
		globalBestFunctionValue = network.errorFunction(globalBest);

	}

}
