package hr.fer.zemris.optjava.dz4.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.dz4.api.ICrossing;
import hr.fer.zemris.optjava.dz4.api.IFunction;
import hr.fer.zemris.optjava.dz4.api.IMutation;
import hr.fer.zemris.optjava.dz4.api.ISelection;
import hr.fer.zemris.optjava.dz4.impl.IntArrayCrossing;
import hr.fer.zemris.optjava.dz4.impl.NTournamentIntArray;
import hr.fer.zemris.optjava.dz4.impl.RateMutation;
import hr.fer.zemris.optjava.dz4.impl.StickFunction;

public class BoxFilling {

	public static void main(String[] args) throws IOException {

		if (args.length != 7) {
			System.out.println("Neispravan broj argumenta");
			System.exit(1);
		}

		String row = Files.readAllLines(Paths.get(args[0])).get(0);

		int[] startingSolution = parseRow(row);

		IFunction function = new StickFunction(20);
		ICrossing crossing = new IntArrayCrossing();
		IMutation mutation = new RateMutation(0.7);

		int populationSize = Integer.valueOf(args[1]);

		int[][] startingPopulation = generateStartingPopulation(startingSolution, mutation, populationSize);

		int n = Integer.valueOf(args[2]);
		ISelection selectionParents = new NTournamentIntArray(n);

		int m = Integer.valueOf(args[3]);
		ISelection selectionElimination = new NTournamentIntArray(m);

		boolean p = Boolean.valueOf(args[4]);

		int maxIterations = Integer.valueOf(args[5]);

		int minSize = Integer.valueOf(args[6]);

		EliminationAlgorithm<int[]> algorithm = new EliminationAlgorithm<int[]>(startingPopulation, function, crossing,
				mutation, selectionParents, selectionElimination, maxIterations, minSize, p);
		algorithm.run();

	}

	private static int[] parseRow(String row) {
		List<Integer> list = new ArrayList<>();
		row = row.substring(1, row.length() - 1);
		String[] numberStrings = row.split(", ");
		for (int i = 0; i < numberStrings.length; i++) {
			list.add(Integer.valueOf(numberStrings[i]));
		}
		int[] array = new int[list.size()];
		for (int i = 0; i < list.size(); i++)
			array[i] = list.get(i);
		return array;

	}

	private static int[][] generateStartingPopulation(int[] startingSolution, IMutation mutation, int populationSize) {
		int[][] population = new int[populationSize][startingSolution.length];
		for (int i = 0; i < populationSize; i++) {
			population[i] = (int[]) mutation.mutate(new int[][] { startingSolution, startingSolution })[0];
		}
		return population;
	}

}
