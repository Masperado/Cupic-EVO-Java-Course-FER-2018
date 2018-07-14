package hr.fer.zemris.trisat;

public class SATFormulaStats {

	public static final int numberOfBest = 2;
	public static final double percentageConstantUp = 0.01;
	public static final double percentageConstantDown = 0.1;
	public static final double percentageUnitAmount = 50;

	private SATFormula formula;
	private double[] percentages;
	private int numberOfSatisfied;
	private double percentageBonus;

	public SATFormulaStats(SATFormula formula) {
		this.formula = formula;
		this.percentages = new double[formula.getNumberOfClauses()];
	}

	public void setAssignment(BitVector assignment, boolean updatePercentages) {
		if (updatePercentages) {
			int trueClauses = 0;
			for (int i = 0; i < formula.getNumberOfClauses(); i++) {
				Clause clause = formula.getClause(i);
				if (clause.isSatisfied(assignment)) {
					trueClauses++;
					percentages[i] += (1 - percentages[i]) * percentageConstantUp;
				} else {
					percentages[i] += (0 - percentages[i]) * percentageConstantDown;
				}
			}
			this.numberOfSatisfied = trueClauses;
		} else {
			int trueClauses = 0;
			percentageBonus = 0;
			for (int i = 0; i < formula.getNumberOfClauses(); i++) {
				Clause clause = formula.getClause(i);
				if (clause.isSatisfied(assignment)) {
					trueClauses++;
					percentageBonus += percentageUnitAmount * (1 - percentages[i]);
				} else {
					percentageBonus -= percentageUnitAmount * (1 - percentages[i]);
				}
			}
			this.numberOfSatisfied = trueClauses;
		}
	}

	public int getNumberOfSatisfied() {
		return numberOfSatisfied;
	}

	public boolean isSatisfied() {
		return numberOfSatisfied == this.formula.getNumberOfClauses();
	}

	public double getPercentageBonus() {
		return percentageBonus;
	}

	public double getPercentage(int index) {
		return percentages[index];
	}
}