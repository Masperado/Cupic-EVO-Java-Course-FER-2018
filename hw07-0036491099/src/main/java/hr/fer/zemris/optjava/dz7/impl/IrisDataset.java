package hr.fer.zemris.optjava.dz7.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.optjava.dz7.api.IReadOnlyDataset;

public class IrisDataset implements IReadOnlyDataset {

	private Map<double[], int[]> irisMap;

	public IrisDataset(String path) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(path));
		this.irisMap = parseLinesIntoIrisMap(lines);
	}

	private Map<double[], int[]> parseLinesIntoIrisMap(List<String> lines) {
		Map<double[], int[]> irisMap = new LinkedHashMap<>();

		for (String line : lines) {
			String inputs = line.split(":")[0];
			String outputs = line.split(":")[1];

			String[] inputsSplit = inputs.substring(1, inputs.length() - 1).split(",");
			String[] outputsSplit = outputs.substring(1, outputs.length() - 1).split(",");

			double[] inputsDouble = new double[inputsSplit.length];
			int[] outputsInt = new int[outputsSplit.length];

			for (int i = 0; i < inputsSplit.length; i++) {
				inputsDouble[i] = Double.valueOf(inputsSplit[i]);
			}

			for (int i = 0; i < outputsSplit.length; i++) {
				outputsInt[i] = Integer.valueOf(outputsSplit[i]);
			}

			irisMap.put(inputsDouble, outputsInt);

		}

		return irisMap;
	}

	@Override
	public int[] getOutput(double[] input) {
		return irisMap.get(input);
	}

	@Override
	public Set<double[]> getTestDataset() {
		return irisMap.keySet();
	}

	@Override
	public int numberOfSamples() {
		return irisMap.size();
	}

}
