package hr.fer.zemris.trisat;

public class MutableBitVector extends BitVector {
	

	public MutableBitVector(boolean[] bits) {
		super(bits);
	}

	public MutableBitVector(int n, int numberOfBits) {
		super(n, numberOfBits);

	}

	public void set(int index, boolean value) {
		this.bits[index]=value;
	}
}