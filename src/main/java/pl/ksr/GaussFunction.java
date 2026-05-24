package pl.ksr;

public class GaussFunction implements MembershipFunction {
    private final double c;
    private final double sigma;

    public GaussFunction(double c, double sigma) {
        this.c = c;
        this.sigma = sigma;
    }

    @Override
    public double getMembership(double x) {
        return Math.exp(-1.0 / 2.0 * ((x - c) / sigma) * ((x - c) / sigma));
    }
}
