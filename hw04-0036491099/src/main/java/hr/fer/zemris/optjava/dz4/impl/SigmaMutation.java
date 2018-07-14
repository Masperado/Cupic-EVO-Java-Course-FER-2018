package hr.fer.zemris.optjava.dz4.impl;

import hr.fer.zemris.optjava.dz4.api.IMutation;

public class SigmaMutation implements IMutation<double[]> {

	private double sigma;

	public SigmaMutation(double sigma) {
		this.sigma = sigma;
	}

	@Override
	public double[][] mutate(double[][] kids) {
		return new double[][] { mutateKid(kids[0]), mutateKid(kids[1]) };
	}

	private double[] mutateKid(double[] kid) {
		double[] mutant = kid.clone();

		for (int i = 0; i < mutant.length; i++) {
			mutant[i] = mutant[i] - sigma + 2 * Math.random() * sigma;
		}
		return mutant;

	}
}
