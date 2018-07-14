package hr.fer.zemris.optjava.dz10;

import java.util.ArrayList;
import java.util.List;

public class Problem2 implements MOOPProblem {

    @Override
    public int getNumberOfObjectives() {
        return 2;
    }

    @Override
    public int getNumberOfSolutions() {
        return 2;
    }

    @Override
    public double[] evaluateSolution(double[] solution) {
        if (solution[0] < 0.1 || solution[0] > 1) {
            throw new RuntimeException("Bounds exceeded");
        }
        if (solution[1] < 0 || solution[1] > 5) {
            throw new RuntimeException("Bounds exceeded");
        }


        double[] objective = new double[2];
        objective[0] = solution[0];
        objective[1] = (1 + solution[1]) / solution[0];
        return objective;
    }

    @Override
    public List<double[]> getConstraints() {
        List<double[]> constraints = new ArrayList<>();
        constraints.add(new double[]{0.1, 1});
        constraints.add(new double[]{0, 5});
        return constraints;
    }
}
