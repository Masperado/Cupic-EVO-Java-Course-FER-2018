package hr.fer.zemris.optjava.dz3;


public class NaturalBinaryDecoder extends BitVectorDecoder {

	public NaturalBinaryDecoder(double min, double max, int n, int totalVariables) {
		super(min, max, n, totalVariables);
	}

	@Override
	public double[] decode(BitVectorSolution solution) {

		double[] values = new double[totalVariables];
		String bits = solution.getBits();

		for (int i = 0; i < totalVariables; i++) {
			String valueNBC = bits.substring(i * n, (i + 1) * n);
			int valueInt = intFromNBCString(valueNBC);
			values[i] = min + valueInt * (max - min) / (Math.pow(2, valueNBC.length()) - 1);
		}
		return values;

	}

	private int intFromNBCString(String valueNBC) {
		int solution = 0;

		for (int i = valueNBC.length() - 2; i >= 0; i--) {
			solution += Math.pow(2, i + 1) * Integer.valueOf(valueNBC.substring(i, i + 1));
		}

		return solution;
	}

}
