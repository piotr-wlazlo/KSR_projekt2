package pl.ksr.summary;

import pl.ksr.Car;
import pl.ksr.LogicalOperator;
import pl.ksr.Quantifier;
import pl.ksr.Summarizer;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FirstFormSummary implements LinguisticSummary {
    private final Quantifier quantifier;
    private final List<Summarizer> summarizers;
    private final List<Function<Car, Double>> attributes;
    private final LogicalOperator operator;
    private final List<Car> cars;


    public FirstFormSummary(Quantifier quantifier,
                            List<Summarizer> summarizers,
                            List<Function<Car, Double>> attributes,
                            List<Car> cars) {
        this(quantifier, summarizers, attributes, LogicalOperator.AND, cars);
    }

    public FirstFormSummary(Quantifier quantifier,
                            List<Summarizer> summarizers,
                            List<Function<Car, Double>> attributes,
                            LogicalOperator operator, List<Car> cars) {
        this.quantifier = quantifier;
        this.summarizers = summarizers;
        this.attributes = attributes;
        this.operator = operator;
        this.cars = cars;
    }

    @Override
    public String getSummary() {
        String op = operator == LogicalOperator.AND ? " and " : " or ";

        String summarizersLabel = summarizers.stream()
                .map(s -> s.getVariableName() + " " + s.getLabel())
                .collect(Collectors.joining(op));
        return quantifier.getLabel() + " cars are/have " + summarizersLabel;
    }

    @Override
    public double degreeOfTruth() {
        double sum = 0.0;

        for (Car car : cars) {
            double membership = operator == LogicalOperator.AND ? 1.0 : 0.0;
            for (int i = 0; i < summarizers.size(); i++) {
                double m = summarizers.get(i).getFuzzySet().getMembership(attributes.get(i).apply(car));
                membership = operator == LogicalOperator.AND ? Math.min(membership, m) : Math.max(membership, m);
            }
            sum += membership;
        }
        double r = quantifier.isRelative() ? sum / cars.size() : sum;
        return quantifier.getFuzzySet().getMembership(r);
    }
}