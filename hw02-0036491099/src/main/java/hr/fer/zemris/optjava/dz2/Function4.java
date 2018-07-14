package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import Jama.Matrix;

public class Function4 implements IHFunction {

	private Matrix coeff;
	private Matrix consts;

	public Function4(Matrix coeff, Matrix consts) {
		this.coeff = coeff;
		this.consts = consts;
	}

	// (x1*A+x2*A^3*B+x3*x5^(x4*C)(1+cos(x5*D))+x6*D*E^2-KONST)^2

	// x1 -> 2*A*result
	// x2 -> 2*A^3*B*result
	// x3 -> 2*x5^(C*x4)*(cos(D*x5)+1)*(x3*x5^(C*x4)*(cos(D*x5)+1)-K)
	// x4 -> 2*C*x3*log(x5)*x5^(C*x4)*(cos(D*x5)+1)*(x3*x5^(C*x4)*(cos(D*x5)+1)-K)
	// x5 ->
	// 2*x3*x5^(C*x4-1)*(C*x4*cos(D*x5)+C*x4-D*x5*sin(D*x5))*(x3*x5^(C*x4)*(cos(D*x5)+1)-K))
	// x6 -> 2*D*E^2*result

	@Override
	public int getNumberOfVariables() {
		return 6;
	}

	@Override
	public double getValue(Matrix xes) {
		double[] xesArray = xes.getRowPackedCopy();
		double[] coeffArray = coeff.getRowPackedCopy();
		double[] constsArray = consts.getRowPackedCopy();

		double error = 0;

		for (int i = 0; i < constsArray.length; i++) {
			double x1 = Math.abs(xesArray[0]);
			double x2 = Math.abs(xesArray[1]);
			double x3 = Math.abs(xesArray[2]);
			double x4 = Math.abs(xesArray[3]);
			double x5 = Math.abs(xesArray[4]);
			double x6 = Math.abs(xesArray[5]);
			double A = coeffArray[i * 5 + 0];
			double B = coeffArray[i * 5 + 1];
			double C = coeffArray[i * 5 + 2];
			double D = coeffArray[i * 5 + 3];
			double E = coeffArray[i * 5 + 4];
			double K = constsArray[i];

			double result = x1 * A + x2 * A * A * A * B + x3 * Math.pow(x5, x4 * C) * (1 + Math.cos(x5 * D))
					+ x6 * D * E * E - K;
			result *= result;
			error += result;
		}

		return error;

	}

	// (x1*A+x2*A^3*B+x3*x5^(x4*C)(1+cos(x5*D))+x6*D*E^2-KONST)^2

	// x1 -> 2*A*result
	// x2 -> 2*A^3*B*result
	// x3 -> 2*x5^(C*x4)*(cos(D*x5)+1)*(x3*x5^(C*x4)*(cos(D*x5)+1)-K)
	// x4 -> 2*C*x3*log(x5)*x5^(C*x4)*(cos(D*x5)+1)*(x3*x5^(C*x4)*(cos(D*x5)+1)-K)
	// x5 ->
	// 2*x3*x5^(C*x4-1)*(C*x4*cos(D*x5)+C*x4-D*x5*sin(D*x5))*(x3*x5^(C*x4)*(cos(D*x5)+1)-K))
	// x6 -> 2*D*E^2*result

	@Override
	public Matrix getGradient(Matrix xes) {
		double[] xesArray = xes.getRowPackedCopy();
		double[] coeffArray = coeff.getRowPackedCopy();
		double[] constsArray = consts.getRowPackedCopy();
		double[] gradArray = new double[6];


		for (int i = 0; i < constsArray.length; i++) {
			double x1 = Math.abs(xesArray[0]);
			double x2 = Math.abs(xesArray[1]);
			double x3 = Math.abs(xesArray[2]);
			double x4 = Math.abs(xesArray[3]);
			double x5 = Math.abs(xesArray[4]);
			double x6 = Math.abs(xesArray[5]);
			double A = coeffArray[i * 5 + 0];
			double B = coeffArray[i * 5 + 1];
			double C = coeffArray[i * 5 + 2];
			double D = coeffArray[i * 5 + 3];
			double E = coeffArray[i * 5 + 4];
			double K = constsArray[i];

			double result = x1 * A + x2 * A * A * A * B + x3 * Math.pow(x5, x4 * C) * (1 + Math.cos(x5 * D))
					+ x6 * D * E * E - K;

			gradArray[0] += 2 * A * result;
			gradArray[1] += 2 * A * A * A * B * result;
			gradArray[2] += 2 * Math.pow(x5, (C * x4)) * (Math.cos(D * x5) + 1)
					* (x3 * Math.pow(x5, (C * x4)) * (Math.cos(D * x5) + 1) - K);
			gradArray[3] += 2 * C * x3 * Math.log(x5) * Math.pow(x5, (C * x4)) * (Math.cos(D * x5) + 1)
					* (x3 * Math.pow(x5, (C * x4)) * (Math.cos(D * x5) + 1) - K);
			gradArray[4] += 2 * x3 * Math.pow(x5, (C * x4 - 1))
					* (C * x4 * Math.cos(D * x5) + C * x4 - D * x5 * Math.sin(D * x5))
					* (x3 * Math.pow(x5, (C * x4)) * (Math.cos(D * x5) + 1) - K);
			gradArray[5] += 2 * D * E * E * result;

		}

		RealVector v = new ArrayRealVector(gradArray);
		try {
			v.unitize();
		} catch (Exception e) {
			System.out.println();
		}
		gradArray = v.toArray();

		return new Matrix(new double[][] { gradArray }).transpose();
	}
	
	@Override
	public Matrix getHesse() {
		
		// Posao nalaženja hesseove matrice je izvan mojih matematičkih sposobnosti
		return null;
	}

}
