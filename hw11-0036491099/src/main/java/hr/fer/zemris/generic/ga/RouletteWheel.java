package hr.fer.zemris.generic.ga;


import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.api.ISelection;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

import java.util.List;

public class RouletteWheel implements ISelection<Solution> {

    private int rectNumber;
    private GrayScaleImage image;

    public RouletteWheel(int rectNumber, GrayScaleImage image) {
        this.rectNumber = rectNumber;
        this.image = image;
    }

    IRNG rng = RNG.getRNG();

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

        if (population.size() == 0) {
            return generateNewOne();
        }

        double[] rouletteValues = scale(population);
        double sum = sum(rouletteValues);
        double hit = Math.random() * sum;
        int hitIndex = findIndex(rouletteValues, hit);
        if (hitIndex >= population.size()) {
            hitIndex = population.size() - 1;
        }
        if (hitIndex < 0) {
            hitIndex = 0;
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
            values[i] = -1 / population.get(i).fitness;
        }
        return values;
    }

    private Solution generateNewOne() {
        int[] values = new int[1 + rectNumber * 5];
        values[0] = rng.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
        for (int j = 1; j < 1 + rectNumber * 5; j = j + 5) {
            values[j] = rng.nextInt(0, image.getWidth());
            values[j + 1] = rng.nextInt(0, image.getHeight());
            values[j + 2] = rng.nextInt(1, image.getWidth() / 2);
            values[j + 3] = rng.nextInt(1, image.getHeight() / 2);
            values[j + 4] = rng.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
        }
        Solution temp = new Solution(values);
        return temp;
    }

}
