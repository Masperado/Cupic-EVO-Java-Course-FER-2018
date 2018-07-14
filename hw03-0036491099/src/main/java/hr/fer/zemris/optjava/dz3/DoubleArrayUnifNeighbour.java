package hr.fer.zemris.optjava.dz3;

import java.util.Random;

public class DoubleArrayUnifNeighbour implements INeighborhood<DoubleArraySolution>{

	private double[] deltas;
	private Random rand;
	
	
	
	public DoubleArrayUnifNeighbour(double[] deltas, Random rand) {
		this.deltas = deltas;
		this.rand = rand;
	}


	@Override
	public DoubleArraySolution randomNeighbour(DoubleArraySolution solution) {
		DoubleArraySolution neighbour = solution.duplicate();
		neighbour.randomize(rand, deltas[0], deltas[1]);
		return neighbour;
	}

}
