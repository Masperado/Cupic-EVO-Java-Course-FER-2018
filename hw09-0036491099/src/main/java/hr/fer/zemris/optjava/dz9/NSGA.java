package hr.fer.zemris.optjava.dz9;

import hr.fer.zemris.optjava.dz9.api.ICrossing;
import hr.fer.zemris.optjava.dz9.api.IMutation;
import hr.fer.zemris.optjava.dz9.api.ISelection;

import java.util.ArrayList;
import java.util.List;

public class NSGA {

    private MOOPProblem problem;
    private List<Solution> population = new ArrayList<>();
    private List<List<Solution>> frontes = new ArrayList<>();
    private List<Solution> kids = new ArrayList<>();
    private int populationSize;
    private int maxIter;
    private boolean decisionSpace;
    private int noOfSolutions;
    private int noOfObjectives;
    private Solution minimumSolution;
    private Solution maximumSolution;
    private ICrossing<Solution> crossing;
    private IMutation<Solution> mutation;
    private ISelection<Solution> selection;


    public NSGA(MOOPProblem problem, int populationSize, int maxIter, boolean
            decisionSpace, ICrossing<Solution> crossing, IMutation<Solution>
                        mutation, ISelection<Solution> selection) {
        this.problem = problem;
        this.populationSize = populationSize;
        this.maxIter = maxIter;
        this.decisionSpace = decisionSpace;
        this.noOfSolutions = problem.getNumberOfSolutions();
        this.noOfObjectives = problem.getNumberOfObjectives();
        this.crossing = crossing;
        this.mutation = mutation;
        this.selection = selection;
        initializePopulation();

    }

    private void initializePopulation() {
        List<double[]> constraints = problem.getConstraints();
        for (int i = 0; i < populationSize; i++) {
            double[] values = new double[noOfSolutions];
            for (int j = 0; j < values.length; j++) {
                values[j] = constraints.get(j)[0] + Math.random() *
                        (constraints.get(j)[1] - constraints.get(j)[0]);
            }
            Solution solution = new Solution(values);
            solution.setObjectives(problem.evaluateSolution(solution
                    .getValues()));
            population.add(solution);
        }

    }

    public ReturnSolution run() {
        int iterCount = 0;
        while (iterCount < maxIter) {
            splitIntoFronts();

            calculateGoodness();

            intercourse();

            iterCount++;

            System.out.println(iterCount);
        }
        splitIntoFronts();
        calculateGoodness();
        return new ReturnSolution(population, frontes);
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
        population = new ArrayList<>(kids);

        for (Solution solution : population) {
            solution.setObjectives(problem.evaluateSolution(solution
                    .getValues()));
        }


    }


    private void calculateGoodness() {
        double sigmaShare = 1;
        double epsilon = 0.99;
        double Fmin = population.size() * (1 / epsilon);
        double alfa = 2;

        for (int i = 0; i < frontes.size(); i++) {
            List<Solution> front = frontes.get(i);


            if (decisionSpace) {
                double[] minimums = new double[problem.getNumberOfSolutions()];
                double[] maximums = new double[problem
                        .getNumberOfSolutions()];
                for (int j = 0; j < minimums.length; j++) {
                    final int index = j;
                    minimums[j] = front.stream().mapToDouble(a -> a.getValues()
                            [index]).min().getAsDouble();
                    maximums[j] = front.stream().mapToDouble(a -> a.getValues()
                            [index]).max().getAsDouble();

                }
                minimumSolution = new Solution(minimums);
                maximumSolution = new Solution(maximums);
            } else {
                double[] minimums = new double[problem.getNumberOfObjectives()];
                double[] maximums = new double[problem.getNumberOfObjectives()];
                for (int j = 0; j < minimums.length; j++) {
                    final int index = j;
                    minimums[j] = front.stream().mapToDouble(a -> a
                            .getObjectives()
                            [index]).min().getAsDouble();
                    maximums[j] = front.stream().mapToDouble(a -> a
                            .getObjectives()
                            [index]).max().getAsDouble();

                }
                minimumSolution = new Solution(minimums);
                minimumSolution.setObjectives(minimums);
                maximumSolution = new Solution(maximums);
                maximumSolution.setObjectives(maximums);
            }

            for (Solution solution : front) {
                solution.setGoodness(Fmin * epsilon);
                double nci = 0;
                for (Solution solution1 : front) {
                    if (solution == solution1) {
                        continue;
                    }
                    double d = calculateD(solution, solution1);
                    double sh = 1 - Math.pow(d / sigmaShare, alfa);
                    if (sh < 0) {
                        sh = 0;
                    }
                    nci += sh;
                }
                if (nci == 0 || Double.isNaN(nci)) {
                    nci = 1;
                }

                if (Double.isNaN(nci)) {
                    System.out.println("aa");
                }

                solution.setGoodness(solution.getGoodness() / nci);


            }

            Fmin = front.stream().mapToDouble(a -> a.getGoodness()).min()
                    .getAsDouble();

        }


    }

    private double calculateD(Solution solution, Solution solution1) {
        double d = 0;
        if (decisionSpace) {
            double[] value = solution.getValues();
            double[] value1 = solution1.getValues();
            double[] maximumValues = maximumSolution.getValues();
            double[] minimumValues = minimumSolution.getValues();
            for (int i = 0; i < value.length; i++) {
                d += Math.pow((value[i] - value1[i]) /
                        (maximumValues[i] - minimumValues[i]), 2);
            }

        } else {
            double[] objective = solution.getObjectives();
            double[] objective1 = solution1.getObjectives();
            double[] maximumObjectives = maximumSolution.getObjectives();
            double[] minimumObjectives = minimumSolution.getObjectives();
            for (int i = 0; i < objective.length; i++) {
                d += Math.pow((objective[i] - objective1[i]) /
                        (maximumObjectives[i] - minimumObjectives[i]), 2);
            }
        }
        return Math.sqrt(d);
    }

    private void splitIntoFronts() {
        frontes.clear();
        List<Solution> used = new ArrayList<>();
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

            }
            if (currentFront.size() == 0) {
                break;
            }
            frontes.add(new ArrayList<>(currentFront));
            currentFront.clear();
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
