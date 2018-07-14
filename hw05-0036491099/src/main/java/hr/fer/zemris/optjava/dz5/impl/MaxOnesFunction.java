package hr.fer.zemris.optjava.dz5.impl;

import hr.fer.zemris.optjava.dz5.api.IFunction;

public final class MaxOnesFunction implements IFunction<boolean[]> {

	@Override
	public double valueAt(boolean[] point) {
		final int k = numberOfOnes(point);
		final int n = point.length;
		if (k <= (0.8 * n)) {
			return (double) k / n;
		} else if (k <= (0.9 * n)) {
			return 0.8;
		} else {
			return (2 * (double) k / n) - 1;
		}

	}

	private int numberOfOnes(boolean[] point) {
		int numberOfOnes = 0;

		for (int i = 0; i < point.length; i++) {
			if (point[i]) {
				numberOfOnes++;
			}
		}
		return numberOfOnes;
	}

}
