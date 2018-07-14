package hr.fer.zemris.optjava.dz9;

public class Solution {
    private double[] values;
    private double[] objectives;
    private double goodness;

    public Solution(double[] values) {
        this.values = values;
    }

    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) {
        this.values = values;
    }

    public double[] getObjectives() {
        return objectives;
    }

    public void setObjectives(double[] objectives) {
        this.objectives = objectives;
    }

    public double getGoodness() {
        return goodness;
    }

    public void setGoodness(double goodness) {
        this.goodness = goodness;
    }
}
