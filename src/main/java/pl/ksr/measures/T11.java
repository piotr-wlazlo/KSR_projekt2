package pl.ksr.measures;

import pl.ksr.Qualifier;
import pl.ksr.summary.LinguisticSummary;

import java.util.List;

public class T11 implements QualityMeasure {
    // length of qualifiers
    @Override
    public double calculate(LinguisticSummary summary) {
        List<Qualifier> qualifiers = summary.getQualifiers();
        if (qualifiers.isEmpty()) {
            return 1.0;
        }

        double q = qualifiers.size();
        return 2.0 * Math.pow(0.5, q);
    }
}
