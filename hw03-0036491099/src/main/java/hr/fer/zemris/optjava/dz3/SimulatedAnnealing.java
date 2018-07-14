package hr.fer.zemris.optjava.dz3;

public class SimulatedAnnealing<T extends SingleObjectiveSolution> implements IOptAlgorithm<T> {

	private IDecoder<T> decoder;
	private INeighborhood<T> neighborhood;
	private T startWith;
	private IFunction function;
	private boolean minimize;
	private ITempSchedule tempSchedule;

	public SimulatedAnnealing(IDecoder<T> decoder, INeighborhood<T> neighborhood, T startWith, IFunction function,
			boolean minimize, ITempSchedule tempSchedule) {
		this.decoder = decoder;
		this.neighborhood = neighborhood;
		this.startWith = startWith;
		this.function = function;
		this.minimize = minimize;
		this.tempSchedule = tempSchedule;
	}

	@Override
	public void run() {
		int outerLoopLimit = tempSchedule.getOuterLoopLimit();
		int innerLoopLimit = tempSchedule.getInnerLoopLimit();

		T solution = startWith;
		for (int i = 0; i < outerLoopLimit; i++) {
			System.out.println(i);
			double temp = tempSchedule.getNextTemperature();
			for (int j = 0; j < innerLoopLimit; j++) {
				T neighbour = neighborhood.randomNeighbour(solution);
				solution.calculateFitness(decoder, function);
				neighbour.calculateFitness(decoder, function);

				double distance = neighbour.compareTo(solution);
				if (!minimize) {
					distance = -distance;
				}

				if (distance < 0) {
					solution = neighbour;
				} else {
					double a = Math.random();
					double b = Math.exp(-distance / temp);
					//double b = -0.4*Math.pow(0.9,i-1);
					
					if (a > b) {
						solution = neighbour;
					}
				}
			}
		}
		
		System.out.println(solution.stringRepresentation(decoder));
		System.out.println("Iznos pogreške: " + solution.fitness);
	}

}
