package hr.fer.zemris.optjava.dz3;

public abstract class BitVectorDecoder implements IDecoder<BitVectorSolution> {

	protected double min;
	protected double max;
	protected int n;
	protected int totalVariables;

	public BitVectorDecoder(double min, double max, int n, int totalVariables) {
		this.min = min;
		this.max = max;
		this.n = n;
		this.totalVariables = totalVariables;
	}

	public abstract double[] decode(BitVectorSolution solution);

}
