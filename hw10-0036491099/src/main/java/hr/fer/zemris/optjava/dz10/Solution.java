package hr.fer.zemris.optjava.dz10;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solution implements Comparable<Solution> {
    private double[] values;
    private double[] objectives;
    private double crowdingDistance;
    private int frontNumber;
    private List<Comparator<Solution>> comparatorList;
    private int objectiveSize;


    public Solution(double[] values, int objectiveSize) {
        this.values = values;
        this.objectiveSize = objectiveSize;

        generateComparators(objectiveSize);
    }

    private void generateComparators(int n) {
        comparatorList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            final int j = i;
            comparatorList.add(new Comparator<Solution>() {
                @Override
                public int compare(Solution o1, Solution o2) {
                    return Double.compare(o1.getObjectives()[j], o2.getObjectives()[j]);
                }
            });
        }
    }

    public int getObjectiveSize() {
        return objectiveSize;
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

    public double getCrowdingDistance() {
        return crowdingDistance;
    }

    public void setCrowdingDistance(double crowdingDistance) {
        this.crowdingDistance = crowdingDistance;
    }

    public int getFrontNumber() {
        return frontNumber;
    }

    public void setFrontNumber(int frontNumber) {
        this.frontNumber = frontNumber;
    }

    public List<Comparator<Solution>> getComparatorList() {
        return comparatorList;
    }

    @Override
    public int compareTo(Solution o) {
        if (o.getFrontNumber() > frontNumber) {
            return 1;
        } else if (o.getFrontNumber() < frontNumber) {
            return -1;
        } else {
            if (o.getCrowdingDistance() > crowdingDistance) {
                return -1;
            } else if (o.getCrowdingDistance() < crowdingDistance) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
