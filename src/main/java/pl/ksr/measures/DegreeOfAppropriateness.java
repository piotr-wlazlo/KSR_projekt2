package pl.ksr.measures;

import pl.ksr.LinguisticSummary;
import pl.ksr.QualityMeasure;
import java.util.List;

public class DegreeOfAppropriateness<T> implements QualityMeasure<T> {
    @Override
    public double calculate(LinguisticSummary<T> summary, List<T> dataset) {
        if (dataset.isEmpty()) return 0.0;

        int rCount = 0;
        for (T element : dataset) {
            if (summary.summarizer().getMembership(element) > 0.0) {
                rCount++;
            }
        }

        double r1 = (double) rCount / dataset.size();

        DegreeOfCovering<T> t3Measure = new DegreeOfCovering<>();
        double t3 = t3Measure.calculate(summary, dataset);

        return Math.abs(r1 - t3);
    }

}
