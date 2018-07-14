package hr.fer.zemris.optjava.dz7;

import java.util.Arrays;

public class PSOG {

	private FFANN ffann;
	private double[][] population;
	private double[][] speeds;
	private double[][] personalBest;
	private double[] personalBestFunctionValue;
	private double[] functionValues;
	private double[] globalBest;
	private double globalBestFunctionValue;
	private int populationSize;
	private double c1;
	private double c2;
	private double minSpeed;
	private double maxSpeed;
	private double merr;
	private int maxIter;

	public PSOG(FFANN ffann, int populationSize, double c1, double c2, double minSpeed, double maxSpeed, int maxIter,
			double merr) {
		this.ffann = ffann;
		this.populationSize = populationSize;
		this.c1 = c1;
		this.c2 = c2;
		this.minSpeed = minSpeed;
		this.maxSpeed = maxSpeed;
		this.merr = merr;
		this.maxIter = maxIter;
	}

	public double[] run() {

		initalizePopulation();
		int iterCount = 0;
		int weightsCount = ffann.getWeightsCount();
		double inertion = 1;

		while (iterCount < maxIter && globalBestFunctionValue > merr) {
			for (int i = 0; i < population.length; i++) {
				functionValues[i] = ffann.errorFunction(population[i]);

				if (functionValues[i] < personalBestFunctionValue[i]) {
					personalBest[i] = Arrays.copyOf(population[i], population[i].length);

					personalBestFunctionValue[i] = functionValues[i];
				}

				if (functionValues[i] < globalBestFunctionValue) {
					globalBest = Arrays.copyOf(population[i], population[i].length);
					globalBestFunctionValue = functionValues[i];
				}
			}

			for (int i = 0; i < population.length; i++) {
				for (int j = 0; j < weightsCount; j++) {
					speeds[i][j] = speeds[i][j] * inertion
							+ c1 * Math.random() * (personalBest[i][j] - population[i][j])
							+ c2 * Math.random() * (globalBest[j] - population[i][j]);
					if (speeds[i][j] < minSpeed) {
						speeds[i][j] = minSpeed;
					}
					if (speeds[i][j] > maxSpeed) {
						speeds[i][j] = maxSpeed;
					}
					population[i][j] += speeds[i][j];
				}
			}
			iterCount++;
			inertion *= 0.01;
			System.out.println(iterCount);
			System.out.println(globalBestFunctionValue);
		}

		return globalBest;
	}

	private void initalizePopulation() {
		int weightsCount = ffann.getWeightsCount();
		population = new double[populationSize][weightsCount];
		personalBest = new double[populationSize][weightsCount];
		speeds = new double[populationSize][weightsCount];
		functionValues = new double[populationSize];
		personalBestFunctionValue = new double[populationSize];
		for (int i = 0; i < populationSize; i++) {
			double[] neuron = new double[weightsCount];
			double[] speed = new double[weightsCount];
			for (int j = 0; j < weightsCount; j++) {
				neuron[j] = -1 + Math.random() * 2;
				speed[j] = -1 + Math.random() * 2;
			}
			speeds[i] = speed;
			population[i] = neuron;
			personalBest[i] = Arrays.copyOf(neuron, neuron.length);
			personalBestFunctionValue[i] = ffann.errorFunction(neuron);
		}
		globalBest = Arrays.copyOf(population[0], population[0].length);
		globalBestFunctionValue = ffann.errorFunction(globalBest);

	}

}
