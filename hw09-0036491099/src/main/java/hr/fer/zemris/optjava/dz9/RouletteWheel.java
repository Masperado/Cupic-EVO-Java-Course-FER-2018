package hr.fer.zemris.optjava.dz9;


import hr.fer.zemris.optjava.dz9.api.ISelection;

import java.util.List;

public class RouletteWheel implements ISelection<Solution> {

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

        double[] rouletteValues = scale(population);
        double sum = sum(rouletteValues);
        double hit = Math.random() * sum;
        int hitIndex = findIndex(rouletteValues, hit);
        if (hitIndex == population.size()) {
            hitIndex = population.size() - 1;
        }
        return population.get(hitIndex);

    }

    private int findIndex(double[] rouletteValues, double hit) {
        int index = 0;
        double sum = 0;

        for (int i = 0; i < rouletteValues.length; i++) {

            sum += rouletteValues[i];
            if (sum > hit) {
                break;
            } else {
                index++;
            }
        }

        return index;
    }

    private double sum(double[] values) {
        double sum = 0;

        for (int i = 0; i < values.length; i++) {
            sum += values[i];
        }

        return sum;
    }

    private double[] scale(List<Solution> population) {
        double[] values = new double[population.size()];

        for (int i = 0; i < values.length; i++) {
            values[i] = population.get(i).getGoodness();
        }
        return values;
    }

}
