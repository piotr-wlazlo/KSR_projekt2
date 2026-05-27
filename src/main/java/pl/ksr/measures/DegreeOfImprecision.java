package pl.ksr.measures;

import pl.ksr.LinguisticSummary;
import pl.ksr.QualityMeasure;
import java.util.List;

public class DegreeOfImprecision<T> implements QualityMeasure<T> {
    @Override
    public double calculate(LinguisticSummary<T> summary, List<T> dataset) {
        double inS = summary.summarizer().getFuzzySet().getDegreeOfFuzziness();
        return 1.0 - inS;
    }

}
