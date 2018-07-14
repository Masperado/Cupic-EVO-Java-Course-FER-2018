package hr.fer.zemris.trisat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BitVectorNGenerator implements Iterable<MutableBitVector> {

	private BitVector assignment;

	public BitVectorNGenerator(BitVector assignment) {
		this.assignment = assignment;

	}

	@Override
	public Iterator<MutableBitVector> iterator() {
		return new Iterator<MutableBitVector>() {
			
			private int i=0;
			

			@Override
			public boolean hasNext() {
				return i<BitVectorNGenerator.this.assignment.getSize();
			}

			@Override
			public MutableBitVector next() {
				MutableBitVector vector = BitVectorNGenerator.this.assignment.copy();
				vector.set(i, !vector.get(i));
				i++;
				return new MutableBitVector(vector.bits);
			}

		};

	}

	public MutableBitVector[] createNeighborhood() {
		List<MutableBitVector> vectors = new ArrayList<>();
		Iterator<MutableBitVector> it = iterator();
		while(it.hasNext()) {
			vectors.add(it.next());
		}
		return vectors.toArray(new MutableBitVector[vectors.size()]);
	}
}