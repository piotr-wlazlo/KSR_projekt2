package pl.ksr.summary;

import pl.ksr.*;

import java.util.List;
import java.util.function.Function;

public interface LinguisticSummary {
    String getSummary();
    Quantifier getQuantifier();
    List<Car> getCars();
    List<Summarizer> getSummarizers();

    default List<Qualifier> getQualifiers() {
        return List.of();
    }

    default List<Function<Car, Double>> getQualifierAttributes() {
        return List.of();
    }

    default LogicalOperator getQualifierOperator() {
        return LogicalOperator.AND;
    }
}