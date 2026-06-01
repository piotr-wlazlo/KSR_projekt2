package pl.ksr.measures;

import pl.ksr.summary.LinguisticSummary;

import java.util.ArrayList;
import java.util.List;

public class To {
    private final List<WeightedMeasure> weightedMeasures = new ArrayList<>();

    public void addMeasure(QualityMeasure measure, double weight) {
        weightedMeasures.add(new WeightedMeasure(measure, weight));
    }

    public double calculate(LinguisticSummary summary) {
        double d = 0.0;
        for (WeightedMeasure wm : weightedMeasures) {
            d += wm.calculateWeightedMeasure(summary);
        }

        return d;
    }
}
