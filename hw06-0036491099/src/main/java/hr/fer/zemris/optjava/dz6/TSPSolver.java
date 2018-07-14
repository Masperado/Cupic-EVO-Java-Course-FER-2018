package hr.fer.zemris.optjava.dz6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.dz6.model.City;

public class TSPSolver {

	public static void main(String[] args) throws IOException {
		if (args.length != 4) {
			System.out.println("Neispravan broj argumenata");
			System.exit(1);
		}

		List<String> lines = Files.readAllLines(Paths.get(args[0]));
		if (!lines.get(4).split(" ")[1].equals("EUC_2D")) {
			System.out.println("Unesite problem u EUC_2D obliku!");
			System.exit(1);
		}

		int dimension = Integer.valueOf(lines.get(3).split(" ")[1]);

		List<City> cityList = new ArrayList<>(dimension);
		for (int i = 6; i < (6 + dimension); i++) {
			String[] cityArgs = lines.get(i).split(" ");
			cityList.add(new City(Double.valueOf(cityArgs[1]), Double.valueOf(cityArgs[2]),
					Integer.valueOf(cityArgs[0]) - 1));
		}

		for (City city : cityList) {
			city.generateCandidatesList(cityList, Integer.valueOf(args[1]));
		}

		int maxGenerations = Integer.valueOf(args[3]);
		int maxAnts = Integer.valueOf(args[2]);
		double alfa = 2;
		double beta = 5;
		double ro = 0.02;
		double e = 1;

		MMAS mmas = new MMAS(maxGenerations, maxAnts, alfa, beta, cityList, ro, e);
		mmas.run();
	}

}
