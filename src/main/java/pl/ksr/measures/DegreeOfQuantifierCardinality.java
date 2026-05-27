package pl.ksr.measures;

import pl.ksr.LinguisticSummary;
import pl.ksr.QualityMeasure;
import java.util.List;

public class DegreeOfQuantifierCardinality<T> implements QualityMeasure<T> {
    @Override
    public double calculate(LinguisticSummary<T> summary, List<T> dataset) {
        double relCardQ = summary.quantifier().getRelativeCardinality();
        return 1.0 - relCardQ;
    }

}
