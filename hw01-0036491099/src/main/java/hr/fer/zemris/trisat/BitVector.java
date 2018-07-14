package hr.fer.zemris.trisat;

import java.util.Arrays;
import java.util.Random;

public class BitVector {

	private int numberOfBits;
	protected boolean[] bits;

	public BitVector(Random rand, int numberOfBits) {
		this(rand.nextInt((int) Math.pow(2, numberOfBits)), numberOfBits);
	}

	public BitVector(boolean[] bits) {
		this.bits = bits;
		this.numberOfBits = bits.length;
	}

	public BitVector(int n, int numberOfBits) {
		boolean[] mintermValue = new boolean[numberOfBits];

		for (int i = 0; i < numberOfBits; i++) {
			mintermValue[numberOfBits - i - 1] = n % 2 != 0;
			n /= 2;
		}

		this.bits = mintermValue;
		this.numberOfBits=numberOfBits;
	}

	public boolean get(int index) {
		return bits[index];
	}

	public int getSize() {
		return numberOfBits;
	}

	public boolean[] getBits() {
		return this.bits;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (boolean bool : bits) {
			String value = bool ? "1" : "0";
			sb.append(value);
		}
		return sb.toString();
	}

	public MutableBitVector copy() {
		return new MutableBitVector(Arrays.copyOf(bits, bits.length));
	}
}