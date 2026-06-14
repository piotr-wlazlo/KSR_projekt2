package pl.ksr.summary.multi;

import pl.ksr.*;
import pl.ksr.summary.LinguisticSummary;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MultiFourthForm implements LinguisticSummary {
    private final List<Car> p1;
    private final String p1Name;
    private final List<Car> p2;
    private final String p2Name;
    private final List<Summarizer> summarizers;
    private final List<Function<Car, Double>> attributes;
    private final LogicalOperator operator;

    public MultiFourthForm(List<Car> p1,
                           String p1Name,
                           List<Car> p2,
                           String p2Name,
                           List<Summarizer> summarizers,
                           List<Function<Car, Double>> attributes,
                           LogicalOperator operator) {
        this.p1 = p1;
        this.p1Name = p1Name;
        this.p2 = p2;
        this.p2Name = p2Name;
        this.summarizers = summarizers;
        this.attributes = attributes;
        this.operator = operator;
    }

    @Override public String getSummary() {
        String op = operator == LogicalOperator.AND ? " and " : " or";

        String summarizersLabel = summarizers.stream()
                .map(s -> s.getVariableName() + " " + (s.isNot() ? "not " : "") + s.getLabel())
                .collect(Collectors.joining(op));

        return "more " + p1Name + " than " +p2Name + " are/have " + summarizersLabel;
    }

    @Override
    public Quantifier getQuantifier() {
        return null;
    }

    @Override
    public List<Car> getCars() {
        return Stream.concat(p1.stream(), p2.stream()).collect(Collectors.toList());
    }

    @Override
    public List<Summarizer> getSummarizers() {
        return summarizers;
    }

    public List<Car> getP1() {
        return p1;
    }

    public String getP1Name() {
        return p1Name;
    }

    public List<Car> getP2() {
        return p2;
    }

    public String getP2Name() {
        return p2Name;
    }

    public List<Function<Car, Double>> getAttributes() {
        return attributes;
    }

    public LogicalOperator getOperator() {
        return operator;
    }
}