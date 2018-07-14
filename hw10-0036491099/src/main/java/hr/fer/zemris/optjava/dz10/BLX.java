package hr.fer.zemris.optjava.dz10;


import hr.fer.zemris.optjava.dz10.api.ICrossing;

public class BLX implements ICrossing<Solution> {

    private double alfa;

    public BLX(double alfa) {
        this.alfa = alfa;
    }

    @Override
    public Solution[] cross(Solution parent1, Solution parent2) {
        Solution firstChild = generateChild(parent1, parent2);
        Solution secondChild = generateChild(parent1, parent2);
        return new Solution[]{firstChild, secondChild};
    }

    private Solution generateChild(Solution parent1, Solution parent2) {
        double[] childValues = new double[parent1.getValues().length];
        double[] parent1Values = parent1.getValues();
        double[] parent2Values = parent2.getValues();

        for (int i = 0; i < parent1Values.length; i++) {
            double cMin = parent1Values[i];
            double cMax = parent2Values[i];
            double I = cMax - cMin;

            if (cMin > cMax) {
                double temp = cMax;
                cMax = cMin;
                cMin = temp;
            }

            childValues[i] = cMin - I * alfa + Math.random() * (cMax + I *
                    alfa - cMin + I * alfa);

        }
        return new Solution(childValues, parent1.getObjectiveSize());

    }
}
