package pl.ksr.sets;

import pl.ksr.membershipFunctions.MembershipFunction;
import pl.ksr.universe.DiscreteUniverse;
import pl.ksr.universe.UniverseOfDiscourse;

import java.util.ArrayList;
import java.util.List;

public class FuzzySet {
    private UniverseOfDiscourse universeOfDiscourse;
    private MembershipFunction membershipFunction;

    public FuzzySet(UniverseOfDiscourse universeOfDiscourse, MembershipFunction membershipFunction) {
        this.universeOfDiscourse = universeOfDiscourse;
        this.membershipFunction = membershipFunction;
    }

    public double getMembership(double x) {
        return membershipFunction.getMembership(x);
    }

    public FuzzySet complement() {
        return new FuzzySet(universeOfDiscourse, x -> 1.0 - getMembership(x));
    }

    public FuzzySet union(FuzzySet other) {
        return new FuzzySet(universeOfDiscourse, x -> Math.max(getMembership(x), other.getMembership(x)));
    }

    public FuzzySet intersection(FuzzySet other) {
        return new FuzzySet(universeOfDiscourse, x -> Math.min(getMembership(x), other.getMembership(x)));
    }

    public ClassicSet getSupport() {
        List<Double> supp = new ArrayList<>();

        for (double x : universeOfDiscourse.getElements()) {
            if (getMembership(x) > 0.0) {
                supp.add(x);
            }
        }

        return new ClassicSet(supp);
    }

    public ClassicSet getAlphaCut(double alpha) {
        List<Double> cut = new ArrayList<>();

        for (double x : universeOfDiscourse.getElements()) {
            if (getMembership(x) >= alpha) {
                cut.add(x);
            }
        }

        return new ClassicSet(cut);
    }

    public double getHeight() {
        double maxHeight = 0.0;

        for (double x : universeOfDiscourse.getElements()) {
            maxHeight = Math.max(maxHeight, getMembership(x));
        }

        return maxHeight;
    }

    public boolean isNormal() {
        return getHeight() > 1.0 - 1e-9;
    }

    public boolean isConvex() {
        List<Double> elements = universeOfDiscourse.getElements();
        int L = 0;
        int R = universeOfDiscourse.getElements().size() - 1;

        while (L < R && getMembership(elements.get(L)) <= getMembership(elements.get(L + 1))) {
            L++;
        }

        while (R > L && getMembership(elements.get(R)) <= getMembership(elements.get(R - 1))) {
            R--;
        }

        return L >= R;
    }

    public double cardinality() {
        double cardinality = 0.0;
        for (double x : universeOfDiscourse.getElements()) {
            cardinality += getMembership(x);
        }

        return cardinality;
    }

    public double degreeOfFuzziness() {
        return (double) getSupport().cardinality() / universeOfDiscourse.getUniverseSize();
    }

    public MembershipFunction getMembershipFunction() {
        return membershipFunction;
    }

    public UniverseOfDiscourse getUniverseOfDiscourse() {
        return universeOfDiscourse;
    }
}
