package hr.fer.zemris.optjava.dz7.impl;

import hr.fer.zemris.optjava.dz7.api.ITransferFunction;

public class SigmoidTransferFunction implements ITransferFunction {

	@Override
	public double valueAt(double x) {
		return 1.0 / (1.0 + Math.pow(Math.E, -x));
	}

}
