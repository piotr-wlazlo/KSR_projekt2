package pl.ksr;

import pl.ksr.measures.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        List<Integer> year = new ArrayList<>();
        for (int i = 1981; i <= 2021; i++) {
            year.add(i);
        }

        DiscreteUniverse<Integer> yearUniverse = new DiscreteUniverse<>(year);

        System.out.println(yearUniverse);

        MembershipFunction<Integer> funcBefore1985 = new TrapezoidalFunction<>(
                1981, 1981, 1985, 1987,
                Integer::doubleValue
        );

        MembershipFunction<Integer> funcAbout1995 = new TrapezoidalFunction<>(
                1985, 1987, 2000, 2005,
                Integer::doubleValue
        );

        MembershipFunction<Integer> funcAbout2010 = new TrapezoidalFunction<>(
                200, 2005, 2017, 2020,
                Integer::doubleValue
        );

        MembershipFunction<Integer> funcAfter2020 = new TrapezoidalFunction<>(
                2017, 2020, 2021, 2021,
                Integer::doubleValue
        );

        FuzzySet<Integer> before1985 = new FuzzySet<>(yearUniverse, funcBefore1985);
        FuzzySet<Integer> about1995 = new FuzzySet<>(yearUniverse, funcAbout1995);
        FuzzySet<Integer> about2010 = new FuzzySet<>(yearUniverse, funcAbout2010);
        FuzzySet<Integer> after2020 = new FuzzySet<>(yearUniverse, funcAfter2020);

        LinguisticVariable<Integer> yearVar = new LinguisticVariable<>("Year", yearUniverse);

        yearVar.addLabel("before 1985", before1985);
        yearVar.addLabel("about 1995", about1995);
        yearVar.addLabel("about 2010", about2010);
        yearVar.addLabel("after 2020", after2020);

        System.out.println("linguistic variable: " + yearVar.getName());
        System.out.println("labels: " + yearVar.getLabels().keySet());

        FuzzySet<Integer> test = yearVar.getFuzzySetForLabel("about 1995");
        double membership1986 = test.getMembership(1986);
        double membership1987 = test.getMembership(1987);
        double membership1995 = test.getMembership(1995);
        double membership2002 = test.getMembership(2002);
        double membership2005 = test.getMembership(2005);

        System.out.println("membership1986: " + membership1986);
        System.out.println("membership1987: " + membership1987);
        System.out.println("membership1995: " + membership1995);
        System.out.println("membership2002: " + membership2002);
        System.out.println("membership2005: " + membership2005);

        //test dzialania miar
        SummaryEvaluation<String> evaluator = new SummaryEvaluation<>();

        double w = 0.1;
        evaluator.addMeasure(new DegreeOfTruth<>(), w);
        evaluator.addMeasure(new DegreeOfImprecision<>(), w);
        evaluator.addMeasure(new DegreeOfCovering<>(), w);
        evaluator.addMeasure(new DegreeOfAppropriateness<>(), w);
        evaluator.addMeasure(new LengthOfASummary<>(), w);
        evaluator.addMeasure(new DegreeOfQuantifierImprecision<>(), w);
        evaluator.addMeasure(new DegreeOfQuantifierCardinality<>(), w);
        evaluator.addMeasure(new DegreeOfSummarizerCardinality<>(), w);
        evaluator.addMeasure(new DegreeOfQualifierImprecision<>(), w);
        evaluator.addMeasure(new DegreeOfQualifierCardinality<>(), w);

        List<String> dataset = List.of("Rekord1", "Rekord2", "Rekord3", "Rekord4", "Rekord5");

        DiscreteUniverse<String> universe = new DiscreteUniverse<>(Set.copyOf(dataset));

        DiscreteUniverse<Double> qUniverse = new DiscreteUniverse<>(Set.of(0.0, 0.2, 0.4, 0.6, 0.8, 1.0));

        FuzzySet<Double> qSet = new FuzzySet<>(qUniverse, value -> value);
        Quantifier quantifier = new Quantifier("Większość", qSet,true);

        FuzzySet<String> sSet = new FuzzySet<>(universe, element -> 0.67);
        Summarizer<String> summarizer = new Summarizer<>("fajny", sSet);

        FuzzySet<String> wSet = new FuzzySet<>(universe, element -> 0.5);
        Summarizer<String> qualifier = new Summarizer<>("testowych rekordów", wSet);

        LinguisticSummary<String> summary = new SecondFormSummary<>(
                quantifier,
                " test",
                summarizer,
                qualifier
        );

        try {
            evaluator.evaluate(summary, dataset);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}