package hr.fer.zemris.optjava.dz4.impl;

import hr.fer.zemris.optjava.dz4.api.ICrossing;

public class BLX implements ICrossing<double[]> {

	private double alfa;

	public BLX(double alfa) {
		this.alfa = alfa;
	}

	@Override
	public double[][] cross(double[] parent1, double[] parent2) {
		double[] firstChild = generateChild(parent1, parent2);
		double[] secondChild = generateChild(parent1, parent2);
		return new double[][] { firstChild, secondChild };
	}

	private double[] generateChild(double[] parent1, double[] parent2) {
		double[] child = new double[parent1.length];

		for (int i = 0; i < parent1.length; i++) {
			double cMin = parent1[i];
			double cMax = parent2[i];
			double I = cMax - cMin;

			if (cMin > cMax) {
				double temp = cMax;
				cMax = cMin;
				cMin = temp;
			}

			child[i] = cMin - I * alfa + Math.random() * (cMax + I * alfa - cMin + I * alfa);

		}
		return child;

	}
}
