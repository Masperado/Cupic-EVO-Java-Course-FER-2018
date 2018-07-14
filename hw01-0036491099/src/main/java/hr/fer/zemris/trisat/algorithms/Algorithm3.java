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
import hr.fer.zemris.trisat.SATFormulaStats;

public class Algorithm3 implements Algorithm {
	
	private static final int MAX_FLIPS = 100000;

	private SATFormula formula;
	SATFormulaStats stats;

	public Algorithm3(SATFormula formula) {
		this.formula = formula;
		this.stats = new SATFormulaStats(formula);
	}

	@Override
	public BitVector solve() {
		Random random = new Random();
		int t = 0;
		BitVector mintermValue = new BitVector(random, formula.getNumberOfVariables());
		BitVector solution = null;

		while (t < MAX_FLIPS) {
			
			if (correctClauses(mintermValue) == this.formula.getNumberOfClauses()) {
				solution = mintermValue;
				break;
			}

			stats.setAssignment(mintermValue, true);

			List<MutableBitVector> neighbours = new ArrayList<>(
					Arrays.asList(new BitVectorNGenerator(mintermValue).createNeighborhood()));

			List<MutableBitVector> bestNeighbours = getBestNeigbours(neighbours);

			mintermValue = bestNeighbours.get(random.nextInt(bestNeighbours.size()));

			
			
			t++;
		}

		return solution;
	}

	private List<MutableBitVector> getBestNeigbours(List<MutableBitVector> neighbours) {
		List<MutableBitVector> bestNeighbours = new ArrayList<>();
		double bestResult = 0;

		for (MutableBitVector neighbour : neighbours) {
			double fitValue = fit(neighbour);
			if (fitValue > bestResult) {
				bestResult = fitValue;
			}
		}

		for (MutableBitVector neighbour : neighbours) {
			double fitValue = fit(neighbour);
			if (Math.abs((fitValue - bestResult)) < 1E-12) {
				bestNeighbours.add(neighbour);
			}
		}

		return bestNeighbours;

	}

	private double fit(BitVector mintermValue) {
		
		stats.setAssignment(mintermValue, false);

		return correctClauses(mintermValue) + stats.getPercentageBonus();
	}

	private int correctClauses(BitVector mintermValue) {
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
