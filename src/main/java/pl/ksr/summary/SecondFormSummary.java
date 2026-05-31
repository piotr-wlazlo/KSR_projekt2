package pl.ksr.summary;

import pl.ksr.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SecondFormSummary implements LinguisticSummary {
    private final Quantifier quantifier;
    private final List<Qualifier> qualifiers;
    private final List<Function<Car, Double>> qualifierAttributes;
    private final LogicalOperator qualifierOperator;
    private final List<Summarizer> summarizers;
    private final List<Function<Car, Double>> summarizerAttributes;
    private final LogicalOperator summarizerOperator;
    private final List<Car> cars;

    public SecondFormSummary(Quantifier quantifier,
                             List<Qualifier> qualifiers,
                             List<Function<Car, Double>> qualifierAttributes,
                             List<Summarizer> summarizers,
                             List<Function<Car, Double>> summarizerAttributes,
                             List<Car> cars) {
        this(quantifier, qualifiers, summarizerAttributes, LogicalOperator.AND, summarizers, qualifierAttributes, LogicalOperator.AND, cars);
    }

    public SecondFormSummary(Quantifier quantifier,
                             List<Qualifier> qualifiers,
                             List<Function<Car, Double>> qualifierAttributes,
                             LogicalOperator qualifierOperator,
                             List<Summarizer> summarizers,
                             List<Function<Car, Double>> summarizerAttributes,
                             LogicalOperator summarizerOperator,
                             List<Car> cars) {
        this.quantifier = quantifier;
        this.qualifiers = qualifiers;
        this.qualifierAttributes = qualifierAttributes;
        this.qualifierOperator = qualifierOperator;
        this.summarizers = summarizers;
        this.summarizerAttributes = summarizerAttributes;
        this.summarizerOperator = summarizerOperator;
        this.cars = cars;
    }

    @Override
    public String getSummary() {
        String qualifierOp = qualifierOperator == LogicalOperator.AND ? " and " : " or ";
        String summarizerOp = summarizerOperator == LogicalOperator.AND ? " and " : " or ";

        String qualifiersLabel = qualifiers.stream()
                .map(q -> q.getVariableName() + " " + q.getLabel())
                .collect(Collectors.joining(qualifierOp));

        String summarizersLabel = summarizers.stream()
                .map(s -> s.getVariableName() + " " + s.getLabel())
                .collect(Collectors.joining(summarizerOp));


        return quantifier.getLabel() + " cars being/having " + qualifiersLabel + " are/have " + summarizersLabel;
    }

    @Override
    public double degreeOfTruth() {
        double numerator = 0.0;
        double denominator = 0.0;

        for (Car car : cars) {
            double qualifierMembership = qualifierOperator == LogicalOperator.AND ? 1.0 : 0.0;
            for (int i = 0; i < qualifiers.size(); i++) {
                double m = qualifiers.get(i).getFuzzySet().getMembership(qualifierAttributes.get(i).apply(car));
                qualifierMembership = qualifierOperator == LogicalOperator.AND ? Math.min(qualifierMembership, m) : Math.max(qualifierMembership, m);
            }

            double summarizerMembership = summarizerOperator == LogicalOperator.AND ? 1.0 : 0.0;
            for (int i = 0; i < summarizers.size(); i++) {
                double m = summarizers.get(i).getFuzzySet().getMembership(summarizerAttributes.get(i).apply(car));
                summarizerMembership = summarizerOperator == LogicalOperator.AND ? Math.min(summarizerMembership, m) : Math.max(summarizerMembership, m);
            }


            numerator += Math.min(qualifierMembership, summarizerMembership);
            denominator += qualifierMembership;
        }

        if (denominator == 0.0) {
            return 0.0;
        }

        double r = numerator / denominator;
        return quantifier.getFuzzySet().getMembership(r);

    }
}
