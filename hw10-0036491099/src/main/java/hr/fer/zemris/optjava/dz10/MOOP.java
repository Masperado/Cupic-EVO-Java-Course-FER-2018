package hr.fer.zemris.optjava.dz10;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class MOOP {

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 3) {
            System.out.println("Invalid number of arguments");
            System.exit(1);
        }

        MOOPProblem problem = null;

        int fja = Integer.parseInt(args[0]);
        if (fja == 1) {
            problem = new Problem1();
        } else {
            problem = new Problem2();
        }
        int populationSize = Integer.parseInt(args[1]);

        int maxIter = Integer.parseInt(args[2]);

        NSGAII nsga = new NSGAII(problem, populationSize, maxIter,
                new BLX
                        (0.01), new
                SigmaMutation(0.01, problem.getConstraints()), new
                GroupingTournament());


        ReturnSolution returnSolution = nsga.run();

        List<Solution> population = returnSolution.getPopulation();
        List<List<Solution>> frontes = returnSolution.getFrontes();

        System.out.println("\n" + "BROJ FRONTI: " + returnSolution.getFrontes
                ().size());

        PrintWriter pw = new PrintWriter(new File("izlaz-dec.csv"));
        StringBuilder sb = new StringBuilder();

        for (Solution solution : population) {
            double[] values = solution.getValues();
            for (int i = 0; i < values.length; i++) {
                sb.append(values[i]);
                sb.append(",");
            }
            sb.setLength(sb.length() - 1);
            sb.append("\n");
        }
        pw.write(sb.toString());
        pw.close();

        PrintWriter pw2 = new PrintWriter(new File("izlaz-obj.csv"));
        StringBuilder sb2 = new StringBuilder();

        for (Solution solution : population) {
            double[] objectives = solution.getObjectives();
            for (int i = 0; i < objectives.length; i++) {
                sb2.append(objectives[i]);
                sb2.append(",");
            }
            sb2.setLength(sb2.length() - 1);
            sb2.append("\n");
        }
        pw2.write(sb2.toString());
        pw2.close();
    }
}
