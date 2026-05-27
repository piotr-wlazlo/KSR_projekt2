package pl.ksr.measures;

import pl.ksr.LinguisticSummary;
import pl.ksr.QualityMeasure;
import java.util.List;

public class DegreeOfQualifierImprecision<T> implements QualityMeasure<T> {
    @Override
    public double calculate(LinguisticSummary<T> summary, List<T> dataset) {
        double inW = summary.getQualifierDegreeOfFuzziness();
        return 1.0 - inW;
    }

}