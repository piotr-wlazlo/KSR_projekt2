package pl.ksr;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class VariableInitializer {
    public static void initializeYear() {
        List<Integer> yearList = new ArrayList<>();
        for (int i = 1981; i <= 2021; i++) {
            yearList.add(i);
        }

        DiscreteUniverse<Integer> yearUniverse = new DiscreteUniverse<>(yearList);

//        System.out.println(yearUniverse);

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

        LinguisticVariable<Integer> year = new LinguisticVariable<>("Year", yearUniverse);

        year.addLabel("before 1985", before1985);
        year.addLabel("about 1995", about1995);
        year.addLabel("about 2010", about2010);
        year.addLabel("after 2020", after2020);

//        System.out.println("linguistic variable: " + year.getName());
//        System.out.println("labels: " + year.getLabels().keySet());
//
//        FuzzySet<Integer> test = year.getFuzzySetForLabel("about 1995");
//        double membership1986 = test.getMembership(1986);
//        double membership1987 = test.getMembership(1987);
//        double membership1995 = test.getMembership(1995);
//        double membership2002 = test.getMembership(2002);
//        double membership2005 = test.getMembership(2005);
//
//        System.out.println("membership1986: " + membership1986);
//        System.out.println("membership1987: " + membership1987);
//        System.out.println("membership1995: " + membership1995);
//        System.out.println("membership2002: " + membership2002);
//        System.out.println("membership2005: " + membership2005);
    }

    public static void initializePrice() {
        List<Integer> priceList = new ArrayList<>();
        for (int i = 165; i <= 3299995; i++) {
            priceList.add(i);
        }

        DiscreteUniverse<Integer> priceUniverse = new DiscreteUniverse<>(priceList);

        MembershipFunction<Integer> funcUnder15000 = new TrapezoidalFunction<>(
                165, 165, 15000, 20000,
                Integer::doubleValue
        );

        MembershipFunction<Integer> funcAbout25000 = new TrapezoidalFunction<>(
                10000, 20000, 30000, 40000,
                Integer::doubleValue
        );

        MembershipFunction<Integer> funcAbout50000 = new TrapezoidalFunction<>(
                30000, 40000, 60000, 70000,
                Integer::doubleValue
        );

        MembershipFunction<Integer> funcOver70000 = new TrapezoidalFunction<>(
                60000, 70000, 3299995, 3299995,
                Integer::doubleValue
        );

        FuzzySet<Integer> under15000 = new FuzzySet<>(priceUniverse, funcUnder15000);
        FuzzySet<Integer> about25000 = new FuzzySet<>(priceUniverse, funcAbout25000);
        FuzzySet<Integer> about50000 = new FuzzySet<>(priceUniverse, funcAbout50000);
        FuzzySet<Integer> over70000 = new FuzzySet<>(priceUniverse, funcOver70000);

        LinguisticVariable<Integer> price = new LinguisticVariable<>("Price", priceUniverse);

        price.addLabel("under 15000", under15000);
        price.addLabel("about 25000", about25000);
        price.addLabel("about 50000", about50000);
        price.addLabel("over 70000", over70000);
    }

    public static void initializeEngineCapacity() {
        List<Integer> engineCapacityList = new ArrayList<>();
        for (int i = 1000; i <= 84000; i++) {
            engineCapacityList.add(i);
        }

        DiscreteUniverse<Integer> engineCapacityUniverse = new DiscreteUniverse<>(engineCapacityList);

        MembershipFunction<Integer> funcAbout1500 = new TrapezoidalFunction<>(
                1000, 1000, 1800, 2200,
                Integer::doubleValue
        );

        MembershipFunction<Integer> funcAbout2500 = new TrapezoidalFunction<>(
                1800, 2200, 3200, 3800,
                Integer::doubleValue
        );

        MembershipFunction<Integer> funcAbout4500 = new TrapezoidalFunction<>(
                3200, 3800, 5200, 6000,
                Integer::doubleValue
        );

        MembershipFunction<Integer> funcOver6000 = new TrapezoidalFunction<>(
                5200, 6000, 8400, 8400,
                Integer::doubleValue
        );

        FuzzySet<Integer> about1500 = new FuzzySet<>(engineCapacityUniverse, funcAbout1500);
        FuzzySet<Integer> about2500 = new FuzzySet<>(engineCapacityUniverse, funcAbout2500);
        FuzzySet<Integer> about4500 = new FuzzySet<>(engineCapacityUniverse, funcAbout4500);
        FuzzySet<Integer> over6000 = new FuzzySet<>(engineCapacityUniverse, funcOver6000);

        LinguisticVariable<Integer> engineCapacity = new LinguisticVariable<>("Engine Capacity", engineCapacityUniverse);

        engineCapacity.addLabel("about 1500", about1500);
        engineCapacity.addLabel("about 2500", about2500);
        engineCapacity.addLabel("about 4500", about4500);
        engineCapacity.addLabel("over 6000", over6000);
    }

    public static void initializeHorsepower() {
        List<Integer> horsepowerList = new ArrayList<>();
        for (int i = 55; i <= 1001; i++) {
            horsepowerList.add(i);
        }

        DiscreteUniverse<Integer> horsepowerUniverse = new DiscreteUniverse<Integer>(horsepowerList);

        MembershipFunction<Integer> funcAbout100 = new TrapezoidalFunction<>(
                55, 55, 120, 170,
                Integer::doubleValue
        );

        MembershipFunction<Integer> funcAbout200 = new TrapezoidalFunction<>(
                120, 170, 230, 280,
                Integer::doubleValue
        );

        MembershipFunction<Integer> funcAbout350 = new TrapezoidalFunction<>(
                230, 280, 380, 450,
                Integer::doubleValue
        );

        MembershipFunction<Integer> funcOver450 = new TrapezoidalFunction<>(
                380, 450, 1001, 1001,
                Integer::doubleValue
        );

        FuzzySet<Integer> about100 = new FuzzySet<>(horsepowerUniverse, funcAbout100);
        FuzzySet<Integer> about200 = new FuzzySet<>(horsepowerUniverse, funcAbout200);
        FuzzySet<Integer> about350 = new FuzzySet<>(horsepowerUniverse, funcAbout350);
        FuzzySet<Integer> over450 = new FuzzySet<>(horsepowerUniverse, funcOver450);

        LinguisticVariable<Integer> horsepower = new LinguisticVariable<>("Horsepower", horsepowerUniverse);

        horsepower.addLabel("about 100", about100);
        horsepower.addLabel("about 200", about200);
        horsepower.addLabel("about 350", about350);
        horsepower.addLabel("over 450", over450);
    }
}
