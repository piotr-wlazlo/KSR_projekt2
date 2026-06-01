package pl.ksr.measures;

import pl.ksr.Summarizer;
import pl.ksr.summary.FirstFormSummary;
import pl.ksr.summary.LinguisticSummary;
import pl.ksr.summary.SecondFormSummary;

import java.util.List;

public class T2 implements QualityMeasure {
    // degree of imprecision
    @Override
    public double calculate(LinguisticSummary summary) {
        List<Summarizer> summarizers = summary.getSummarizers();

        double temp = 1.0;
        for (Summarizer s : summarizers) {
            temp *= s.getFuzzySet().degreeOfFuzziness();
        }

        return 1.0 - Math.pow(temp, 1.0 / summarizers.size());
    }
}
