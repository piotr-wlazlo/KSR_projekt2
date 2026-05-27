package pl.ksr.measures;

import pl.ksr.LinguisticSummary;
import pl.ksr.QualityMeasure;
import java.util.List;

public class LengthOfASummary<T> implements QualityMeasure<T> {
    @Override
    public double calculate(LinguisticSummary<T> summary, List<T> dataset) {
        // T5 = 2 * (0.5)^|S|.  na razie do testow zrobiony pojedynczy sumaryzator (|S| = 1).
        int sLength = 1;
        return 2.0 * Math.pow(0.5, sLength);
    }

}