package hr.fer.zemris.optjava.dz9;


import hr.fer.zemris.optjava.dz9.api.IMutation;

import java.util.List;

public class SigmaMutation implements IMutation<Solution> {

    private double sigma;
    private List<double[]> constraints;

    public SigmaMutation(double sigma, List<double[]> constraints) {
        this.sigma = sigma;
        this.constraints = constraints;
    }

    @Override
    public Solution[] mutate(Solution[] kids) {
        return new Solution[]{mutateKid(kids[0]), mutateKid(kids[1])};
    }

    private Solution mutateKid(Solution kid) {
        Solution mutant = new Solution(kid.getValues().clone());

        double[] mutantValues = mutant.getValues();
        for (int i = 0; i < mutantValues.length; i++) {
            mutantValues[i] = mutantValues[i] - sigma + 2 * Math.random() *
                    sigma;

            // TODO:
            if (mutantValues[i] < constraints.get(i)[0]) {
                mutantValues[i] = constraints.get(i)[0];
            }
            if (mutantValues[i] > constraints.get(i)[1]) {
                mutantValues[i] = constraints.get(i)[1];
            }

        }
        return mutant;

    }
}
