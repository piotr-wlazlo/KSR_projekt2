package pl.ksr.measures;

import pl.ksr.LinguisticSummary;
import pl.ksr.QualityMeasure;
import java.util.List;

public class DegreeOfQualifierCardinality<T> implements QualityMeasure<T> {
    @Override
    public double calculate(LinguisticSummary<T> summary, List<T> dataset) {
        double relCardW = summary.getQualifierRelativeCardinality();
        return 1.0 - relCardW;
    }

}
