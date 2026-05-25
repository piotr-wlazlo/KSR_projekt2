package pl.ksr;

import java.util.HashSet;
import java.util.Set;

public class ClassicSet<T> {
    private final Set<T> elements;

    public ClassicSet(Set<T> elements) {
        this.elements = new HashSet<>(elements);
    }

    public double getMembership(T element) {
        if (elements.contains(element)) {
            return 1.0;
        } else {
            return 0.0;
        }
    }

    public Set<T> getElements() {
        return elements;
    }

    public ClassicSet<T> intersection(ClassicSet<T> other) {
        Set<T> intersection = new HashSet<>(this.elements);
        intersection.retainAll(other.elements);

        return new ClassicSet<>(intersection);
    }

    public ClassicSet<T> sum(ClassicSet<T> other) {
        Set<T> sum = new HashSet<>(this.elements);
        sum.addAll(other.elements);

        return new ClassicSet<>(sum);
    }

    public ClassicSet<T> complement(ClassicSet<T> universe) {
        Set<T> complement = new HashSet<>(universe.elements);
        complement.removeAll(this.elements);

        return new ClassicSet<>(complement);
    }
}
