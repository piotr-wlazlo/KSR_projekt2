package pl.ksr;

import java.util.*;
import java.util.function.Function;

public class FuzzySet<T> {
    private final DiscreteUniverse<T> universeOfDiscourse;
    private final MembershipFunction<T> membershipFunction;

    public FuzzySet(DiscreteUniverse<T> universeOfDiscourse, MembershipFunction<T> membershipFunction) {
        this.universeOfDiscourse = universeOfDiscourse;
        this.membershipFunction = membershipFunction;
    }

    public FuzzySet<T> intersection(FuzzySet<T> x) {
        return new FuzzySet<>(
                this.universeOfDiscourse,
                element -> Math.min(this.getMembership(element), x.getMembership(element))
        );
    }

    public FuzzySet<T> sum(FuzzySet<T> x) {
        return new FuzzySet<>(
                this.universeOfDiscourse,
                element -> Math.max(this.getMembership(element), x.getMembership(element))
        );
    }

    public FuzzySet<T> complement() {
        return new FuzzySet<>(
                this.universeOfDiscourse,
                element -> 1.0 - this.getMembership(element)
        );
    }

    boolean isNormal() {
        return getHeight() > 1.0 - 1e-9;
    }

    double getHeight() {
        double maxHeight = 0.0;

        for (T element : universeOfDiscourse.getElements()) {
            double membership = getMembership(element);
            if (membership > maxHeight) {
                maxHeight = membership;
            }
        }

        return maxHeight;
    }

    public ClassicSet<T> getSupport() {
        Set<T> support = new HashSet<>();
        for (T element : universeOfDiscourse.getElements()) {
            if (getMembership(element) > 0.0) {
                support.add(element);
            }
        }

        return new ClassicSet<>(support);
    }

    public ClassicSet<T> getAlphaCut(double alpha) {
        Set<T> cut = new HashSet<>();
        for (T element : universeOfDiscourse.getElements()) {
            if (getMembership(element) >= alpha) {
                cut.add(element);
            }
        }

        return new ClassicSet<>(cut);
    }

    double getMembership(T element) {
        return membershipFunction.getMembership(element);
    }

    public boolean isEmpty() {
        return getHeight() == 0.0;
    }

    public boolean isConvex(Comparator<T> comparator) {
        List<T> elements = new ArrayList<>(universeOfDiscourse.getElements());
        elements.sort(comparator);

        int left = 0;
        int right = elements.size() - 1;

        while (left < right) {
            if (getMembership(elements.get(left)) > getMembership(elements.get(left + 1))) {
                return false;
            }

            if (getMembership(elements.get(right)) > getMembership(elements.get(right - 1))) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }
}
