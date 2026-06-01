package pl.ksr.measures;

import pl.ksr.summary.LinguisticSummary;

public interface QualityMeasure {
    double calculate(LinguisticSummary summary);
}
