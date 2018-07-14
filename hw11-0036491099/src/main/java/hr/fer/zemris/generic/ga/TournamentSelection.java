package hr.fer.zemris.generic.ga;


import hr.fer.zemris.generic.ga.api.GASolution;
import hr.fer.zemris.generic.ga.api.ISelection;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

import java.util.ArrayList;
import java.util.List;

public class TournamentSelection implements ISelection<Solution> {

    private IRNG rng = RNG.getRNG();
    private int groupSize;
    List<Solution> randomPopulation;

    public TournamentSelection(int groupSize) {
        this.groupSize = groupSize;
        this.randomPopulation = new ArrayList<>(groupSize);
    }

    @Override
    public Solution[] select(List<Solution> population) {
        Solution parent1 = selectParent(population, null);
        Solution parent2 = selectParent(population, parent1);

        while (parent1 == parent2) {
            parent2 = selectParent(population, parent1);
            System.out.println("tuuu2");
        }

        return new Solution[]{parent1, parent2};
    }

    public synchronized Solution selectParent(List<Solution> population, Solution parent1) {

        randomPopulation.clear();

        for (int i = 0; i < groupSize; i++) {
            try {
                Solution choosen = population.get(rng.nextInt(0, population.size()));
                if (randomPopulation.contains(choosen)) continue;
                randomPopulation.add(choosen);
            } catch (IndexOutOfBoundsException ex){
                System.out.println(Thread.currentThread().getId());
                ex.printStackTrace();
            }
        }

        if (randomPopulation.size()<2){
            randomPopulation.add(population.get(0));
            randomPopulation.add(population.get(1));
        }

        randomPopulation.sort(GASolution::compareTo);



        Solution returnSolution = randomPopulation.get(0);

        if (returnSolution == parent1) {
            returnSolution = randomPopulation.get(1);
        }


        return returnSolution;


    }


}
