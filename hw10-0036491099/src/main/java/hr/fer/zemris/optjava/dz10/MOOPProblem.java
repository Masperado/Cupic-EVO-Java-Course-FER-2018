package hr.fer.zemris.optjava.dz10;

import java.util.List;

public interface MOOPProblem {

    int getNumberOfObjectives();

    int getNumberOfSolutions();

    double[] evaluateSolution(double[] solution);

    List<double[]> getConstraints();
}
