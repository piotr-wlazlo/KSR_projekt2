package pl.ksr;

import pl.ksr.sets.FuzzySet;
import pl.ksr.universe.DiscreteUniverse;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinguisticVariable {
    private final String name;
    private final DiscreteUniverse universeOfDiscourse;
    private final Map<String, FuzzySet> labels;

    public LinguisticVariable(String name, DiscreteUniverse universeOfDiscourse) {
        this.name = name;
        this.universeOfDiscourse = universeOfDiscourse;
        this.labels = new LinkedHashMap<>();
    }

    public void addLabel(String label, FuzzySet fuzzySet) {
        labels.put(label, fuzzySet);
    }

    public FuzzySet getFuzzySet(String label) {
        return labels.get(label);
    }

    public String getName() {
        return name;
    }

    public DiscreteUniverse getUniverseOfDiscourse() {
        return universeOfDiscourse;
    }

    public Map<String, FuzzySet> getLabels() {
        return labels;
    }
}
