package hr.fer.zemris.generic.ga;

import hr.fer.zemris.art.EV;
import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.api.GASolution;
import hr.fer.zemris.generic.ga.api.ICrossing;
import hr.fer.zemris.generic.ga.api.IMutation;
import hr.fer.zemris.generic.ga.api.ISelection;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;
import hr.fer.zemris.optjava.rng.rngimpl.EVOThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ChildGA {

    private List<Solution> population = new ArrayList<>();
    private List<Solution> kids = new ArrayList<>();
    private List<Solution> mutants = new ArrayList<>();
    private ISelection<Solution> selection;
    private ICrossing<Solution> crossing;
    private IMutation<Solution> mutation;
    private double minError;
    private int maxIter;
    private int rectNumber;
    private IRNG rng = RNG.getRNG();
    private GrayScaleImage image;
    private Solution bestSolution;
    Queue<Integer> evaluate = new ConcurrentLinkedQueue<>();
    Queue<Solution> done = new ConcurrentLinkedQueue<>();
    private int populationSize;


    public ChildGA(ISelection<Solution> selection, ICrossing<Solution> crossing, IMutation<Solution> mutation,
                   double minError, int maxIter, int rectNumber, GrayScaleImage image, int populationSize) {
        this.selection = selection;
        this.crossing = crossing;
        this.mutation = mutation;
        this.minError = minError;
        this.maxIter = maxIter;
        this.rectNumber = rectNumber;
        this.image = image;
        this.populationSize = populationSize;
        initializePopulation();
    }

    public Solution run() {

        int workersNumber = Runtime.getRuntime().availableProcessors();
        EVOThread[] workers = new EVOThread[workersNumber];
        for (int i = 0; i < workersNumber; i++) {
            workers[i] = new EVOThread(new Job(), image);
            workers[i].start();
        }

        int iterCount = 0;

        while (iterCount < maxIter && bestSolution.fitness < minError) {


            for (int i = 0; i < workersNumber*4; i++) {
                evaluate.offer(4);
            }
            kids.clear();
            for (int i = 0; i < workersNumber*4*4; i++) {
                Solution temp = done.poll();
                if (temp == null) {
                    i--;
                    continue;
                }
                kids.add(temp);
            }
            population = new ArrayList<>();
            population.addAll(kids);

            population.add(bestSolution);

            population.sort(GASolution::compareTo);

            population=population.subList(0,populationSize);


            if (population.get(0).fitness > bestSolution.fitness) {
                bestSolution = population.get(0);
            }


            System.out.println(bestSolution.fitness);
            System.out.println(iterCount++);

        }

        for (int i = 0; i < workersNumber; i++) {
            evaluate.add(0);
        }

        System.out.println(bestSolution.fitness);

        return bestSolution;


    }

    private class InitJob implements Runnable{
        @Override
        public void run() {
            for (int i = 0; i < populationSize; i++) {
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
                Evaluator ev = EV.getEvaluator();
                ev.evaluate(temp);

                population.add(temp);
            }
            population.sort(GASolution::compareTo);
            bestSolution = population.get(0);
        }
    }

    private void initializePopulation() {
        EVOThread thread = new EVOThread(new InitJob(),image);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    private class Job implements Runnable {

        @Override
        public void run() {
            while (true) {
                Integer howManyKids = evaluate.poll();
                if (howManyKids == null) {
                    continue;
                }

                if (howManyKids == 0) {
                    break;
                }
                for (int i = 0; i < howManyKids; i++) {
                    Solution kid = intercourse();
                    Evaluator ev = EV.getEvaluator();
                    ev.evaluate(kid);
                    done.offer(kid);

                }
            }
        }

        private Solution intercourse() {

            Solution[] parents = selection.select(population);

            Solution[] kidss = crossing.cross(parents[0], parents[1]);

            Solution[] mutants = mutation.mutate(kidss);

            if (mutants[0].fitness > mutants[1].fitness) {
                return mutants[0];
            } else {
                return mutants[1];
            }

        }
    }
}
