package hr.fer.zemris.optjava.dz2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import Jama.Matrix;

public class Sustav {

	public static void main(String[] args) throws IOException {
		if (args.length != 3) {
			System.out.println("Nedovoljan broj argumenata");
			System.exit(1);
		}

		int maxIterations = Integer.valueOf(args[1]);
		List<String> rows = Files.readAllLines(Paths.get(args[2]));
		double[] consts = new double[rows.size() - 8];
		double[][] coeff = new double[rows.size() - 8][rows.size() - 8];
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

		IHFunction function = new Function3(coeffMatrix, constMatrix);
		Point point = null;

		List<Matrix> solutions = null;

		switch (args[0]) {
		case "grad":
			solutions = NumOptAlgorithms.getMinimumGrad(function, maxIterations, point);
			break;
		case "newt":
			solutions = NumOptAlgorithms.getMinimumNewt(function, maxIterations, point);
			break;
		default:
			System.out.println("Nepoznati algoritam!");
			System.exit(1);
		}

		double[] values = solutions.get(solutions.size() - 1).getRowPackedCopy();

		System.out.println("Udaljenost: " + function.getValue(solutions.get(solutions.size() - 1)));
		System.out.println();

		for (int i = 0; i < values.length; i++) {
			System.out.println("Rješenje x" + (i + 1) + ": " + values[i]);
		}

	}

}
