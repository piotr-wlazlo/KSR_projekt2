package pl.ksr;

public class BellFunction<T> implements MembershipFunction<T> {
    private final double a;
    private final double b;
    private final double c;
    private final java.util.function.ToDoubleFunction<T> extractor;


    public BellFunction(double a, double b, double c, java.util.function.ToDoubleFunction<T> extractor) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.extractor = extractor;
    }

    @Override
    public double getMembership(T element) {
        double x = extractor.applyAsDouble(element);
        return 1.0 / (1.0 + Math.pow(Math.abs((x - c) / a), 2 * b));
    }
}
