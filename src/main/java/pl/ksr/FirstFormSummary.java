package pl.ksr;

public record FirstFormSummary<T>(
        Quantifier quantifier,
        String subject,
        Summarizer<T> summarizer
) implements LinguisticSummary {
    @Override
    public String getSummary() {
        return quantifier.label() + " " + subject + " is/have " + summarizer.label();
    }
}
