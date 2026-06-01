package pl.ksr.measures;

import pl.ksr.Qualifier;
import pl.ksr.summary.FirstFormSummary;
import pl.ksr.summary.LinguisticSummary;
import pl.ksr.summary.SecondFormSummary;

import java.util.List;

public class T11 implements QualityMeasure {
    // length of qualifiers
    @Override
    public double calculate(LinguisticSummary summary) {
        if (summary instanceof FirstFormSummary) {
            return 0.0;
        }

        SecondFormSummary s = (SecondFormSummary) summary;
        double q = s.getQualifiers().size();

        return 2.0 * Math.pow(0.5, q);
    }
}
