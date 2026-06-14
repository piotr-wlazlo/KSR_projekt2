package pl.ksr.measures;

import pl.ksr.Car;
import pl.ksr.LogicalOperator;
import pl.ksr.summary.single.SingleFirstFormSummary;
import pl.ksr.summary.LinguisticSummary;
import pl.ksr.summary.single.SingleSecondFormSummary;

public class T1 implements QualityMeasure {
    // degree of truth
    @Override
    public double calculate(LinguisticSummary summary) {
        if (summary instanceof SingleFirstFormSummary) {
            return calculateFirstForm((SingleFirstFormSummary) summary);
        } else if (summary instanceof SingleSecondFormSummary) {
            return calculateSecondForm((SingleSecondFormSummary) summary);
        }
        return 0.0;
    }

    private double calculateFirstForm(SingleFirstFormSummary summary) {
        double sum = 0.0;

        for (Car car : summary.getCars()) {
            double membership = summary.getOperator() == LogicalOperator.AND ? 1.0 : 0.0;
            for (int i = 0; i < summary.getSummarizers().size(); i++) {
                double m = summary.getSummarizers().get(i).getFuzzySet().getMembership(summary.getAttributes().get(i).apply(car));
                membership = summary.getOperator() == LogicalOperator.AND ? Math.min(membership, m) : Math.max(membership, m);
            }
            sum += membership;
        }
        double r = summary.getQuantifier().isRelative() ? sum / summary.getCars().size() : sum;

        return summary.getQuantifier().getFuzzySet().getMembership(r);
    }

    private double calculateSecondForm(SingleSecondFormSummary summary) {
        double numerator = 0.0;
        double denominator = 0.0;
        for (Car car : summary.getCars()) {
            double qualifierMembership = summary.getQualifierOperator() == LogicalOperator.AND ? 1.0 : 0.0;
            for (int i = 0; i < summary.getQualifiers().size(); i++) {
                double m = summary.getQualifiers().get(i).getFuzzySet().getMembership(summary.getQualifierAttributes().get(i).apply(car));
                qualifierMembership = summary.getQualifierOperator() == LogicalOperator.AND ? Math.min(qualifierMembership, m) : Math.max(qualifierMembership, m);
            }

            double summarizerMembership = summary.getSummarizerOperator() == LogicalOperator.AND ? 1.0 : 0.0;
            for (int i = 0; i < summary.getSummarizers().size(); i++) {
                double m = summary.getSummarizers().get(i).getFuzzySet().getMembership(summary.getSummarizerAttributes().get(i).apply(car));
                summarizerMembership = summary.getSummarizerOperator() == LogicalOperator.AND ? Math.min(summarizerMembership, m) : Math.max(summarizerMembership, m);
            }

            numerator += Math.min(qualifierMembership, summarizerMembership);
            denominator += qualifierMembership;
        }

        if (denominator == 0.0) return 0.0;
        double r = numerator / denominator;
        return summary.getQuantifier().getFuzzySet().getMembership(r);
    }
}