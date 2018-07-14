package hr.fer.zemris.trisat.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.Clause;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;

public class Algorithm2 implements Algorithm {

	private static final int MAX_FLIPS = 100000;

	private SATFormula formula;

	public Algorithm2(SATFormula formula) {
		this.formula = formula;
	}

	@Override
	public BitVector solve() {
		Random random = new Random();
		int t = 0;
		BitVector mintermValue = new BitVector(random, formula.getNumberOfVariables());
		BitVector solution = null;

		while (t < MAX_FLIPS) {

			if (fit(mintermValue) == this.formula.getNumberOfClauses()) {
				solution = mintermValue;
				break;
			}

			List<MutableBitVector> neighbours = new ArrayList<>(
					Arrays.asList(new BitVectorNGenerator(mintermValue).createNeighborhood()));

			List<MutableBitVector> bestNeighbours = getBestNeigbours(neighbours);

			if (fit(mintermValue) > fit(bestNeighbours.get(0))) {
				break;
			}

			mintermValue = bestNeighbours.get(random.nextInt(bestNeighbours.size()));

			t++;
		}

		return solution;
	}

	private List<MutableBitVector> getBestNeigbours(List<MutableBitVector> neighbours) {
		List<MutableBitVector> bestNeighbours = new ArrayList<>();
		int bestResult = 0;

		for (MutableBitVector neighbour : neighbours) {
			int fitValue = fit(neighbour);
			if (fitValue > bestResult) {
				bestResult = fitValue;
			}
		}

		for (MutableBitVector neighbour : neighbours) {
			int fitValue = fit(neighbour);
			if (fitValue == bestResult) {
				bestNeighbours.add(neighbour);
			}
		}

		return bestNeighbours;

	}

	private int fit(BitVector mintermValue) {

		int trueClauses = 0;

		for (int i = 0; i < formula.getNumberOfClauses(); i++) {
			Clause clause = formula.getClause(i);
			boolean clauseResult = clause.isSatisfied(mintermValue);
			if (clauseResult) {
				trueClauses++;
			}

		}

		return trueClauses;
	}

}
