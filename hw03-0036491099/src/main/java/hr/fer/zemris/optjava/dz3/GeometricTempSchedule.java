package hr.fer.zemris.optjava.dz3;

public class GeometricTempSchedule implements ITempSchedule {

	public double alpha;
	public double tInitial;
	public double tCurrent;
	public int innerLimit;
	public int outerLimit;

	public GeometricTempSchedule(double alpha, double tInitial, int innerLimit, int outerLimit) {
		this.alpha = alpha;
		this.tInitial = tInitial;
		this.innerLimit = innerLimit;
		this.outerLimit = outerLimit;

		this.tCurrent = tInitial;
	}

	@Override
	public double getNextTemperature() {
		tCurrent = alpha * tInitial;
		return tCurrent;
	}

	@Override
	public int getInnerLoopLimit() {
		return this.innerLimit;
	}

	@Override
	public int getOuterLoopLimit() {
		return this.outerLimit;
	}

	@Override
	public void resetTemp() {
		tCurrent = tInitial;
	}

}
