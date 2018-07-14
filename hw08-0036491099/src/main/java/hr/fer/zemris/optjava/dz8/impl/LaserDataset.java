package hr.fer.zemris.optjava.dz8.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.optjava.dz8.api.IReadOnlyDataset;

public class LaserDataset implements IReadOnlyDataset {

	private Map<double[], double[]> laserMap;
	private List<Double> doubleLines;
	private int min;
	private int max;

	public LaserDataset(String path, int inputNumber, int limit) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(path));
		this.doubleLines = normalizeLines(lines);

		this.laserMap = parseLinesIntoIrisMap(lines, inputNumber, limit);
	}

	private List<Double> normalizeLines(List<String> lines) {
		List<Double> linesDouble = new ArrayList<>();
		List<Integer> linesInt = new ArrayList<>();
		min = Integer.MAX_VALUE;
		max = Integer.MIN_VALUE;

		for (String line : lines) {
			int number = Integer.parseInt(line.trim());
			if (number < min) {
				min = number;
			}
			if (number > max) {
				max = number;
			}
			linesInt.add(number);
		}

		for (int i = 0; i < 600; i++) {
			linesDouble.add(-1.0 + 2 * ((double) linesInt.get(i) - min) / (max - min));
		}

		return linesDouble;
	}

	private Map<double[], double[]> parseLinesIntoIrisMap(List<String> lines, int inputNumber, int limit) {
		Map<double[], double[]> laserMap = new LinkedHashMap<>();
		int counter = 0;
		if (limit == -1) {
			limit = doubleLines.size() - inputNumber;
		}
		while (counter < limit) {
			double[] inputs = new double[inputNumber];
			for (int i = 0; i < inputNumber; i++) {
				inputs[i] = doubleLines.get(counter + i);
			}
			double[] outputs = new double[] { doubleLines.get(counter + inputNumber) };
			laserMap.put(inputs, outputs);
			counter++;
		}

		return laserMap;
	}

	@Override
	public double[] getOutput(double[] input) {
		return laserMap.get(input);
	}

	@Override
	public Set<double[]> getTestDataset() {
		return laserMap.keySet();
	}

	@Override
	public int numberOfSamples() {
		return laserMap.size();
	}

	@Override
	public int getMin() {
		return min;
	}

	@Override
	public int getMax() {
		return max;
	}

}
