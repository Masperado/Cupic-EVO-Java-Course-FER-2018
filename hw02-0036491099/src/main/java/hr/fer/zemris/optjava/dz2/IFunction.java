package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

public interface IFunction {
	
	public int getNumberOfVariables();
	
	public double getValue(Matrix xes);
	
	public Matrix getGradient(Matrix xes);

}
