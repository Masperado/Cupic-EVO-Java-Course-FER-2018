package hr.fer.zemris.trisat.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.Clause;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;

public class Algorithm5 implements Algorithm {

	private static final int MAX_TRIES = 20;
	private static final int MAX_FLIPS = 100000;
	private static final double RANDOM_FACTOR = 0.2;
	private SATFormula formula;

	public Algorithm5(SATFormula formula) {
		this.formula = formula;
	}

	@Override
	public BitVector solve() {

		BitVector solution = null;

		for (int i = 0; i < MAX_TRIES; i++) {
			Random random = new Random();
			int t = 0;
			BitVector mintermValue = new BitVector(random, formula.getNumberOfVariables());

			while (t < MAX_FLIPS) {

				if (fit(mintermValue) == this.formula.getNumberOfClauses()) {
					solution = mintermValue;
					break;
				}

				Clause unsatisfiedClause = getRandomUnsatisfiedClause(mintermValue, random);
				MutableBitVector vector = mintermValue.copy();

				if (random.nextDouble() < RANDOM_FACTOR) {
					int index = unsatisfiedClause.getLiteral(random.nextInt(unsatisfiedClause.getSize()));
					if (index < 0) {
						index = -index;
					}
					index--;
					vector.set(index, !mintermValue.get(index));
				} else {
					vector = generateBestRandomVariable(mintermValue, vector, unsatisfiedClause);
				}

				t++;
			}

			if (solution != null) {
				break;
			}
		}

		return solution;
	}

	private MutableBitVector generateBestRandomVariable(BitVector bitVector, MutableBitVector vector,
			Clause unsatisfiedClause) {

		List<MutableBitVector> vectors = new ArrayList<>();
		for (int i = 0; i < unsatisfiedClause.getSize(); i++) {
			int index = unsatisfiedClause.getLiteral(i);
			if (index < 0) {
				index = -index;
			}
			index--;
			vector.set(index, !bitVector.get(index));
			vectors.add(vector);
			vector.set(index, bitVector.get(index));
		}

		return getBestNeigbours(vectors).get(0);
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

	private Clause getRandomUnsatisfiedClause(BitVector mintermValue, Random random) {
		List<Clause> unsatisfiedClauses = new ArrayList<>();
		for (int i = 0; i < formula.getNumberOfClauses(); i++) {
			Clause clause = formula.getClause(i);
			if (!clause.isSatisfied(mintermValue)) {
				unsatisfiedClauses.add(clause);
			}
		}
		return unsatisfiedClauses.get(random.nextInt(unsatisfiedClauses.size() - 1));

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
