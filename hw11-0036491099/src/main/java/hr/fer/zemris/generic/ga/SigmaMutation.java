package hr.fer.zemris.generic.ga;


import hr.fer.zemris.generic.ga.api.IMutation;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class SigmaMutation implements IMutation<Solution> {

    private int sigma;
    private IRNG rng = RNG.getRNG();


    public SigmaMutation(int sigma) {
        this.sigma = sigma;
    }

    @Override
    public Solution[] mutate(Solution[] kids) {
        return new Solution[]{mutateKid(kids[0]), mutateKid(kids[1])};
    }

    private Solution mutateKid(Solution kid) {
        Solution mutant = (Solution) kid.duplicate();

        int[] mutantValues = mutant.getValues();
        for (int i = 0; i < mutantValues.length; i++) {
            if (rng.nextDouble()<0.01) {
                mutantValues[i] = mutantValues[i] + (int) (rng.nextGaussian() * sigma);
            }
        }
        return mutant;

    }
}
