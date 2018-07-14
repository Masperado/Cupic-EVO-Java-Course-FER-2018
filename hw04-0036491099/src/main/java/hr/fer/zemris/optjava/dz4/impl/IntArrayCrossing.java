package hr.fer.zemris.optjava.dz4.impl;

import java.util.Map;
import java.util.TreeMap;

import hr.fer.zemris.optjava.dz4.api.ICrossing;

public class IntArrayCrossing implements ICrossing<int[]> {

	@Override
	public int[][] cross(int[] parent1, int[] parent2) {
		int[] firstChild = generateChild(parent1, parent2);
		int[] secondChild = generateChild(parent1, parent2);
		return new int[][] { firstChild, secondChild };
	}

	private int[] generateChild(int[] parent1, int[] parent2) {
		int[] child = new int[parent1.length];

		int startPos = (int) (Math.random() * parent1.length);
		int endPos = (int) (Math.random() * parent1.length);

		if (startPos > endPos) {
			int temp = endPos;
			endPos = startPos;
			startPos = temp;
		}

		Map<Integer, Integer> numberOfEachStick = getNumber(parent1);
		Map<Integer, Integer> childMap = new TreeMap<>();
		for (int i = 0; i < parent1.length; i++) {
			childMap.put(parent1[i], Integer.valueOf(0));
		}

		for (int i = 0; i < child.length; i++) {
			if (startPos < endPos && i > startPos && i < endPos) {
				child[i] = parent1[i];
				Integer oldValue = childMap.get(child[i]);
				Integer newValue = Integer.valueOf(oldValue.intValue() + 1);
				childMap.put(child[i], newValue);
			} else if (startPos > endPos) {
				if (!(i < startPos && i > endPos)) {
					child[i] = parent1[i];
					Integer oldValue = childMap.get(child[i]);
					Integer newValue = Integer.valueOf(oldValue.intValue() + 1);
					childMap.put(child[i], newValue);
				}
			}
		}

		for (int i = 0; i < parent2.length; i++) {
			if (allowedIndex(parent1, parent2, childMap, numberOfEachStick, parent2[i])) {
				for (int j = 0; j < child.length; j++) {
					if (child[j] == 0) {
						child[j] = parent2[i];
						Integer oldValue = childMap.get(child[j]);
						Integer newValue = Integer.valueOf(oldValue.intValue() + 1);
						childMap.put(child[j], newValue);
						break;
					}
				}
			}
		}

		return child;
	}

	private boolean allowedIndex(int[] parent1, int[] parent2, Map<Integer, Integer> childMap,
			Map<Integer, Integer> numberOfEachStick, int i) {
		return childMap.get(i).intValue() < numberOfEachStick.get(i).intValue();
	}

	private Map<Integer, Integer> getNumber(int[] parent1) {
		Map<Integer, Integer> numbers = new TreeMap<>();
		for (int i = 0; i < parent1.length; i++) {
			numbers.put(parent1[i], Integer.valueOf(0));
		}

		for (int i = 0; i < parent1.length; i++) {
			Integer oldValue = numbers.get(parent1[i]);
			Integer newValue = Integer.valueOf(oldValue.intValue() + 1);
			numbers.put(parent1[i], newValue);
		}

		return numbers;
	}

}
