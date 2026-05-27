package pl.ksr.measures;

import pl.ksr.LinguisticSummary;
import pl.ksr.QualityMeasure;
import java.util.List;

public class DegreeOfSummarizerCardinality<T> implements QualityMeasure<T> {
    @Override
    public double calculate(LinguisticSummary<T> summary, List<T> dataset) {
        double relCardS = summary.summarizer().getFuzzySet().getRelativeCardinality();
        return 1.0 - relCardS;
    }

}