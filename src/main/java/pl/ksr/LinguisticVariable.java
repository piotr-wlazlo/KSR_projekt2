package pl.ksr;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LinguisticVariable<T> {
    private final String name;
//    private final Set<T> universeOfDiscourse;
    private final DiscreteUniverse<T> universeOfDiscourse;
    private final Map<String, FuzzySet<T>> labels;

    public LinguisticVariable(String name, DiscreteUniverse<T> universeOfDiscourse) {
        this.name = name;
        this.universeOfDiscourse = universeOfDiscourse;
        this.labels = new HashMap<>();
    }

    public boolean addLabel(String label, FuzzySet<T> fuzzySet) {
        if (labels.containsKey(label)) {
            return false;
        } else {
            this.labels.put(label, fuzzySet);
            return true;
        }
    }

    public boolean removeLabel(String label) {
        if (labels.containsKey(label)) {
            this.labels.remove(label);
            return true;
        } else {
            return false;
        }
    }

    public FuzzySet<T> getFuzzySetForLabel(String label) {
        if (!labels.containsKey(label)) {
            throw new IllegalArgumentException("Label " + label + " not found.");
        }
        return labels.get(label);
    }

    public String getName() {
        return name;
    }

    public DiscreteUniverse<T> getUniverseOfDiscourse() {
        return universeOfDiscourse;
    }

    public Map<String, FuzzySet<T>> getLabels() {
        return labels;
    }

}