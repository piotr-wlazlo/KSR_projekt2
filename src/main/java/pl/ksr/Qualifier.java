package pl.ksr;

import pl.ksr.sets.FuzzySet;

public class Qualifier {
    private final LinguisticVariable variable;
    private final String label;
    private final boolean not;

    public Qualifier(LinguisticVariable variable, String label) {
        this(variable, label, false);
    }

    public Qualifier(LinguisticVariable variable, String label, boolean not) {
        this.variable = variable;
        this.label = label;
        this.not = not;
    }

    public String getVariableName() {
        return variable.getName();
    }

    public String getLabel() {
        return label;
    }

    public boolean isNot() {
        return not;
    }

    public FuzzySet getFuzzySet() {
        FuzzySet fuzzySet = variable.getFuzzySet(label);
        return not ? fuzzySet.complement() : fuzzySet;
    }
}