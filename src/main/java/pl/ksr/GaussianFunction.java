package pl.ksr;

public class GaussianFunction<T> implements MembershipFunction<T> {
    private final double c;
    private final double sigma;
    private final java.util.function.ToDoubleFunction<T> extractor;

    public GaussianFunction(double c, double sigma, java.util.function.ToDoubleFunction<T> extractor) {
        this.c = c;
        this.sigma = sigma;
        this.extractor = extractor;
    }

    @Override
    public double getMembership(T element) {
        double x = extractor.applyAsDouble(element);
        return Math.exp(-1.0 / 2.0 * ((x - c) / sigma) * ((x - c) / sigma));
    }
}
