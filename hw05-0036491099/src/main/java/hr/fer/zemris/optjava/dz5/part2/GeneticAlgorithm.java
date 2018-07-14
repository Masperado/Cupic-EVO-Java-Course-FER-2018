package hr.fer.zemris.optjava.dz5.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.optjava.dz5.api.ICrossing;
import hr.fer.zemris.optjava.dz5.api.IFunction;
import hr.fer.zemris.optjava.dz5.api.IMutation;
import hr.fer.zemris.optjava.dz5.api.ISelection;
import hr.fer.zemris.optjava.dz5.impl.IntArrayCrossing;
import hr.fer.zemris.optjava.dz5.impl.NTournamentIntArray;
import hr.fer.zemris.optjava.dz5.impl.QAPFunction;
import hr.fer.zemris.optjava.dz5.impl.RateMutation;
import hr.fer.zemris.optjava.dz5.impl.SASEGASA;

public class GeneticAlgorithm {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws IOException {

		if (args.length != 3) {
			System.out.println("NEISPRAVAN BROJ ARGUMENATA");
			System.exit(1);
		}

		List<String> lines = Files.readAllLines(Paths.get(args[0]));

		int noOfVariables = Integer.valueOf(lines.get(0));

		int[][] distances = readMatrix(noOfVariables, lines, 2, 2 + noOfVariables);

		int[][] quantites = readMatrix(noOfVariables, lines, 2 + noOfVariables + 1,
				2 + noOfVariables + 1 + noOfVariables);

		int populationCount = Integer.valueOf(args[1]);

		int noOfVilages = Integer.valueOf(args[2]);

		int[][] startingPopulation = generateStartingPopulation(noOfVariables, populationCount);
		IFunction function = new QAPFunction(distances, quantites);
		ICrossing crossing = new IntArrayCrossing();
		IMutation mutation = new RateMutation(0.1);
		ISelection selection = new NTournamentIntArray(3);
		double compFactor = 0.5;
		int maxGenerations = 10;

		@SuppressWarnings("unchecked")
		SASEGASA<int[]> sasegasa = new SASEGASA<>(startingPopulation, function, crossing, mutation, selection,
				compFactor, noOfVilages, maxGenerations);

		sasegasa.run();
	}

	private static int[][] generateStartingPopulation(int noOfVariables, int populationCount) {
		int[][] startingPopulation = new int[populationCount][noOfVariables];

		for (int i = 0; i < populationCount; i++) {
			startingPopulation[i] = generateSolution(noOfVariables);
		}
		return startingPopulation;
	}

	private static int[] generateSolution(int noOfVariables) {

		List<Integer> numbers = new ArrayList<>();
		for (int i = 0; i < noOfVariables; i++) {
			numbers.add(i);
		}
		Collections.shuffle(numbers);
		return toIntArray(numbers);

	}

	private static int[] toIntArray(List<Integer> list) {
		int[] ret = new int[list.size()];
		for (int i = 0; i < ret.length; i++)
			ret[i] = list.get(i);
		return ret;
	}

	private static int[][] readMatrix(int noOfVariables, List<String> lines, int start, int end) {
		int[][] matrix = new int[noOfVariables][noOfVariables];

		for (int i = start; i < end; i++) {

			String[] numbers = lines.get(i).split(" ");
			for (int j = 0; j < numbers.length; j++) {
				matrix[i - start][j] = Integer.valueOf(numbers[j]);
			}
		}
		return matrix;
	}

}
