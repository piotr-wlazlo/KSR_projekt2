package pl.ksr.summary.multi;

import pl.ksr.*;
import pl.ksr.summary.LinguisticSummary;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MultiSecondFormSummary implements LinguisticSummary {
    private final Quantifier quantifier;
    private final List<Car> p1;
    private final String p1Name;
    private final List<Car> p2;
    private final String p2Name;
    private final List<Qualifier> qualifiers;
    private final List<Function<Car, Double>> qualifierAttributes;
    private final LogicalOperator qualifierOperator;
    private final List<Summarizer> summarizers;
    private final List<Function<Car, Double>> summarizerAttributes;
    private final LogicalOperator summarizerOperator;

    public MultiSecondFormSummary(Quantifier quantifier,
                                  List<Car> p1,
                                  String p1Name,
                                  List<Car> p2,
                                  String p2Name,
                                  List<Qualifier> qualifiers,
                                  List<Function<Car, Double>> qualifierAttributes,
                                  LogicalOperator qualifierOperator,
                                  List<Summarizer> summarizers,
                                  List<Function<Car, Double>> summarizerAttributes,
                                  LogicalOperator summarizerOperator) {
        this.quantifier = quantifier;
        this.p1 = p1;
        this.p1Name = p1Name;
        this.p2 = p2;
        this.p2Name = p2Name;
        this.qualifiers = qualifiers;
        this.qualifierAttributes = qualifierAttributes;
        this.qualifierOperator = qualifierOperator;
        this.summarizers = summarizers;
        this.summarizerAttributes = summarizerAttributes;
        this.summarizerOperator = summarizerOperator;
    }

    @Override
    public String getSummary() {
        String qualifierOp = qualifierOperator == LogicalOperator.AND ? " and " : " or ";
        String summarizerOp = summarizerOperator == LogicalOperator.AND ? " and " : " or ";

        String qualifiersLabel = qualifiers.stream()
                .map(q -> q.getVariableName() + " " + (q.isNot() ? "not " : "") + q.getLabel())
                .collect(Collectors.joining(qualifierOp));

        String summarizersLabel = summarizers.stream()
                .map(s -> s.getVariableName() + " " + (s.isNot() ? "not " : "") + s.getLabel())
                .collect(Collectors.joining(summarizerOp));

        return quantifier.getLabel() + " " + p1Name + " compared to " + p2Name + " being/having " + qualifiersLabel + " are/have " + summarizersLabel;
    }

    @Override
    public Quantifier getQuantifier() {
        return quantifier;
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

    @Override
    public List<Qualifier> getQualifiers() {
        return qualifiers;
    }

    @Override
    public List<Function<Car, Double>> getQualifierAttributes() {
        return qualifierAttributes;
    }

    @Override
    public LogicalOperator getQualifierOperator() {
        return qualifierOperator;
    }

    public List<Function<Car, Double>> getSummarizerAttributes() {
        return summarizerAttributes;
    }

    public LogicalOperator getSummarizerOperator() {
        return summarizerOperator;
    }
}