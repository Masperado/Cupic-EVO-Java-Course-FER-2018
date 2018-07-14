package hr.fer.zemris.optjava.dz8.impl;

import hr.fer.zemris.optjava.dz8.api.ITransferFunction;

public class TangensTransferFunction implements ITransferFunction {

	@Override
	public double valueAt(double x) {
		double potency = Math.pow(Math.E, -x);

		return (1.0 - potency) / (1.0 + potency);
	}

}
