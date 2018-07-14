package hr.fer.zemris.optjava.dz3.solver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz3.BinaryUnifNeighborhood;
import hr.fer.zemris.optjava.dz3.BitVectorSolution;
import hr.fer.zemris.optjava.dz3.DoubleArraySolution;
import hr.fer.zemris.optjava.dz3.DoubleArrayUnifNeighbour;
import hr.fer.zemris.optjava.dz3.GeometricTempSchedule;
import hr.fer.zemris.optjava.dz3.IFunction;
import hr.fer.zemris.optjava.dz3.MyFunction;
import hr.fer.zemris.optjava.dz3.NaturalBinaryDecoder;
import hr.fer.zemris.optjava.dz3.PassThroughDecoder;
import hr.fer.zemris.optjava.dz3.SimulatedAnnealing;

public class RegresijaSustava {

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("Neispravan broj argumenta");
			System.exit(1);
		}

		List<String> rows = Files.readAllLines(Paths.get(args[1]));
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

		IFunction function = new MyFunction(coeffMatrix, constMatrix);

		String[] representationWithN = args[0].split(":");
		String representation = representationWithN[0];

		int n = 0;
		if (representationWithN.length == 2) {
			n = Integer.valueOf(representationWithN[1]);
			
			if (n<5 || n>30) {
				System.out.println("Neispravan broj bitova po varijabli!");
				System.exit(1);
			}
		}
		Random rand = new Random();

		switch (representation) {
		case "decimal":
			DoubleArraySolution startWith = getRandomDoubleSolution();
			DoubleArrayUnifNeighbour neighborhood = new DoubleArrayUnifNeighbour(new double[] { -0.1, 0.1 }, rand);
			GeometricTempSchedule tempSchedule = new GeometricTempSchedule(0.99, 1000, 100, 1000);
			PassThroughDecoder decoder = new PassThroughDecoder();
			boolean minimize = true;
			SimulatedAnnealing<DoubleArraySolution> simulation = new SimulatedAnnealing<DoubleArraySolution>(decoder,
					neighborhood, startWith, function, minimize, tempSchedule);
			simulation.run();
			break;
		case "binary":
			BitVectorSolution startWith2 = getRandomBinarySolution(n*6);
			BinaryUnifNeighborhood neighborhood2 = new BinaryUnifNeighborhood(rand);
			GeometricTempSchedule tempSchedule2 = new GeometricTempSchedule(0.99, 1000, 100, 1000);
			NaturalBinaryDecoder decoder2 = new NaturalBinaryDecoder(-100, 100, n, 6);
			boolean minimize2 = true;
			SimulatedAnnealing<BitVectorSolution> simulation2 = new SimulatedAnnealing<BitVectorSolution>(decoder2, neighborhood2, startWith2, function, minimize2, tempSchedule2);
			simulation2.run();


		}
	}

	private static BitVectorSolution getRandomBinarySolution(int n) {
		Random rand = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<n;i++) {
			sb.append(rand.nextInt(2));
		}
		return new BitVectorSolution(sb.toString());
	}

	private static DoubleArraySolution getRandomDoubleSolution() {
		Random rand = new Random();
		double[] solution = new double[6];
		for (int i = 0; i < 6; i++) {
			solution[i] = -5 + rand.nextDouble() * 10;
		}
		return new DoubleArraySolution(solution);
	}
	
	
}
