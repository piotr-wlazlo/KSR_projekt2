package pl.ksr.measures;

import pl.ksr.LinguisticSummary;
import pl.ksr.QualityMeasure;
import java.util.List;

public class DegreeOfTruth<T> implements QualityMeasure<T> {

    @Override
    public double calculate(LinguisticSummary<T> summary, List<T> dataset) {
        if (dataset.isEmpty()) return 0.0;

        double sumNumerator = 0.0;
        double sumDenominator = 0.0;

        for (T element : dataset) {
            double muS = summary.summarizer().getMembership(element);
            double muW = summary.getQualifierMembership(element);

            sumNumerator += Math.min(muS, muW);
            sumDenominator += muW;
        }

        if (sumDenominator == 0.0) return 0.0;

        double r = sumNumerator / sumDenominator;
        return summary.quantifier().getMembership(r);
    }

}