package pl.ksr.measures;

import pl.ksr.*;
import pl.ksr.summary.LinguisticSummary;
import pl.ksr.summary.multi.MultiFirstFormSummary;
import pl.ksr.summary.multi.MultiFourthForm;
import pl.ksr.summary.multi.MultiSecondFormSummary;
import pl.ksr.summary.multi.MultiThirdFormSummary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class MultiSubjectT implements QualityMeasure {
    @Override
    public double calculate(LinguisticSummary summary) {
        if (summary instanceof MultiFirstFormSummary s) {
            return calculateFirstForm(s);
        } else if (summary instanceof MultiSecondFormSummary s) {
            return calculateSecondForm(s);
        } else if (summary instanceof MultiThirdFormSummary s) {
            return calculateThirdForm(s);
        } else if (summary instanceof MultiFourthForm s) {
            return calculateFourthForm(s);
        } else {
            return 0.0;
        }
    }

    private double calculateFirstForm(MultiFirstFormSummary s) {
        if (s.getP1().isEmpty() || s.getP2().isEmpty()) {
            return 0.0;
        }

        double s_p1 = calculateHelper(s.getP1(), s.getSummarizers(), s.getAttributes(), s.getOperator(), s.getQualifiers(), s.getQualifierAttributes(), s.getQualifierOperator());
        double s_p2 = calculateHelper(s.getP2(), s.getSummarizers(), s.getAttributes(), s.getOperator(), s.getQualifiers(), s.getQualifierAttributes(), s.getQualifierOperator());

        if (s_p1 + s_p2 == 0.0) {
            return 0.0;
        }

        return s.getQuantifier().getFuzzySet().getMembership(s_p1 / (s_p1 + s_p2));
    }

    private double calculateSecondForm(MultiSecondFormSummary s) {
        if (s.getP1().isEmpty() || s.getP2().isEmpty()) {
            return 0.0;
        }

        double s_p1 = calculateHelper(s.getP1(), s.getSummarizers(), s.getSummarizerAttributes(), s.getSummarizerOperator(), null, null, null);
        double s_p2 = calculateHelper(s.getP2(), s.getSummarizers(), s.getSummarizerAttributes(), s.getSummarizerOperator(), s.getQualifiers(), s.getQualifierAttributes(), s.getQualifierOperator());

        if (s_p1 + s_p2 == 0.0) {
            return 0.0;
        }

        return s.getQuantifier().getFuzzySet().getMembership(s_p1 / (s_p1 + s_p2));
    }

    private double calculateThirdForm(MultiThirdFormSummary s) {
        if (s.getP1().isEmpty() || s.getP2().isEmpty()) {
            return 0.0;
        }

        double s_p1 = calculateHelper(s.getP1(), s.getSummarizers(), s.getSummarizerAttributes(), s.getSummarizerOperator(), s.getQualifiers(), s.getQualifierAttributes(), s.getQualifierOperator());
        double s_p2 = calculateHelper(s.getP2(), s.getSummarizers(), s.getSummarizerAttributes(), s.getSummarizerOperator(), null, null, null);

        if (s_p1 + s_p2 == 0.0) {
            return 0.0;
        }

        return s.getQuantifier().getFuzzySet().getMembership(s_p1 / (s_p1 + s_p2));
    }

    private double calculateFourthForm(MultiFourthForm s) {
        List<Car> p1 = s.getP1();
        List<Car> p2 = s.getP2();
        if (p1.isEmpty() || p2.isEmpty()) {
            return 0.0;
        }

        Set<Car> p1Set = new HashSet<>(p1);
        Set<Car> p2Set = new HashSet<>(p2);

        List<Car> universe = new ArrayList<>(p1.size() + p2.size());
        universe.addAll(p1);
        universe.addAll(p2);

        double sum = 0.0;
        for (Car car : universe) {
            double a = p2Set.contains(car) ? calculateHelper(List.of(car), s.getSummarizers(), s.getAttributes(), s.getOperator(), null, null, null) : 0.0;
            double b = p1Set.contains(car) ? calculateHelper(List.of(car), s.getSummarizers(), s.getAttributes(), s.getOperator(), null, null, null) : 0.0;

            sum += ReichenbachImplication.implication(a, b);
        }

        return 1 - (sum / universe.size());
    }

    private double calculateHelper(List<Car> cars,
                                   List<Summarizer> summarizers,
                                   List<Function<Car, Double>> summarizerAttributes,
                                   LogicalOperator summarizerOperator,
                                   List<Qualifier> qualifiers,
                                   List<Function<Car, Double>> qualifierAttributes,
                                   LogicalOperator qualifierOperator) {
        double sum = 0.0;

        for (Car car : cars) {
            double sMembership = (summarizerOperator == LogicalOperator.AND) ? 1.0 : 0.0;
            if (summarizers != null && summarizers.size() > 0) {
                for (int i = 0; i < summarizers.size(); i++) {
                    double m = summarizers.get(i).getFuzzySet().getMembership(summarizerAttributes.get(i).apply(car));
                    sMembership = (summarizerOperator == LogicalOperator.AND) ? Math.min(sMembership, m) : Math.max(sMembership, m);
                }
            }

            if (qualifiers != null && qualifiers.size() > 0) {
                double qMembership = (qualifierOperator == LogicalOperator.AND) ? 1.0 : 0.0;
                for (int i = 0; i < qualifiers.size(); i++) {
                    double m = qualifiers.get(i).getFuzzySet().getMembership(qualifierAttributes.get(i).apply(car));
                    qMembership = (qualifierOperator == LogicalOperator.AND) ? Math.min(qMembership, m) : Math.max(qMembership, m);
                }
                sum += Math.min(sMembership, qMembership);
            } else {
                sum += sMembership;
            }
        }
        return sum / cars.size();
    }
}
