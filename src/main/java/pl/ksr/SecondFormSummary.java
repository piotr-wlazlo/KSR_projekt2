package pl.ksr;

public record SecondFormSummary<T> (
    Quantifier quantifier,
    String subject,
    Summarizer<T> summarizer,
    Summarizer<T> qualifier
) implements LinguisticSummary {
    @Override
    public String getSummary() {
        return quantifier.label() + subject + " being/having " + qualifier.label() + " is/have " + summarizer.label();
    }
}
