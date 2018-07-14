package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.SATFormula;


public class Algorithm1 implements Algorithm{
	

	private SATFormula formula;
	
	public Algorithm1(SATFormula formula) {
		this.formula = formula;
	}

	public BitVector solve() {

		int mintermNumber = 0;
		
		BitVector firstResultFound = null;

		while (mintermNumber < Math.pow(2, formula.getNumberOfVariables())) {
			BitVector mintermValue = new BitVector(mintermNumber, formula.getNumberOfVariables());

			boolean overallResult = formula.isSatisfied(mintermValue);

			if (overallResult) {
				if (firstResultFound==null) {
					firstResultFound=mintermValue;
				}
				System.out.println(mintermValue);
			}

			mintermNumber++;
		}

		return firstResultFound;
	}

	

}
