package hr.fer.zemris.trisat;

public class SATFormula {
	private int numberOfVariables;
	private Clause[] clauses;

	public SATFormula(int numberOfVariables, Clause[] clauses) {
		this.numberOfVariables = numberOfVariables;
		this.clauses = clauses;
	}

	public int getNumberOfVariables() {
		return this.numberOfVariables;
	}

	public int getNumberOfClauses() {
		return this.clauses.length;
	}

	public Clause getClause(int index) {
		return this.clauses[index];
	}

	public boolean isSatisfied(BitVector assignment) {
		boolean overallResult = true;
		for (Clause clause: clauses) {
			boolean clauseResult = clause.isSatisfied(assignment);
			if (!clauseResult) {
				overallResult = false;
				break;
			}
		}
		return overallResult;
		
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Clause cl : clauses) {
			sb.append(cl);
		}
		return sb.toString();
	}
}