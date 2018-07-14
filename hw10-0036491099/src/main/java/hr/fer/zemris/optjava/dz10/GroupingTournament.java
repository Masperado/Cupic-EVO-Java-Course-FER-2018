package hr.fer.zemris.optjava.dz10;


import hr.fer.zemris.optjava.dz10.api.ISelection;

import java.util.List;
import java.util.Random;

public class GroupingTournament implements ISelection<Solution> {

    @Override
    public Solution[] select(List<Solution> population) {
        Solution parent1 = selectParent(population);
        Solution parent2 = selectParent(population);

        while (parent1 == parent2) {
            parent2 = selectParent(population);
        }

        return new Solution[]{parent1, parent2};
    }

    public Solution selectParent(List<Solution> population) {

        Random rand = new Random();
        Solution sol1 = population.get(rand.nextInt(population.size()));
        Solution sol2 = population.get(rand.nextInt(population.size()));
        if (sol1.compareTo(sol2) > 0) {
            return sol1;
        } else {
            return sol2;
        }

    }


}
