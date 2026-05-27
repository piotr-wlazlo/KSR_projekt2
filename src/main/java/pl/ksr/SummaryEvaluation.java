package pl.ksr;

import pl.ksr.measures.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SummaryEvaluation<T> {
    private final Map<QualityMeasure<T>, Double> measuresWithWeights = new LinkedHashMap<>();

    public void addMeasure(QualityMeasure<T> measure, double weight) {
        measuresWithWeights.put(measure, weight);
    }

    public double evaluate(LinguisticSummary<T> summary, List<T> dataset) {
        double finalScore = 0.0;
        double weightSum = 0.0;

        for (Map.Entry<QualityMeasure<T>, Double> entry : measuresWithWeights.entrySet()) {
            QualityMeasure<T> measure = entry.getKey();
            double weight = entry.getValue();

            double score = measure.calculate(summary, dataset);

            finalScore += score * weight;
            weightSum += weight;

        }

        double overallQuality = weightSum > 0 ? finalScore / weightSum : 0.0;
        System.out.printf("Goodness of the summary (T): %.4f%n", overallQuality);


        return overallQuality;
    }
}