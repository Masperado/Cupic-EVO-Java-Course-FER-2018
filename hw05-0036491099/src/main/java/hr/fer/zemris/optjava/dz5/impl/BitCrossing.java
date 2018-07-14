package hr.fer.zemris.optjava.dz5.impl;

import hr.fer.zemris.optjava.dz5.api.ICrossing;

public class BitCrossing implements ICrossing<boolean[]> {

	@Override
	public boolean[][] cross(boolean[] parent1, boolean[] parent2) {
		boolean[] child1 = generateChild(parent1, parent2);
		boolean[] child2 = generateChild(parent1, parent2);

		return new boolean[][] { child1, child2 };
	}

	private boolean[] generateChild(boolean[] parent1, boolean[] parent2) {
		boolean[] kid = new boolean[parent1.length];
		for (int i = 0; i < parent1.length; i++) {
			if (Math.random() > 0.5) {
				kid[i] = parent1[i];
			} else {
				kid[i] = parent2[i];
			}
			// AKO ŽELIŠ VIDJETI ŠTO JE PRAVO KRIŽANJE ODKOMENTIRAJ OVO!!!
			// if (parent1[i]) {
			// kid[i]=parent1[i];
			// } else {
			// kid[i]=parent2[i];
			// }
		}
		return kid;
	}

}
