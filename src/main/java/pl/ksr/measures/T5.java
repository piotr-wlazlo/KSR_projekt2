package pl.ksr.measures;

import pl.ksr.summary.LinguisticSummary;

public class T5 implements QualityMeasure {
    // length of a summary
    @Override
    public double calculate(LinguisticSummary summary) {
        double s = summary.getSummarizers().size();

        return 2.0 * Math.pow(0.5, s);
    }
}
