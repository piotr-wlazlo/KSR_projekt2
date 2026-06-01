package pl.ksr.measures;

import pl.ksr.Qualifier;
import pl.ksr.summary.FirstFormSummary;
import pl.ksr.summary.LinguisticSummary;
import pl.ksr.summary.SecondFormSummary;

import java.util.List;

public class T9 implements QualityMeasure {
    // degree od qualifier imprecision
    @Override
    public double calculate(LinguisticSummary summary) {
        if (summary instanceof FirstFormSummary) {
            return 0.0;
        }

        SecondFormSummary s = (SecondFormSummary) summary;
        List<Qualifier> qualifiers = s.getQualifiers();
        if (qualifiers.isEmpty()) {
            return 0.0;
        }

        double temp = 1.0;
        for (Qualifier q : qualifiers) {
            temp *= q.getFuzzySet().degreeOfFuzziness();
        }

        return 1.0 - Math.pow(temp, 1.0 / qualifiers.size());
    }
}
