package hr.fer.zemris.optjava.dz10;

import hr.fer.zemris.optjava.dz10.api.ICrossing;
import hr.fer.zemris.optjava.dz10.api.IMutation;
import hr.fer.zemris.optjava.dz10.api.ISelection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NSGAII {

    private MOOPProblem problem;
    private List<Solution> population = new ArrayList<>();
    private List<List<Solution>> frontes = new ArrayList<>();
    private List<Solution> kids = new ArrayList<>();
    private int populationSize;
    private int maxIter;
    private int noOfSolutions;
    private ICrossing<Solution> crossing;
    private IMutation<Solution> mutation;
    private ISelection<Solution> selection;


    public NSGAII(MOOPProblem problem, int populationSize, int maxIter, ICrossing<Solution> crossing, IMutation<Solution>
            mutation, ISelection<Solution> selection) {
        this.problem = problem;
        this.populationSize = populationSize;
        this.maxIter = maxIter;
        this.noOfSolutions = problem.getNumberOfSolutions();
        this.crossing = crossing;
        this.mutation = mutation;
        this.selection = selection;
        initializePopulation();

    }

    private void initializePopulation() {
        List<double[]> constraints = problem.getConstraints();
        int numberOfObjectives = problem.getNumberOfObjectives();
        for (int i = 0; i < populationSize; i++) {
            double[] values = new double[noOfSolutions];
            for (int j = 0; j < values.length; j++) {
                values[j] = constraints.get(j)[0] + Math.random() *
                        (constraints.get(j)[1] - constraints.get(j)[0]);
            }
            Solution solution = new Solution(values, numberOfObjectives);
            solution.setObjectives(problem.evaluateSolution(solution
                    .getValues()));
            population.add(solution);
        }

    }

    public ReturnSolution run() {
        int iterCount = 0;
        while (iterCount < maxIter) {
            intercourse();

            splitIntoFronts();

            calculateDistances();

            makeNewPopulation();

            iterCount++;

            System.out.println(iterCount);
        }
        splitIntoFronts();
        calculateDistances();
        return new ReturnSolution(population, frontes);
    }

    private void calculateDistances() {
        for (List<Solution> front : frontes) {
            for (Solution sol : front) {
                sol.setCrowdingDistance(0);
            }
            int objectiveSize = front.get(0).getObjectiveSize();
            List<Comparator<Solution>> comparators = front.get(0).getComparatorList();
            for (int i = 0; i < objectiveSize; i++) {
                Comparator<Solution> comparator = comparators.get(i);
                front.sort(comparator);

                Solution max = front.get(front.size() - 1);
                Solution min = front.get(0);
                double range = max.getObjectives()[i] - min.getObjectives()[i];

                max.setCrowdingDistance(max.getCrowdingDistance() + 1E9);
                min.setCrowdingDistance(min.getCrowdingDistance() + 1E9);
                if (front.size() < 3) {
                    continue;
                }

                for (int j = 1; j < front.size() - 1; j++) {
                    Solution sol = front.get(j);
                    sol.setCrowdingDistance(sol.getCrowdingDistance() +
                            (front.get(j + 1).getObjectives()[i]
                                    - front.get(j - 1).getObjectives()[i]) / range);
                }

            }

        }
    }

    private void makeNewPopulation() {
        List<Solution> newPopulation = new ArrayList<>();
        int wantedPopulationSize = population.size() / 2;

        for (List<Solution> front : frontes) {
            if ((newPopulation.size() + front.size()) <= wantedPopulationSize) {
                newPopulation.addAll(front);
            } else {
                front.sort((x, y) -> (x.compareTo(y)));
                int i = front.size() - 1;
                while (newPopulation.size() < wantedPopulationSize) {
                    newPopulation.add(front.get(i--));
                }
                break;
            }

        }
        population = new ArrayList<>(newPopulation);

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
        population.addAll(kids);

        for (Solution solution : population) {
            solution.setObjectives(problem.evaluateSolution(solution
                    .getValues()));
        }


    }


    private void splitIntoFronts() {
        frontes.clear();
        List<Solution> used = new ArrayList<>();
        int currentFrontNumber = 0;
        while (true) {
            List<Solution> currentFront = new ArrayList<>();
            for (int i = 0; i < population.size(); i++) {
                Solution element = population.get(i);
                if (used.contains(element)) {
                    continue;
                }
                boolean dominated = false;
                for (Solution solution : population) {
                    if (element == solution) {
                        continue;
                    }

                    if (used.contains(solution)) {
                        continue;
                    }
                    if (dominates(solution.getObjectives(), element
                            .getObjectives())) {
                        dominated = true;
                        break;
                    }
                }
                if (dominated) {
                    continue;
                }
                currentFront.add(element);
                used.add(element);
                element.setFrontNumber(currentFrontNumber);

            }
            if (currentFront.size() == 0) {
                break;
            }
            frontes.add(new ArrayList<>(currentFront));
            currentFront.clear();
            currentFrontNumber++;
        }
    }

    private boolean dominates(double[] candidate, double[] solution) {
        for (int i = 0; i < candidate.length; i++) {
            if (candidate[i] > solution[i]) {
                return false;
            }
        }

        return true;
    }


}
