package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

public class Function1 implements IHFunction{
	
	@Override
	public int getNumberOfVariables() {
		return 2;
	}

	@Override
	public double getValue(Matrix xes) {
		double[] xesArray = xes.getRowPackedCopy();
		return xesArray[0]*xesArray[0]+(xesArray[1]-1)*(xesArray[1]-1);
	}

	@Override
	public Matrix getGradient(Matrix xes) {

		double[] xesArray = xes.getRowPackedCopy();
		
		double[] gradArray = new double[] {2*xesArray[0], 2*(xesArray[1]-1)};
		
		return new Matrix(new double[][] {gradArray}).transpose();
	}

	@Override
	public Matrix getHesse() {
		return new Matrix(new double[][] {new double[] {2,0}, new double[] {0,2}});
	}

}
