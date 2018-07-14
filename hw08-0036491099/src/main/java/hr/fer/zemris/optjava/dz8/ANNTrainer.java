package hr.fer.zemris.optjava.dz8;

import java.io.IOException;

import hr.fer.zemris.optjava.dz8.api.INeuralNetwork;
import hr.fer.zemris.optjava.dz8.api.IReadOnlyDataset;
import hr.fer.zemris.optjava.dz8.api.ITransferFunction;
import hr.fer.zemris.optjava.dz8.impl.LaserDataset;
import hr.fer.zemris.optjava.dz8.impl.TangensTransferFunction;

public class ANNTrainer {

	public static void main(String[] args) throws IOException {
		if (args.length != 5) {
			System.out.println("NEISPRAVAN BROJ ARGUMENATA");
			System.exit(1);
		}

		String[] architecture = args[1].split("-")[1].split("x");
		ITransferFunction[] functions = new ITransferFunction[architecture.length - 1];
		for (int i = 0; i < functions.length; i++) {
			functions[i] = new TangensTransferFunction();
		}

		int[] layers = new int[architecture.length];
		for (int i = 0; i < layers.length; i++) {
			layers[i] = Integer.parseInt(architecture[i]);
		}

		IReadOnlyDataset dataset = new LaserDataset(args[0], layers[0], -1);
		INeuralNetwork network = null;

		if (args[1].startsWith("tdnn")) {
			network = new TDNN(layers, functions, dataset);
		} else if (args[1].startsWith("elman")) {
			network = new Elman(layers, functions, dataset);
		} else {
			System.out.println("NEIPRAVAN ALGORITAM");
			System.exit(1);
		}

		System.out.println("Imamo uzoraka za ucenje: " + dataset.numberOfSamples());

		int n = Integer.parseInt(args[2]);
		double merr = Double.parseDouble(args[3]);
		int maxIter = Integer.parseInt(args[4]);
		double[] weight = null;
		DE clonalg = new DE(network, n, maxIter, merr);
		weight = clonalg.run();

		network.statistics(weight);
	}

}
