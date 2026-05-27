package pl.ksr;

public record SecondFormSummary<T> (
    Quantifier quantifier,
    String subject,
    Summarizer<T> summarizer,
    Summarizer<T> qualifier
) implements LinguisticSummary<T> {
    @Override
    public String getSummary() {
        return quantifier.label() + subject + " being/having " + qualifier.label() + " is/have " + summarizer.label();
    }

    @Override
    public double getQualifierMembership(T element) {
        return qualifier.getMembership(element);
    }

    @Override
    public double getQualifierDegreeOfFuzziness() {
        return qualifier.getFuzzySet().getDegreeOfFuzziness();
    }

    @Override
    public double getQualifierRelativeCardinality() {
        return qualifier.getFuzzySet().getRelativeCardinality();
    }
}
