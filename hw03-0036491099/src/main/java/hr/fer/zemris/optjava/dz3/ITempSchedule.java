package hr.fer.zemris.optjava.dz3;

public interface ITempSchedule {
	
	double getNextTemperature();
	
	int getInnerLoopLimit();
	
	int getOuterLoopLimit();
	
	void resetTemp();

}
