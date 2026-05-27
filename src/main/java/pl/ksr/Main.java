package pl.ksr;

import java.util.ArrayList;
import java.util.List;

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
    }
}