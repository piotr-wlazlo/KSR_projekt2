package pl.ksr;

import pl.ksr.sets.FuzzySet;

public class Quantifier {
    private final String label;
    private final FuzzySet fuzzySet;
    private final boolean isRelative;


    public Quantifier(String label, FuzzySet fuzzySet, boolean isRelative) {
        this.label = label;
        this.fuzzySet = fuzzySet;
        this.isRelative = isRelative;
    }

    public String getLabel() {
        return label;
    }

    public boolean isRelative() {
        return isRelative;
    }

    public FuzzySet getFuzzySet() {
        return fuzzySet;
    }
}
