package hr.fer.zemris.optjava.dz3;

import java.util.Random;

public class BinaryUnifNeighborhood implements INeighborhood<BitVectorSolution> {

	private Random rand;

	public BinaryUnifNeighborhood(Random rand) {
		this.rand = rand;
	}

	@Override
	public BitVectorSolution randomNeighbour(BitVectorSolution solution) {
		BitVectorSolution neighbour = solution.duplicate();
		neighbour.randomize(rand);
		return neighbour;

	}

}
