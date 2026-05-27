package pl.ksr;

public record Quantifier(
        String label,
        FuzzySet<Double> fuzzySet,
        boolean isRelative
) {
    public double getMembership(Double value) {
        return fuzzySet.getMembership(value);
    }

    public double getDegreeOfFuzziness() {
        return fuzzySet.getDegreeOfFuzziness();
    }

    public double getRelativeCardinality() {
        return fuzzySet.getRelativeCardinality();
    }

    public FuzzySet<Double> getFuzzySet() {
        return fuzzySet;
    }
}