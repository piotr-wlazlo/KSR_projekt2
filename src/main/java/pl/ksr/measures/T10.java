package pl.ksr.measures;

import pl.ksr.Qualifier;
import pl.ksr.summary.LinguisticSummary;

import java.util.List;

public class T10 implements QualityMeasure {
    // degree of qualifier cardinality
    @Override
    public double calculate(LinguisticSummary summary) {
        List<Qualifier> qualifiers = summary.getQualifiers();
        if (qualifiers.isEmpty()) {
            return 0.0;
        }

        double temp = 1.0;
        double numerator;
        double denominator;
        for (Qualifier q : qualifiers) {
            numerator = q.getFuzzySet().cardinality();
            denominator = q.getFuzzySet().getUniverseOfDiscourse().getUniverseSize();
            temp *= numerator / denominator;
        }

        return 1.0 - Math.pow(temp, 1.0 / qualifiers.size());

    }
}
