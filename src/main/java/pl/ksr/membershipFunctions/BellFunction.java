package pl.ksr.membershipFunctions;

public class BellFunction implements MembershipFunction {
    private final double a;
    private final double b;
    private final double c;


    public BellFunction(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double getMembership(double x) {
        return 1.0 / (1.0 + Math.pow(Math.abs((x - c) / a), 2 * b));
    }
}
