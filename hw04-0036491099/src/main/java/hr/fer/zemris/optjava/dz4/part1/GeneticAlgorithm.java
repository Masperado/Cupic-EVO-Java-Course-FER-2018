package hr.fer.zemris.optjava.dz4.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz4.api.ICrossing;
import hr.fer.zemris.optjava.dz4.api.IFunction;
import hr.fer.zemris.optjava.dz4.api.IMutation;
import hr.fer.zemris.optjava.dz4.api.ISelection;
import hr.fer.zemris.optjava.dz4.impl.AFFunction;
import hr.fer.zemris.optjava.dz4.impl.BLX;
import hr.fer.zemris.optjava.dz4.impl.SigmaMutation;
import hr.fer.zemris.optjava.dz4.impl.NTournamentDoubleArray;
import hr.fer.zemris.optjava.dz4.impl.RouletteWheel;

public class GeneticAlgorithm {

	public static void main(String[] args) throws IOException {

		if (args.length != 6) {
			System.out.println("Neispravan broj argumenta");
			System.exit(1);
		}

		List<String> rows = Files.readAllLines(Paths.get(args[0]));
		double[] consts = new double[rows.size() - 1];
		double[][] coeff = new double[rows.size() - 1][5];
		int numberOfComments = 0;

		for (int i = 0; i < rows.size(); i++) {
			if (rows.get(i).startsWith("#")) {
				numberOfComments++;
				continue;
			}

			String[] values = rows.get(i).substring(1, rows.get(i).length() - 1).split(", ");
			for (int j = 0; j < values.length - 1; j++) {
				coeff[i - numberOfComments][j] = Double.valueOf(values[j]);
			}
			consts[i - numberOfComments] = Double.valueOf(values[values.length - 1]);

		}

		Matrix coeffMatrix = new Matrix(coeff);
		Matrix constMatrix = new Matrix(new double[][] { consts });
		IFunction function = new AFFunction(coeffMatrix, constMatrix);

		int populationSize = Integer.valueOf(args[1]);
		double[][] startingPopulation = generatePopulation(populationSize);

		double minError = Double.valueOf(args[2]);
		int maxIterations = Integer.valueOf(args[3]);

		ISelection selection = null;

		if (args[4].equals("rouletteWheel")) {
			selection = new RouletteWheel();
		} else {
			int n = Integer.valueOf(args[4].split(":")[1]);
			selection = new NTournamentDoubleArray(n);
		}

		double sigma = Double.valueOf(args[5]);
		IMutation mutation = new SigmaMutation(sigma);

		ICrossing crossing = new BLX(0.1);

		ElitisticAlgorithm<double[]> algorithm = new ElitisticAlgorithm<double[]>(startingPopulation, function,
				crossing, mutation, selection, minError, maxIterations);
		algorithm.run();
	}

	private static double[][] generatePopulation(int populationSize) {
		double[][] population = new double[populationSize][6];
		for (int i = 0; i < populationSize; i++) {
			population[i] = generateSolution();
		}
		return population;
	}

	private static double[] generateSolution() {
		Random rand = new Random();
		double[] solution = new double[6];
		for (int i = 0; i < 6; i++) {
			solution[i] = -5 + rand.nextDouble() * 10;
		}
		return solution;
	}

}
