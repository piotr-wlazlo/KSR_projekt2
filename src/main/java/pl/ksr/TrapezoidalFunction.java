package pl.ksr;

public class TrapezoidalFunction<T> implements MembershipFunction<T> {
     private final double a;
     private final double b;
     private final double c;
     private final double d;
     private final java.util.function.ToDoubleFunction<T> extractor;


    public TrapezoidalFunction(double a, double b, double c, double d, java.util.function.ToDoubleFunction<T> extractor) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.extractor = extractor;
    }

    @Override
    public double getMembership(T element) {
        double x = extractor.applyAsDouble(element);

        if (a < x && x < b) {
            return (x - a) / (b - a);
        } else if (b <= x && x <= c) {
            return 1;
        } else if (c < x && x < d) {
            return (d - x) / (d - c);
        }

        return 0.0;
    }
}
