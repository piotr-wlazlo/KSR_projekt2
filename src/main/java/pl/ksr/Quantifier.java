package pl.ksr;

public record Quantifier(
        String label,
        FuzzySet<Double> fuzzySet,
        boolean isRelative
) {}