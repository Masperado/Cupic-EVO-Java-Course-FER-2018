package hr.fer.zemris.trisat;

public class Clause {
	private int[] indexes;

	public Clause(int[] indexes) {
		this.indexes = indexes;
	}

	public int getSize() {
		return indexes.length;
	}

	public int getLiteral(int index) {
		return indexes[index];
	}

	public boolean isSatisfied(BitVector assignment) {

		boolean[] mintermValue = assignment.getBits();

		boolean clauseResult = false;

		for (int varNumber : indexes) {
			clauseResult = varNumber > 0 ? mintermValue[varNumber - 1] : !mintermValue[-varNumber - 1];
			if (clauseResult) {
				break;
			}
		}
		return clauseResult;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int index : indexes) {
			sb.append(index);
		}
		sb.append("\n");
		return sb.toString();
	}
}