package pl.ksr.membershipFunctions;

public class TrapezoidalFunction implements MembershipFunction {
    private final double a;
    private final double b;
    private final double c;
    private final double d;

    public TrapezoidalFunction(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public double getMembership(double x) {
        if (a < x && x < b) {
            return (x - a) / (b - a);
        } else if (b <= x && x <= c) {
            return 1;
        } else if (c < x && x < d) {
            return (d - x) / (d - c);
        }
        return 0.0;
    }

    public double getA() { return a; }
    public double getB() { return b; }
    public double getC() { return c; }
    public double getD() { return d; }
}