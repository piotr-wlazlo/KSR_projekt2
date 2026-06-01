package pl.ksr.measures;

import pl.ksr.summary.LinguisticSummary;

public class WeightedMeasure {
    private final QualityMeasure measure;
    private final double weight;

    public WeightedMeasure(QualityMeasure measure, double weight) {
        this.measure = measure;
        this.weight = weight;
    }

    public double calculateWeightedMeasure(LinguisticSummary summary) {
        return measure.calculate(summary) * weight;
    }
}
