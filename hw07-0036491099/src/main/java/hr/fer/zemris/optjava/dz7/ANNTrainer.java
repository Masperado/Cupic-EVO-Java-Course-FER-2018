package hr.fer.zemris.optjava.dz7;

import java.io.IOException;

import hr.fer.zemris.optjava.dz7.api.IReadOnlyDataset;
import hr.fer.zemris.optjava.dz7.api.ITransferFunction;
import hr.fer.zemris.optjava.dz7.impl.IrisDataset;
import hr.fer.zemris.optjava.dz7.impl.SigmoidTransferFunction;

public class ANNTrainer {

	public static void main(String[] args) throws IOException {
		if (args.length != 5) {
			System.out.println("NEISPRAVAN BROJ ARGUMENATA");
			System.exit(1);
		}

		IReadOnlyDataset dataset = new IrisDataset(args[0]);
		System.out.println("Imamo uzoraka za ucenje: " + dataset.numberOfSamples());
		FFANN ffann = new FFANN(new int[] { 4, 3, 3 },
				new ITransferFunction[] { new SigmoidTransferFunction(), new SigmoidTransferFunction() }, dataset);

		int n = Integer.parseInt(args[2]);
		double merr = Double.parseDouble(args[3]);
		int maxIter = Integer.parseInt(args[4]);
		double[] weight = null;
		if (args[1].equals("pso-a")) {
			PSOG psog = new PSOG(ffann, n, 2.05, 2.05, -2, 2, maxIter, merr);
			weight = psog.run();
		} else if (args[1].startsWith("pso-b")) {
			int neigbourhoodSize = Integer.parseInt(args[1].split("-")[2]);
			PSOL psol = new PSOL(ffann, n, 2.05, 2.05, -2, 2, neigbourhoodSize, maxIter, merr);
			weight = psol.run();
		} else if (args[1].equals("clonalg")) {
			CLONALG clonalg = new CLONALG(ffann, n, maxIter, merr);
			weight = clonalg.run();
		} else {
			System.out.println("NEIPRAVAN ALGORITAM");
			System.exit(1);
		}

		ffann.statistics(weight);
	}

}
