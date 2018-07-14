package hr.fer.zemris.optjava.dz3;

public abstract class SingleObjectiveSolution {
	
	protected double fitness;
	
	public double compareTo(SingleObjectiveSolution solution) {
		return fitness -solution.fitness;
	}
	
	public abstract void calculateFitness(IDecoder decoder, IFunction function);
	
	public abstract String stringRepresentation(IDecoder decoder);
	
}
