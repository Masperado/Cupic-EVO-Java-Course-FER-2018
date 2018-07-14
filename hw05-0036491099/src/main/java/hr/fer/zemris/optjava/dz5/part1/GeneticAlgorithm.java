package hr.fer.zemris.optjava.dz5.part1;

import hr.fer.zemris.optjava.dz5.api.ICrossing;
import hr.fer.zemris.optjava.dz5.api.IFunction;
import hr.fer.zemris.optjava.dz5.api.IMutation;
import hr.fer.zemris.optjava.dz5.api.ISelection;
import hr.fer.zemris.optjava.dz5.impl.BitCrossing;
import hr.fer.zemris.optjava.dz5.impl.BitMutation;
import hr.fer.zemris.optjava.dz5.impl.MaxOnesFunction;
import hr.fer.zemris.optjava.dz5.impl.NTournamentRandomSelection;
import hr.fer.zemris.optjava.dz5.impl.RAPGA;

public class GeneticAlgorithm {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("NEISPRAVAN BROJ ARGUMENATA");
			System.exit(1);
		}

		int n = Integer.valueOf(args[0]);

		boolean[][] startingPopulation = generateStartingPopulation(n, 10000);
		IFunction function = new MaxOnesFunction();
		ICrossing crossing = new BitCrossing();
		IMutation mutation = new BitMutation(0.01);
		ISelection selection = new NTournamentRandomSelection(2);
		double compFactor = 0.5;
		int minPopulationSize = 20;
		int maxPopulationSize = 10001;
		int maxIterations = 10000;
		double minError = 1 / n;

		@SuppressWarnings("unchecked")
		RAPGA<boolean[]> rapga = new RAPGA<>(startingPopulation, function, crossing, mutation, selection, compFactor,
				minPopulationSize, maxPopulationSize, maxIterations, minError);

		rapga.run();
	}

	private static boolean[][] generateStartingPopulation(int n, int populationSize) {
		boolean[][] population = new boolean[populationSize][n];

		for (int i = 0; i < populationSize; i++) {
			population[i] = generateSolution(n);
		}
		return population;
	}

	private static boolean[] generateSolution(int n) {
		boolean[] solution = new boolean[n];

		for (int i = 0; i < n; i++) {
			solution[i] = Math.random() > 0.5;
		}

		return solution;
	}

}
