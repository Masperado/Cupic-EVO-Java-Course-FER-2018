package hr.fer.zemris.optjava.dz5.impl;

import hr.fer.zemris.optjava.dz5.api.IMutation;

public class RateMutation implements IMutation<int[]> {

	private double rate;

	public RateMutation(double rate) {
		this.rate = rate;
	}

	@Override
	public int[][] mutate(int[][] kids) {
		return new int[][] { mutateKid(kids[0]), mutateKid(kids[1]) };
	}

	private int[] mutateKid(int[] kid) {
		int[] mutant = kid.clone();

		for (int i = 0; i < kid.length; i++) {
			if (Math.random() < rate) {
				int j = (int) (kid.length * Math.random());
				int temp = kid[i];
				kid[i] = kid[j];
				kid[j] = temp;
			}
		}
		return mutant;
	}

}
