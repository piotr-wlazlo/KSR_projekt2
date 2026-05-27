package pl.ksr;

public interface LinguisticSummary<T> {
    String getSummary();
    Quantifier quantifier();
    Summarizer<T> summarizer();

    double getQualifierMembership(T element);

    double getQualifierDegreeOfFuzziness();

    double getQualifierRelativeCardinality();
}
