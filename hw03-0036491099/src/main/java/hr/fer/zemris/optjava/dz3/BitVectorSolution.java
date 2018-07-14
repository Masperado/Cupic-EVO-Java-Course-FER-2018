package hr.fer.zemris.optjava.dz3;

import java.util.Random;

public class BitVectorSolution extends SingleObjectiveSolution {

	private String bits;

	public BitVectorSolution(String bits) {
		this.bits = bits;
	}

	public String getBits() {
		return bits;
	}

	public void randomize(Random rand) {
		int bytePosition1 = rand.nextInt(bits.length()/6);
		int bytePosition2 = bits.length()/6+rand.nextInt(bits.length()/6);
		int bytePosition3 = 2*bits.length()/6+rand.nextInt(bits.length()/6);
		int bytePosition4 = 3*bits.length()/6+rand.nextInt(bits.length()/6);
		int bytePosition5 = 4*bits.length()/6+rand.nextInt(bits.length()/6);
		int bytePosition6 = 5*bits.length()/6+rand.nextInt(bits.length()/6);

		
		char[] charArray = bits.toCharArray();
		charArray[bytePosition1] = charArray[bytePosition1]=='0'?'1':'0';
		charArray[bytePosition2] = charArray[bytePosition2]=='0'?'1':'0';
		charArray[bytePosition3] = charArray[bytePosition3]=='0'?'1':'0';
		charArray[bytePosition4] = charArray[bytePosition4]=='0'?'1':'0';
		charArray[bytePosition5] = charArray[bytePosition5]=='0'?'1':'0';
		charArray[bytePosition6] = charArray[bytePosition6]=='0'?'1':'0';

		bits = fromNBCCharArray(charArray);
	}

	private String fromNBCCharArray(char[] charArray) {
		StringBuilder sb = new StringBuilder();
		
		for (int i=0;i<charArray.length;i++) {
			sb.append(charArray[i]);
		}
		
		return sb.toString();
	}

	public BitVectorSolution duplicate() {
		return new BitVectorSolution(bits.substring(0));
	}

	@Override
	public void calculateFitness(IDecoder decoder, IFunction function) {
		this.fitness = function.valueAt(decoder.decode(this));
	}


	@Override
	public String toString() {
		return bits;
	}

	@Override
	public String stringRepresentation(IDecoder decoder) {
		double[] values = decoder.decode(this);

		StringBuilder sb = new StringBuilder();
		sb.append("A = " + values[0] + "\n");
		sb.append("B = " + values[1] + "\n");
		sb.append("C = " + values[2] + "\n");
		sb.append("D = " + values[3] + "\n");
		sb.append("E = " + values[4] + "\n");
		sb.append("F = " + values[5] + "\n");

		return sb.toString();
	}

}
