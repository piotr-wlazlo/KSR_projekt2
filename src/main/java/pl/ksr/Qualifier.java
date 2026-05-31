package pl.ksr;

import pl.ksr.sets.FuzzySet;

public class Qualifier {
    private final LinguisticVariable variable;
    private final String label;

    public Qualifier(LinguisticVariable variable, String label) {
        this.variable = variable;
        this.label = label;
    }

    public String getVariableName() {
        return variable.getName();
    }

    public String getLabel() {
        return label;
    }

    public FuzzySet getFuzzySet() {
        return variable.getFuzzySet(label);
    }
}
