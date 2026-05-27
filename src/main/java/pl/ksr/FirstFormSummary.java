package pl.ksr;

public record FirstFormSummary<T>(
        Quantifier quantifier,
        String subject,
        Summarizer<T> summarizer
) implements LinguisticSummary<T> {
    @Override
    public String getSummary() {
        return quantifier.label() + " " + subject + " is/have " + summarizer.label();
    }

    @Override
    public double getQualifierMembership(T element) {
        return 1.0;
    }

    @Override
    public double getQualifierDegreeOfFuzziness() {
        return 0.0;
    }

    @Override
    public double getQualifierRelativeCardinality() {
        return 0.0;
    }
}
