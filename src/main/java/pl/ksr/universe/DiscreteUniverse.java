package pl.ksr.universe;

import java.util.List;

public class DiscreteUniverse implements UniverseOfDiscourse {
    private final List<Double> elements;

    public DiscreteUniverse(List<Double> elements) {
        this.elements = elements;
    }

    public List<Double> getElements() {
        return elements;
    }

    @Override
    public boolean contains(double x) {
        return elements.contains(x);
    }

    @Override
    public double getUniverseSize() {
        return elements.size();
    }
}
