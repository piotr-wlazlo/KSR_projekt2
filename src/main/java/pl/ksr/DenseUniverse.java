package pl.ksr;

import java.util.HashSet;
import java.util.Set;

public class DenseUniverse implements UniverseOfDiscourse<Double> {
    private final double min;
    private final double max;

    public DenseUniverse(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public ClassicSet<Double> discretize(int numberOfSteps) {
        Set<Double> discrete = new HashSet<>();
        double stepSize = (max - min) / (numberOfSteps - 1);

        for (int i = 0; i < numberOfSteps; i++) {
            discrete.add(min + (i * stepSize));
        }

        return new ClassicSet<>(discrete);
    }

    @Override
    public boolean contains(Double element) {
        if (element == null) {
            return false;
        } else {
            return min <= element && element <= max;
        }
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }
}
