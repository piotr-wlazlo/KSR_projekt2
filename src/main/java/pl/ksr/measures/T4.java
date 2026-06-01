package pl.ksr.measures;

import pl.ksr.Summarizer;
import pl.ksr.summary.LinguisticSummary;

import java.util.List;

public class T4 implements QualityMeasure {
    // degree of appropriateness
    @Override
    public double calculate(LinguisticSummary summary) {
        List<Summarizer> summarizers = summary.getSummarizers();

        double temp = 1.0;
        for (Summarizer s : summarizers) {
            temp *= s.getFuzzySet().degreeOfFuzziness();
        }

        double t3 = new T3().calculate(summary);

        return Math.abs(temp - t3);
    }
}
