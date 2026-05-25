package pl.ksr;

public record Summarizer<T>(
        String label,
        FuzzySet<T> fuzzySet
) {}
