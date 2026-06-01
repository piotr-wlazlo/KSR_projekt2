package pl.ksr.universe;

import java.util.ArrayList;
import java.util.List;

public class DenseUniverse implements UniverseOfDiscourse {
    private final double min;
    private final double max;

    private List<Double> cachedElements;

    public DenseUniverse(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    @Override
    public boolean contains(double x) {
        return (min <= x && x <= max);
    }

    @Override
    public List<Double> getElements() {
        if (cachedElements == null) {
            cachedElements = new ArrayList<>();
            double stepSize = (max - min) / 10000.0;

            for (int i = 0; i <= 10000; i++) {
                cachedElements.add(Math.round((min + i * stepSize) * 100000.0) / 100000.0);
            }
        }
        return cachedElements;
    }

    @Override
    public double getUniverseSize() {
        return getElements().size();
    }
}