package hr.fer.zemris.optjava.dz4.impl;

import hr.fer.zemris.optjava.dz4.api.IFunction;

public class StickFunction implements IFunction<int[]> {

	private int height;

	public StickFunction(int height) {
		this.height = height;
	}

	@Override
	public double valueAt(int[] point) {
		int length = 1;
		int tempHeight = 0;
		for (int i = 0; i < point.length; i++) {
			tempHeight += point[i];
			if (tempHeight == height) {
				length++;
				tempHeight = 0;
			} else if (tempHeight > 20) {
				length++;
				tempHeight = 0;
				i--;
			}
		}
		return length;

	}

}
