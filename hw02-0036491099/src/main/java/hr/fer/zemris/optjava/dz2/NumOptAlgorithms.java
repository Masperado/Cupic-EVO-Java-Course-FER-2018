package hr.fer.zemris.optjava.dz2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Jama.Matrix;

public class NumOptAlgorithms {

	public static List<Matrix> getMinimumNewt(IHFunction function, int maxIterations, Point point) {

		List<Matrix> solutions = new ArrayList<Matrix>();
		Matrix solution;
		
		if (point!=null) {
			solution = new Matrix(new double[][] {new double[] {point.getX(),point.getY()}});
		}else {
			solution = getInitSolution(function.getNumberOfVariables());

		}
		

		for (int i = 0; i < maxIterations; i++) {
			solutions.add(solution);
			double lambda = getLambda(function, solution);
			double[] solArray = solution.getRowPackedCopy();

			double[] dArray = function.getHesse().times(-1).inverse().times(function.getGradient(solution)).getRowPackedCopy();

			for (int j = 0; j < solArray.length; j++) {
				solArray[j] += lambda * dArray[j];
			}
			solution = new Matrix(new double[][] { solArray });

		}

		return solutions;
	}

	public static List<Matrix> getMinimumGrad(IFunction function, int maxIterations, Point point) {

		List<Matrix> solutions = new ArrayList<Matrix>();
		
		Matrix solution;

		if (point!=null) {
			solution = new Matrix(new double[][] {new double[] {point.getX(),point.getY()}});
		}else {
			solution = getInitSolution(function.getNumberOfVariables());

		}

		for (int i = 0; i < maxIterations; i++) {
			double lambda = getLambda(function, solution);
			double[] solArray = solution.getRowPackedCopy();
			
			double[] dArray = function.getGradient(solution).times(-1).getRowPackedCopy();
			
			
																																																for (int j = 0; j < solArray.length; j++) {
				solArray[j] += lambda * dArray[j];
			}
			solution = new Matrix(new double[][] { solArray });
			solutions.add(solution);

		}

		return solutions;
	}

	private static Matrix getInitSolution(int numberOfVariables) {
		Random rand = new Random();
		double[] solution = new double[numberOfVariables];
		for (int i = 0; i < numberOfVariables; i++) {
			solution[i] = -5 + rand.nextDouble() * 10;
		}
		return new Matrix(new double[][] { solution });
	}

	private static double getLambda(IFunction function, Matrix curSolution) {
		double lower = 0;
		double upper = getUpper(function, curSolution);

		double lambda = (lower + upper) / 2;

		Matrix solution = curSolution;
		Matrix d = function.getGradient(solution).times(-1);

		while (true) {
			lambda = (lower + upper) / 2;

			double[] solArray = solution.getRowPackedCopy();
			double[] dArray = d.getRowPackedCopy();

			for (int i = 0; i < solArray.length; i++) {
				solArray[i] += lambda * dArray[i];
			}

			Matrix tempSolution = new Matrix(new double[][] { solArray });
			Matrix gradient = function.getGradient(tempSolution);
			double derivation = gradient.transpose().times(d).det();

			if (Math.abs(derivation) < 1E-6) {
				break;
			}
			if (derivation > 0) {
				upper = lambda;
			} else {
				lower = lambda;
			}

		}

		return lambda;

	}

	private static double getUpper(IFunction function, Matrix curSolution) {

		double upper = 1;
		Matrix solution = curSolution;
		
		Matrix d = function.getGradient(solution).times(-1);
		
		

		while (true) {

			double[] solArray = solution.getRowPackedCopy();
			double[] dArray = d.getRowPackedCopy();

			for (int i = 0; i < solArray.length; i++) {
				solArray[i] += upper * dArray[i];
			}

			Matrix tempSolution = new Matrix(new double[][] { solArray });
			Matrix gradient = function.getGradient(tempSolution);
			Matrix derivationMatrix = gradient.transpose().times(d);
			double derivation = derivationMatrix.det();
			if (Double.isNaN(derivation)) {
				System.out.println("Došlo je do pogreške, pokušajte ponovno!");
				System.exit(1);
			}
			if (derivation > 0 || Math.abs(derivation) < 1E-6) {
				break;
			}
			upper *= 2;
		}

		return upper;
	}

}
