package pl.ksr.measures;

import pl.ksr.LinguisticSummary;
import pl.ksr.QualityMeasure;
import java.util.List;

public class DegreeOfCovering<T> implements QualityMeasure<T> {
    @Override
    public double calculate(LinguisticSummary<T> summary, List<T> dataset) {
        if (dataset.isEmpty()) return 0.0;

        int tCount = 0;
        int hCount = 0;

        for (T element : dataset) {
            double muS = summary.summarizer().getMembership(element);
            double muW = summary.getQualifierMembership(element);

            if (muW > 0.0) {
                hCount++;
                if (muS > 0.0) {
                    tCount++;
                }
            }
        }

        if (hCount == 0) return 0.0;
        return (double) tCount / hCount;
    }

}
