package pl.ksr;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class FuzzySet<T> {
    private final Set<T> universeOfDiscourse;
    private final Function<T, Double> membershipFunction;

    public FuzzySet(Set<T> universeOfDiscourse, Function<T, Double> membershipFunction) {
        this.universeOfDiscourse = universeOfDiscourse;
        this.membershipFunction = membershipFunction;
    }

    // TODO: fuzzySet intersection(fuzzySet x)

    // TODO: fuzzySet sum(fuzzySet x)

    boolean isNormal() {
        return getHeight() > 1.0 - 1e-9;
    }

    double getHeight() {
        double maxHeight = 0.0;

        for (T element : universeOfDiscourse) {
            double membership = getMembership(element);
            if (membership > maxHeight) {
                maxHeight = membership;
            }
        }

        return maxHeight;
    }

    Set<T> getSupport() {
        Set<T> support = new HashSet<T>();
        for (T element : universeOfDiscourse) {
            if (getMembership(element) > 0.0) {
                support.add(element);
            }
        }

        return support;
    }

    Set<T> getAlphaCut(double alpha) {
        Set<T> cut = new HashSet<T>();
        for (T element : universeOfDiscourse) {
            if (getMembership(element) >= alpha) {
                cut.add(element);
            }
        }

        return cut;
    }

    double getMembership(T element) {
        return membershipFunction.apply(element);
    }
}
