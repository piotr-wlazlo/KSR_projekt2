package pl.ksr.sets;

import java.util.ArrayList;
import java.util.List;

public class ClassicSet {
    private List<Double> elements;

    public ClassicSet(List<Double> elements) {
        this.elements = elements;
    }

    public List<Double> getElements() {
        return elements;
    }

    public double getMembership(double x) {
        return elements.contains(x) ? 1.0 : 0.0;
    }

//    public ClassicSet complement(DiscreteUniverse universeOfDiscourse) {
//        List<Double> complement = new ArrayList<>();
//        for (double x : universeOfDiscourse.getElements()) {
//            if (!elements.contains(x)) {
//                complement.add(x);
//            }
//        }
//
//        return new ClassicSet(complement);
//    }

    public ClassicSet intersection(ClassicSet other) {
        List<Double> intersection = new ArrayList<>(this.elements);
        intersection.retainAll(other.elements);

        return new ClassicSet(intersection);
    }

    public ClassicSet union(ClassicSet other) {
        List<Double> union = new ArrayList<>(this.elements);
        for (double x : other.elements) {
            if (!union.contains(x)) {
                union.add(x);
            }
        }

        return new ClassicSet(union);
    }

    public int cardinality() {
        return elements.size();
    }
}
