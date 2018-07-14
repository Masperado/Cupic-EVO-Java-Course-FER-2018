package hr.fer.zemris.optjava.dz3;

public class PassThroughDecoder implements IDecoder<DoubleArraySolution>{

	@Override
	public double[] decode(DoubleArraySolution solution) {
		return solution.getValues();
	}

}
