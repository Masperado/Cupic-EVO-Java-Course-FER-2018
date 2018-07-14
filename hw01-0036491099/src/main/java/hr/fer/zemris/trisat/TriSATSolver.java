package hr.fer.zemris.trisat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.trisat.algorithms.Algorithm;
import hr.fer.zemris.trisat.algorithms.Algorithm1;
import hr.fer.zemris.trisat.algorithms.Algorithm2;
import hr.fer.zemris.trisat.algorithms.Algorithm3;
import hr.fer.zemris.trisat.algorithms.Algorithm4;
import hr.fer.zemris.trisat.algorithms.Algorithm5;
import hr.fer.zemris.trisat.algorithms.Algorithm6;

public class TriSATSolver {

	public static void main(String[] args) throws IOException {

		if (args.length != 2) {
			System.out.println("Invalid number of arguments!");
			System.exit(1);
		}

		int algNumber = Integer.valueOf(args[0]);
		Path path = Paths.get(args[1]);

		List<String> rows = Files.readAllLines(path);

		int definitionStartingNumber = 0;

		while (rows.get(definitionStartingNumber).startsWith("c")) {
			definitionStartingNumber++;
		}

		int numberOfVariables = Integer.valueOf(rows.get(definitionStartingNumber).split(" ")[2].trim());
		int numberOfClauses = Integer.valueOf(rows.get(definitionStartingNumber).split(" ")[4].trim());

		List<String> clauses = rows.subList(definitionStartingNumber + 2,
				definitionStartingNumber + 1 + numberOfClauses);
		clauses.add(0, rows.get(definitionStartingNumber + 1).trim());

		Clause[] clausesArray = new Clause[clauses.size()];
		for (int i = 0; i < clauses.size(); i++) {
			String[] indexStrings = clauses.get(i).split(" ");
			int[] indexInts = new int[indexStrings.length-1];
			for (int j = 0; j < indexStrings.length-1; j++) {
				indexInts[j] = Integer.parseInt(indexStrings[j]);
			}
			clausesArray[i] = new Clause(indexInts);
		}
		
		SATFormula formula = new SATFormula(numberOfVariables, clausesArray);

		BitVector solution = null;
		Algorithm alg = null;

		switch (algNumber) {
		case 1:
			alg = new Algorithm1(formula);
			break;
		case 2:
			alg = new Algorithm2(formula);
			break;
		case 3:
			alg = new Algorithm3(formula);
			break;
		case 4:
			alg = new Algorithm4(formula);
			break;
		case 5:
			alg = new Algorithm5(formula);
			break;
		case 6:
			alg = new Algorithm6(formula);
			break;
		}
		solution = alg.solve();

		System.out.print("\nZadovoljivo: ");
		if (solution != null) {
			System.out.println(solution);
		} else {
			System.out.println("Nije pronađeno nijedno rješenje.");
		}

	}

}
