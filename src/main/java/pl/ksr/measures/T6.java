package pl.ksr.measures;

import pl.ksr.Quantifier;
import pl.ksr.membershipFunctions.TrapezoidalFunction;
import pl.ksr.summary.LinguisticSummary;

public class T6 implements QualityMeasure {
    // Degree of quantifier imprecision
    @Override
    public double calculate(LinguisticSummary summary) {
        Quantifier q = summary.getQuantifier();

        double universeSize = q.isRelative() ? 1.0 : summary.getCars().size();

        if (universeSize == 0) return 0.0;

        double supportWidth;

        if (q.getFuzzySet().getMembershipFunction() instanceof TrapezoidalFunction) {
            TrapezoidalFunction tf = (TrapezoidalFunction) q.getFuzzySet().getMembershipFunction();
            supportWidth = tf.getD() - tf.getA();
        } else {
            supportWidth = universeSize;
        }

        return Math.max(0.0, 1.0 - (supportWidth / universeSize));
    }
}