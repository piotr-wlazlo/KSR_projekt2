package pl.ksr.measures;

import pl.ksr.Quantifier;
import pl.ksr.Summarizer;
import pl.ksr.summary.LinguisticSummary;

import java.util.List;

public class T8 implements QualityMeasure {
    // degree of summarizer cardinality
    @Override
    public double calculate(LinguisticSummary summary) {
        List<Summarizer> summarizers = summary.getSummarizers();
        if (summarizers.isEmpty()) {
            return 0.0;
        }

        double temp = 1.0;
        double numerator;
        double denominator;
        for (Summarizer s : summarizers) {
            numerator = s.getFuzzySet().cardinality();
            denominator = s.getFuzzySet().getUniverseOfDiscourse().getUniverseSize();

            temp *= (numerator / denominator);
        }

        return 1.0 - Math.pow(temp, 1.0 / summarizers.size());
    }
}
