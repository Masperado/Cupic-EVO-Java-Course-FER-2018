package hr.fer.zemris.optjava.dz5.impl;

import hr.fer.zemris.optjava.dz5.api.IFunction;

public class QAPFunction implements IFunction<int[]> {

	private int[][] distances;
	private int[][] quantites;

	public QAPFunction(int[][] distances, int[][] quantites) {
		this.distances = distances;
		this.quantites = quantites;
	}

	@Override
	public double valueAt(int[] point) {
		double cost = 0;
		for (int i = 0; i < point.length; i++) {
			for (int j = 0; j < point.length; j++) {
				cost += distances[i][j] * quantites[point[i]][point[j]];
			}
		}
		return cost;
	}

}
