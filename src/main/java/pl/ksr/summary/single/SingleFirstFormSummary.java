package pl.ksr.summary.single;

import pl.ksr.Car;
import pl.ksr.LogicalOperator;
import pl.ksr.Quantifier;
import pl.ksr.Summarizer;
import pl.ksr.summary.LinguisticSummary;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SingleFirstFormSummary implements LinguisticSummary {
    private final Quantifier quantifier;
    private final List<Summarizer> summarizers;
    private final List<Function<Car, Double>> attributes;
    private final LogicalOperator operator;
    private final List<Car> cars;


    public SingleFirstFormSummary(Quantifier quantifier,
                                  List<Summarizer> summarizers,
                                  List<Function<Car, Double>> attributes,
                                  List<Car> cars) {
        this(quantifier, summarizers, attributes, LogicalOperator.AND, cars);
    }

    public SingleFirstFormSummary(Quantifier quantifier,
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
                .map(s -> s.getVariableName() + " " + (s.isNot() ? "not " : "") + s.getLabel())
                .collect(Collectors.joining(op));
        return quantifier.getLabel() + " cars are/have " + summarizersLabel;
    }

    public Quantifier getQuantifier() {
        return quantifier;
    }

    public List<Summarizer> getSummarizers() {
        return summarizers;
    }

    public List<Function<Car, Double>> getAttributes() {
        return attributes;
    }

    public LogicalOperator getOperator() {
        return operator;
    }

    public List<Car> getCars() {
        return cars;
    }
}