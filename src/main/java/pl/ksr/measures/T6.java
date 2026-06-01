package pl.ksr.measures;

import pl.ksr.Quantifier;
import pl.ksr.summary.LinguisticSummary;

public class T6 implements QualityMeasure {
    //degree of quantifier imprecision
    @Override
    public double calculate(LinguisticSummary summary) {
        Quantifier quantifier = summary.getQuantifier();

        return 1.0 - quantifier.getFuzzySet().degreeOfFuzziness();
    }
}
