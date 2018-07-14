package hr.fer.zemris.optjava.dz9;

import java.util.List;

public class ReturnSolution {

    private List<Solution> population;
    private List<List<Solution>> frontes;

    public ReturnSolution(List<Solution> population, List<List<Solution>>
            frontes) {
        this.population = population;
        this.frontes = frontes;
    }

    public List<Solution> getPopulation() {
        return population;
    }

    public List<List<Solution>> getFrontes() {
        return frontes;
    }
}
