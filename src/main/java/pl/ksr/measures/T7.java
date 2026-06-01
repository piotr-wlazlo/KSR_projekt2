package pl.ksr.measures;

import pl.ksr.Quantifier;
import pl.ksr.summary.LinguisticSummary;

public class T7 implements QualityMeasure {
    // degree of quantifier cardinality
    @Override
    public double calculate(LinguisticSummary summary) {
        Quantifier quantifier = summary.getQuantifier();
        double numerator = quantifier.getFuzzySet().cardinality();
        double denominator = quantifier.getFuzzySet().getUniverseOfDiscourse().getUniverseSize();

        return 1.0 - (numerator / denominator);
    }
}
