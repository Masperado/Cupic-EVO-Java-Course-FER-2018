package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

public class Function3 implements IHFunction {

	private Matrix coeff;
	private Matrix consts;

	public Function3(Matrix coeff, Matrix consts) {
		this.coeff = coeff;
		this.consts = consts;
	}

	@Override
	public int getNumberOfVariables() {
		return this.coeff.getColumnDimension();
	}

	@Override
	public double getValue(Matrix xes) {

		Matrix value = coeff.times(xes.transpose());
		double[] constsArray = consts.getRowPackedCopy();
		double[] valueArray = value.getRowPackedCopy();

		double sum = 0;
		for (int i = 0; i < constsArray.length; i++) {
			sum += Math.pow(constsArray[i] - valueArray[i], 2);
		}

		return sum;
	}

	@Override
	public Matrix getGradient(Matrix xes) {
		Matrix value = coeff.times(xes.transpose());
		double[] constsArray = consts.getRowPackedCopy();
		double[] valueArray = value.getRowPackedCopy();
		double[] coeffArray = coeff.getColumnPackedCopy();

		int numberOfVariables = getNumberOfVariables();

		double[] gradArray = new double[numberOfVariables];
		for (int i = 0; i < numberOfVariables; i++) {
			for (int j = 0; j < numberOfVariables; j++) {
				gradArray[i] += 2 * coeffArray[i * numberOfVariables + j] * (valueArray[j] - constsArray[j]);
			}
		}

		return new Matrix(new double[][] { gradArray }).transpose();

	}

	@Override
	public Matrix getHesse() {
		double[] coeffArray = coeff.getColumnPackedCopy();
		int numberOfVariables = getNumberOfVariables();

		double[][] hesseArray = new double[numberOfVariables][numberOfVariables];
		
		for (int i=0;i<numberOfVariables;i++) {
			for (int j=0;j<numberOfVariables;j++) {
				for (int k=0; k<numberOfVariables;k++) {
					hesseArray[i][j]+=2*coeffArray[k*numberOfVariables+i]*coeffArray[k*numberOfVariables+j];
				}
			}
		}
		
		return new Matrix(hesseArray);
		
	}

}
