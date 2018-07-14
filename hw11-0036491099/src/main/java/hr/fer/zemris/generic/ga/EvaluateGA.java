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

public class EvaluateGA {

    private List<Solution> population = new ArrayList<>();
    private List<Solution> kids = new ArrayList<>();
    private ISelection<Solution> selection;
    private ICrossing<Solution> crossing;
    private IMutation<Solution> mutation;
    private double minError;
    private int maxIter;
    private int rectNumber;
    private IRNG rng = RNG.getRNG();
    private GrayScaleImage image;
    private Solution bestSolution;
    Queue<Solution> evaluate = new ConcurrentLinkedQueue<>();
    Queue<Solution> done = new ConcurrentLinkedQueue<>();
    private int populationSize;


    public EvaluateGA(ISelection<Solution> selection, ICrossing<Solution> crossing, IMutation<Solution> mutation,
                      double
                              minError, int maxIter, int rectNumber, GrayScaleImage image, int populationSize) {
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


        for (Solution sol : population) {
            evaluate.offer(sol);
        }
        population.clear();
        for (int i = 0; i < populationSize; i++) {
            Solution temp = done.poll();
            if (temp == null) {
                i--;
                continue;
            }
            population.add(temp);
        }


        while (iterCount < maxIter && bestSolution.fitness < minError) {

            population.add(bestSolution);

            population.sort(GASolution::compareTo);

            population=population.subList(0,populationSize);


            if (population.get(0).fitness > bestSolution.fitness) {
                bestSolution = population.get(0);
            }


            intercourse();

            System.out.println(iterCount++);
            System.out.println(bestSolution.fitness);

        }

        for (int i = 0; i < workersNumber; i++) {
            evaluate.offer(new Solution(null));
        }


        return bestSolution;


    }

    private void intercourse() {
        kids.clear();

        while (true) {

            Solution[] parents = selection.select(population);

            Solution[] kidss = crossing.cross(parents[0], parents[1]);

            Solution[] mutants = mutation.mutate(kidss);

            kids.add(mutants[0]);
            if (kids.size() == population.size()) {
                break;
            }

            kids.add(mutants[1]);
            if (kids.size() == population.size()) {
                break;
            }
        }


        for (Solution sol : kids) {
            evaluate.offer(sol);
        }

        kids.clear();

        for (int i = 0; i < populationSize; i++) {
            Solution temp = done.poll();
            if (temp == null) {
                i--;
                continue;
            }
            kids.add(temp);
        }

        population.addAll(kids);


    }


    private void initializePopulation() {
        for (int i = 0; i < populationSize; i++) {
            int[] values = new int[1 + rectNumber * 5];
            values[0] = rng.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
            for (int j = 1; j < 1 + rectNumber * 5; j = j + 5) {
                values[j] = rng.nextInt(0, image.getWidth());
                values[j + 1] = rng.nextInt(0, image.getHeight());
                values[j + 2] = rng.nextInt(1, image.getWidth());
                values[j + 3] = rng.nextInt(1, image.getHeight());
                values[j + 4] = rng.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
            }
            population.add(new Solution(values));
        }
        bestSolution = population.get(0);
        bestSolution.fitness = -1000000000;
    }


    private class Job implements Runnable {

        @Override
        public void run() {
            while (true) {
                Solution solution = evaluate.poll();
                if (solution == null) {
                    continue;
                }

                if (solution.getValues() == null) {
                    break;
                }
                Evaluator ev = EV.getEvaluator();
                ev.evaluate(solution);
                done.offer(solution);
            }
        }
    }
}
