package pl.ksr.measures;

import pl.ksr.Car;
import pl.ksr.LogicalOperator;
import pl.ksr.Qualifier;
import pl.ksr.Summarizer;
import pl.ksr.summary.FirstFormSummary;
import pl.ksr.summary.LinguisticSummary;
import pl.ksr.summary.SecondFormSummary;
import pl.ksr.summary.multi.FirstForm;
import pl.ksr.summary.multi.SecondForm;
import pl.ksr.summary.multi.ThirdForm;
import pl.ksr.summary.multi.FourthForm;

import java.util.List;
import java.util.function.Function;

public class T1 implements QualityMeasure {

    @Override
    public double calculate(LinguisticSummary summary) {
        if (summary instanceof FirstFormSummary) return calculateFirstForm((FirstFormSummary) summary);
        if (summary instanceof SecondFormSummary) return calculateSecondForm((SecondFormSummary) summary);

        if (summary instanceof FirstForm) return calculateMulti1((FirstForm) summary);
        if (summary instanceof SecondForm) return calculateMulti2((SecondForm) summary);
        if (summary instanceof ThirdForm) return calculateMulti3((ThirdForm) summary);
        if (summary instanceof FourthForm) return calculateMulti4((FourthForm) summary);

        return 0.0;
    }

    private double calculateFirstForm(FirstFormSummary summary) {
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

    private double calculateSecondForm(SecondFormSummary summary) {
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

    // --- METODY DLA FORM WIELOPODMIOTOWYCH ---
    private double calculateMulti1(FirstForm s) {
        double m1 = s.p1.size(); double m2 = s.p2.size();
        if (m1 == 0 || m2 == 0) return 0.0;
        double h1 = (1.0 / m1) * calcMem(s.p1, s.sums, s.sumAttrs, s.sumOp, null, null, null);
        double h2 = (1.0 / m2) * calcMem(s.p2, s.sums, s.sumAttrs, s.sumOp, null, null, null);
        if (h1 + h2 == 0) return 0.0;
        return s.getQuantifier().getFuzzySet().getMembership(h1 / (h1 + h2));
    }

    private double calculateMulti2(SecondForm s) {
        double m1 = s.p1.size(); double m2 = s.p2.size();
        if (m1 == 0 || m2 == 0) return 0.0;
        double h1 = (1.0 / m1) * calcMem(s.p1, s.sums, s.sumAttrs, s.sumOp, null, null, null);
        double h2 = (1.0 / m2) * calcMem(s.p2, s.sums, s.sumAttrs, s.sumOp, s.quals, s.qualAttrs, s.qualOp);
        if (h1 + h2 == 0) return 0.0;
        return s.getQuantifier().getFuzzySet().getMembership(h1 / (h1 + h2));
    }

    private double calculateMulti3(ThirdForm s) {
        double m1 = s.p1.size(); double m2 = s.p2.size();
        if (m1 == 0 || m2 == 0) return 0.0;
        double h1 = (1.0 / m1) * calcMem(s.p1, s.sums, s.sumAttrs, s.sumOp, s.quals, s.qualAttrs, s.qualOp);
        double h2 = (1.0 / m2) * calcMem(s.p2, s.sums, s.sumAttrs, s.sumOp, null, null, null);
        if (h1 + h2 == 0) return 0.0;
        return s.getQuantifier().getFuzzySet().getMembership(h1 / (h1 + h2));
    }

    private double calculateMulti4(FourthForm s) {
        double m1 = s.p1.size(); double m2 = s.p2.size();
        if (m1 == 0 || m2 == 0) return 0.0;
        double v1 = (1.0 / m1) * calcMem(s.p1, s.sums, s.sumAttrs, s.sumOp, null, null, null);
        double v2 = (1.0 / m2) * calcMem(s.p2, s.sums, s.sumAttrs, s.sumOp, null, null, null);

        if (v1 == 0) return 0.0;
        return 1.0 - (Math.min(v1, v2) / v1);
    }

    private double calcMem(List<Car> cars, List<Summarizer> sums, List<Function<Car, Double>> sAttrs, LogicalOperator sumOp,
                           List<Qualifier> quals, List<Function<Car, Double>> qAttrs, LogicalOperator qualOp) {
        double total = 0.0;
        for (Car car : cars) {
            double sMem = (sumOp == LogicalOperator.AND) ? 1.0 : 0.0;
            if (sums != null && !sums.isEmpty()) {
                for(int i=0; i<sums.size(); i++) {
                    double m = sums.get(i).getFuzzySet().getMembership(sAttrs.get(i).apply(car));
                    sMem = (sumOp == LogicalOperator.AND) ? Math.min(sMem, m) : Math.max(sMem, m);
                }
            }
            if (quals != null && !quals.isEmpty()) {
                double qMem = (qualOp == LogicalOperator.AND) ? 1.0 : 0.0;
                for(int i=0; i<quals.size(); i++) {
                    double m = quals.get(i).getFuzzySet().getMembership(qAttrs.get(i).apply(car));
                    qMem = (qualOp == LogicalOperator.AND) ? Math.min(qMem, m) : Math.max(qMem, m);
                }
                total += Math.min(sMem, qMem);
            } else {
                total += sMem;
            }
        }
        return total;
    }
}