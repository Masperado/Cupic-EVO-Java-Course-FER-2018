package hr.fer.zemris.optjava.dz5.impl;

import hr.fer.zemris.optjava.dz5.api.IMutation;

public class BitMutation implements IMutation<boolean[]> {

	private double mutationRate;

	public BitMutation(double mutationRate) {
		this.mutationRate = mutationRate;
	}

	@Override
	public boolean[][] mutate(boolean[][] kids) {
		boolean[] mutant1 = generateMutant(kids[0]);
		boolean[] mutant2 = generateMutant(kids[1]);

		return new boolean[][] { mutant1, mutant2 };
	}

	private boolean[] generateMutant(boolean[] kid) {
		boolean[] mutant = new boolean[kid.length];
		for (int i = 0; i < kid.length; i++) {
			if (mutationRate > Math.random()) {
				if (kid[i]) {
					mutant[i] = false;
				} else {
					mutant[i] = true;
				}
			} else {
				mutant[i] = kid[i];
			}
		}
		return mutant;
	}

}
