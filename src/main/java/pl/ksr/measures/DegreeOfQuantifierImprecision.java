package pl.ksr.measures;

import pl.ksr.LinguisticSummary;
import pl.ksr.QualityMeasure;
import java.util.List;

public class DegreeOfQuantifierImprecision<T> implements QualityMeasure<T> {
    @Override
    public double calculate(LinguisticSummary<T> summary, List<T> dataset) {
        double inQ = summary.quantifier().getDegreeOfFuzziness();
        return 1.0 - inQ;
    }

}
