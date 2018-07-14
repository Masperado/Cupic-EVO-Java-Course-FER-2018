package hr.fer.zemris.optjava.dz10;

import java.util.ArrayList;
import java.util.List;

public class Problem1 implements MOOPProblem {

    @Override
    public int getNumberOfObjectives() {
        return 4;
    }

    @Override
    public int getNumberOfSolutions() {
        return 4;
    }

    @Override
    public double[] evaluateSolution(double[] solution) {
        for (int i = 0; i < solution.length; i++) {
            if (solution[i] < -5 || solution[i] > 5) {
                throw new RuntimeException("Bounds exceeded");
            }
        }
        double[] objective = new double[4];
        for (int i = 0; i < objective.length; i++) {
            objective[i] = solution[i] * solution[i];
        }
        return objective;
    }

    @Override
    public List<double[]> getConstraints() {
        List<double[]> constraints = new ArrayList<>();
        constraints.add(new double[]{-5, 5});
        constraints.add(new double[]{-5, 5});
        constraints.add(new double[]{-5, 5});
        constraints.add(new double[]{-5, 5});
        return constraints;
    }
}
