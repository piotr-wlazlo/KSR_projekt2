package pl.ksr.summary.multi;

import pl.ksr.*;
import pl.ksr.summary.LinguisticSummary;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FourthForm implements LinguisticSummary {
    public final String sub1; public final List<Car> p1;
    public final String sub2; public final List<Car> p2;
    public final List<Summarizer> sums; public final List<Function<Car, Double>> sumAttrs; public final LogicalOperator sumOp;

    public FourthForm(String sub1, List<Car> p1, String sub2, List<Car> p2, List<Summarizer> sums, List<Function<Car, Double>> sumAttrs, LogicalOperator sumOp) {
        this.sub1 = sub1; this.p1 = p1; this.sub2 = sub2; this.p2 = p2; this.sums = sums; this.sumAttrs = sumAttrs; this.sumOp = sumOp;
    }

    @Override public String getSummary() {
        String opStr = sumOp == LogicalOperator.AND ? " and " : " or ";
        String sLab = sums.stream().map(s -> s.getVariableName() + " " + s.getLabel()).collect(Collectors.joining(opStr));
        return "More " + sub1 + " than " + sub2 + " are/have " + sLab;
    }
    @Override public List<Summarizer> getSummarizers() { return sums; }
    @Override public List<Car> getCars() { return Stream.concat(p1.stream(), p2.stream()).collect(Collectors.toList()); }
    @Override public Quantifier getQuantifier() { return null; } // Forma 4 nie używa kwantyfikatora Q
}