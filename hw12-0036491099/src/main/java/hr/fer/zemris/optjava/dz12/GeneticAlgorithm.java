package hr.fer.zemris.optjava.dz12;

import hr.fer.zemris.optjava.dz12.functions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {

    private List<Solution> parents;
    private List<Solution> kids;
    private int populationSize;
    private int maxGeneration;
    private int minKilaJanjetine;
    private Random random;

    public GeneticAlgorithm(int maxGeneration, int populationSize, int minKilaJanjetine) {
        this.populationSize = populationSize;
        this.maxGeneration = maxGeneration;
        this.minKilaJanjetine = minKilaJanjetine;
        this.random = new Random();
        this.parents = new ArrayList<>(populationSize + 1);
        this.kids = new ArrayList<>(populationSize + 1);
        this.initPopulation();
    }

    public Solution run() {
        int iterCount = 0;
        evaluate();
        while (parents.get(0).getKilaJanjetine() < minKilaJanjetine && iterCount < maxGeneration) {
            generateKids();
            evaluate();
            iterCount++;
            System.out.println(parents.get(0).getKilaJanjetine());
        }
        return parents.get(0);
    }

    private void generateKids() {
        kids.clear();
        kids.add(parents.get(0));

        for (int i = 0; i < populationSize; i++) {

            double hit = random.nextDouble();

            if (hit < 0.84) {
                cross();
            } else if (hit < 0.99) {
                mutate();
            } else {
                reproduce();
            }
        }

        parents.clear();
        parents.addAll(kids);
    }

    private void cross() {
        Solution parent1 = tournament();
        Solution parent2 = tournament();

        IFunction root1 = parent1.rootCopy();
        root1.setDepth(0);
        IFunction root2 = parent2.rootCopy();
        root2.setDepth(0);

        List<IFunction> root1List = root1.functionsBelow();
        root1List.add(root1);
        List<IFunction> root2List = root2.functionsBelow();
        root2List.add(root2);

        if (root1List.size() == 1) {
            kids.add(parent2);
            return;
        }

        if (root2List.size() == 1) {
            kids.add(parent1);
            return;
        }

        IFunction rand1 = root1List.get(random.nextInt(root1List.size()));
        IFunction rand2 = root2List.get(random.nextInt(root2List.size()));

        rand1.setA(rand2.getA());

        Solution kid = new Solution(root1);


        if (kid.getRoot().getBelow() > 200) {
            kids.add(parent1);
            return;
        }

        kids.add(kid);

    }

    private void mutate() {
        Solution parent1 = tournament();
        IFunction root1 = parent1.rootCopy();
        root1.setDepth(0);
        List<IFunction> root1List = root1.functionsBelow();
        root1List.add(root1);

        if (root1List.size() == 1) {
            kids.add(new Solution(generateTerminalFunction()));
            return;
        }

        IFunction rand1 = root1List.get(random.nextInt(root1List.size()));

        int currentDepth = rand1.getDepth();
        int wantedDepth = currentDepth+3;


        rand1.setA(generateFullFunction(currentDepth, wantedDepth));

        Solution kid = new Solution(root1);

        if (kid.rootCopy().getBelow() > 200) {
            kids.add(parent1);
            return;
        }

        kids.add(kid);
    }

    private void reproduce() {
        Solution parent1 = tournament();
        kids.add(parent1);

    }

    private Solution tournament() {
        Solution parent = parents.get(random.nextInt(parents.size()));
        for (int i = 0; i < 6; i++) {
            Solution candidate = parents.get(random.nextInt(parents.size()));
            if (candidate.getKilaJanjetine() > parent.getKilaJanjetine()) {
                parent = candidate;
            }
        }
        return parent;
    }

    private void evaluate() {
        for (Solution solution : parents) {
            solution.run();
        }
        parents.sort((o1, o2) -> Integer.compare(o2.getKilaJanjetine(), o1.getKilaJanjetine()));
    }


    private void initPopulation() {

        for (int i = 0; i < populationSize; i++) {
            if (i < (populationSize / 10)) {
                parents.add(generateFullSolution(2));
            } else if (i < (2 * populationSize / 10)) {
                parents.add(generateGrowSolution(2));
            } else if (i < (3 * populationSize / 10)) {
                parents.add(generateFullSolution(3));
            } else if (i < (4 * populationSize / 10)) {
                parents.add(generateGrowSolution(3));
            } else if (i < (5 * populationSize / 10)) {
                parents.add(generateFullSolution(4));
            } else if (i < (6 * populationSize / 10)) {
                parents.add(generateGrowSolution(4));
            } else if (i < (7 * populationSize / 10)) {
                parents.add(generateFullSolution(5));
            } else if (i < (8 * populationSize / 10)) {
                parents.add(generateGrowSolution(5));
            } else if (i < (9 * populationSize / 10)) {
                parents.add(generateFullSolution(6));
            } else if (i < (10 * populationSize / 10)) {
                parents.add(generateGrowSolution(6));
            }
        }
    }

    private Solution generateFullSolution(int maxDepth) {
        IFunction function = generateFullFunction(0, maxDepth);
        return new Solution(function);
    }


    private Solution generateGrowSolution(int maxDepth) {
        IFunction function = generateGrowFunction(0, maxDepth);
        return new Solution(function);
    }

    private IFunction generateFullFunction(int currentDepth, int maxDepth) {
        if (currentDepth == maxDepth) {
            return generateTerminalFunction();
        }
        switch (random.nextInt(3)) {
            case 0:
                IfFoodAhead ahead = new IfFoodAhead();
                ahead.setA(generateFullFunction(currentDepth + 1, maxDepth));
                ahead.setB(generateFullFunction(currentDepth + 1, maxDepth));
                return ahead;
            case 1:
                Prog2 prog2 = new Prog2();
                prog2.setA(generateFullFunction(currentDepth + 1, maxDepth));
                prog2.setB(generateFullFunction(currentDepth + 1, maxDepth));
                return prog2;
            case 2:
                Prog3 prog3 = new Prog3();
                prog3.setA(generateFullFunction(currentDepth + 1, maxDepth));
                prog3.setB(generateFullFunction(currentDepth + 1, maxDepth));
                prog3.setC(generateFullFunction(currentDepth + 1, maxDepth));
                return prog3;
            default:
                return new Move();
        }
    }

    private IFunction generateGrowFunction(int currentDepth, int maxDepth) {
        if (currentDepth == maxDepth) {
            return generateTerminalFunction();
        }
        switch (random.nextInt(6)) {
            case 0:
                return new Left();
            case 1:
                return new Right();
            case 2:
                return new Move();
            case 3:
                IfFoodAhead ahead = new IfFoodAhead();
                ahead.setA(generateGrowFunction(currentDepth + 1, maxDepth));
                ahead.setB(generateGrowFunction(currentDepth + 1, maxDepth));
                return ahead;
            case 4:
                Prog2 prog2 = new Prog2();
                prog2.setA(generateGrowFunction(currentDepth + 1, maxDepth));
                prog2.setB(generateGrowFunction(currentDepth + 1, maxDepth));
                return prog2;
            case 5:
                Prog3 prog3 = new Prog3();
                prog3.setA(generateGrowFunction(currentDepth + 1, maxDepth));
                prog3.setB(generateGrowFunction(currentDepth + 1, maxDepth));
                prog3.setC(generateGrowFunction(currentDepth + 1, maxDepth));
                return prog3;
            default:
                return new Move();
        }
    }

    private IFunction generateTerminalFunction() {
        switch (random.nextInt(3)) {
            case 0:
                return new Left();
            case 1:
                return new Right();
            case 2:
                return new Move();
            default:
                return new Move();
        }
    }
}
