package hr.fer.zemris.generic.ga;

import hr.fer.zemris.generic.ga.api.ICrossing;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class OnePointCrossing implements ICrossing<Solution> {

    private IRNG rng = RNG.getRNG();


    @Override
    public Solution[] cross(Solution parent1, Solution parent2) {


        Solution firstChild = generateChild(parent1, parent2);
        Solution secondChild = generateChild(parent1, parent2);
        return new Solution[]{firstChild, secondChild};


    }

    private Solution generateChild(Solution parent1, Solution parent2) {
        int[] childValues = new int[parent1.getValues().length];
        int[] parent1Values = parent1.getValues();
        int[] parent2Values = parent2.getValues();

        int position = rng.nextInt(0,parent1Values.length);

        for (int i = 0; i < childValues.length; i++) {
            if (i<position) {
                childValues[i] = parent1Values[i];
            } else {
                childValues[i] = parent2Values[i];
            }
        }
//
//
//        for (int i = 0; i < childValues.length; i++) {
//            if (rng.nextBoolean()) {
//                childValues[i] = parent1Values[i];
//            } else {
//                childValues[i] = parent2Values[i];
//            }
//        }


        return new Solution(childValues);
    }
}
