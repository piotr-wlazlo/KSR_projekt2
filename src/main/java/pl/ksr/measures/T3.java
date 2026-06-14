package pl.ksr.measures;

import pl.ksr.Car;
import pl.ksr.LogicalOperator;
import pl.ksr.summary.single.SingleFirstFormSummary;
import pl.ksr.summary.LinguisticSummary;
import pl.ksr.summary.single.SingleSecondFormSummary;

import java.util.List;

public class T3 implements QualityMeasure {
    // degree of covering
    @Override
    public double calculate(LinguisticSummary summary) {
        if (summary instanceof SingleFirstFormSummary) {
            return calculateFirstForm((SingleFirstFormSummary) summary);
        } else if (summary instanceof SingleSecondFormSummary) {
            return calculateSecondForm((SingleSecondFormSummary) summary);
        }
        return 0.0;
    }

    private double calculateFirstForm(SingleFirstFormSummary s) {
        List<Car> cars = s.getCars();

        if (cars.isEmpty()) {
            return 0.0;
        }

        int numerator = 0;
        int denominator = cars.size();

        for (Car car : cars) {
            double membership = s.getOperator() == LogicalOperator.AND ? 1.0 : 0.0;

            for (int i = 0; i < s.getSummarizers().size(); i++) {
                double m = s.getSummarizers().get(i).getFuzzySet().getMembership(s.getAttributes().get(i).apply(car));
                membership = s.getOperator() == LogicalOperator.AND ? Math.min(membership, m) : Math.max(membership, m);
            }

            if (membership > 0.0) {
                numerator++;
            }
        }


        return (double) numerator / denominator;
    }

    private double calculateSecondForm(SingleSecondFormSummary s) {
        List<Car> cars = s.getCars();

        if (cars.isEmpty()) {
            return 0.0;
        }

        int numerator = 0;
        int denominator = 0;

        for (Car car : cars) {
            double qualifierMembership = s.getQualifierOperator() == LogicalOperator.AND ? 1.0 : 0.0;
            for (int i = 0; i < s.getQualifiers().size(); i++) {
                double m = s.getQualifiers().get(i).getFuzzySet().getMembership(s.getQualifierAttributes().get(i).apply(car));
                qualifierMembership = s.getQualifierOperator() == LogicalOperator.AND ? Math.min(qualifierMembership, m) : Math.max(qualifierMembership, m);
            }

            if (qualifierMembership > 0.0) {
                denominator++;

                double summarizerMembership = s.getSummarizerOperator() == LogicalOperator.AND ? 1.0 : 0.0;
                for (int i = 0; i < s.getSummarizers().size(); i++) {
                    double m = s.getSummarizers().get(i).getFuzzySet().getMembership(s.getSummarizerAttributes().get(i).apply(car));
                    summarizerMembership = s.getSummarizerOperator() == LogicalOperator.AND ? Math.min(summarizerMembership, m) : Math.max(summarizerMembership, m);
                }

                if (summarizerMembership > 0.0) {
                    numerator++;
                }
            }
        }

        if (denominator == 0) {
            return 0.0;
        }

        return (double) numerator / denominator;
    }
}
