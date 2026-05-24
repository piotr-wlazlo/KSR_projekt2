package pl.ksr;

public class TrapezoidalFunction implements MembershipFunction {
     private double a;
     private double b;
     private double c;
     private double d;

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
}
