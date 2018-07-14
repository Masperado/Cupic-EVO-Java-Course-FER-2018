package hr.fer.zemris.optjava.dz3;

import Jama.Matrix;

public class MyFunction implements IFunction {

	private Matrix coeff;
	private Matrix consts;

	public MyFunction(Matrix coeff, Matrix consts) {
		this.coeff = coeff;
		this.consts = consts;
	}

	@Override
	public double valueAt(double[] point) {
		double[] xesArray = point;
		double[] coeffArray = coeff.getRowPackedCopy();
		double[] constsArray = consts.getRowPackedCopy();

		double error = 0;

		for (int i = 0; i < constsArray.length; i++) {
			double a = xesArray[0];
			double b = xesArray[1];
			double c = xesArray[2];
			double d = xesArray[3];
			double e = xesArray[4];
			double f = xesArray[5];
			double x1 = coeffArray[i * 5 + 0];
			double x2 = coeffArray[i * 5 + 1];
			double x3 = coeffArray[i * 5 + 2];
			double x4 = coeffArray[i * 5 + 3];
			double x5 = coeffArray[i * 5 + 4];
			double K = constsArray[i];

			double result = (a * x1) + (b * Math.pow(x1, 3) * x2) + (c * Math.exp(d * x3) * (1 + Math.cos(e * x4)))
					+ (f * x4 * Math.pow(x5, 2)) - K;
			result *= result;
			error += result;
		}

		return Math.sqrt(error);
	}

}
