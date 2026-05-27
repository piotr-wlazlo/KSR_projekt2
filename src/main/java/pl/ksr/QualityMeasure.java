package pl.ksr;

import java.util.List;

public interface QualityMeasure<T> {
    double calculate(LinguisticSummary<T> summary, List<T> dataset);
}