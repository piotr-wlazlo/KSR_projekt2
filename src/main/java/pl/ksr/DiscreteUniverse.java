package pl.ksr;

import java.util.Collection;

public class DiscreteUniverse<T> implements UniverseOfDiscourse<T> {
    private final Collection<T> elements;

    public DiscreteUniverse(Collection<T> elements) {
        this.elements = elements;
    }

    @Override
    public boolean contains(T element) {
        return elements.contains(element);
    }

    public Collection<T> getElements() {
        return elements;
    }
}
