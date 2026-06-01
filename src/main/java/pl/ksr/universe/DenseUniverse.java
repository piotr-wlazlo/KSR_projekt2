package pl.ksr.universe;

import java.util.ArrayList;
import java.util.List;

public class DenseUniverse implements UniverseOfDiscourse {
    private final double min;
    private final double max;

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
        List<Double> elements = new ArrayList<>();
        double stepSize = (max - min) / 10000.0;

        for (int i = 0; i <= 10000; i++) {
            elements.add(Math.round((min + i * stepSize) * 100000.0) / 100000.0);
        }

        return elements;
    }

    @Override
    public double getUniverseSize() {
        return max - min;
    }
}
