package pl.ksr;

public record Summarizer<T>(
        String label,
        FuzzySet<T> fuzzySet
) {
    public double getMembership(T element) {
        return fuzzySet.getMembership(element);
    }

    public FuzzySet<T> getFuzzySet() {
        return fuzzySet;
    }
}
