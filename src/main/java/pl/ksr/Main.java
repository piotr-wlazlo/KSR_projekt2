package pl.ksr;


import pl.ksr.db.DataLoader;
import pl.ksr.membershipFunctions.TrapezoidalFunction;
import pl.ksr.sets.FuzzySet;
import pl.ksr.summary.FirstFormSummary;
import pl.ksr.summary.SecondFormSummary;
import pl.ksr.universe.DenseUniverse;
import pl.ksr.universe.DiscreteUniverse;
import pl.ksr.universe.UniverseOfDiscourse;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main() {
//        List<Double> list1 = new ArrayList<>();
//        list1.add(1.0);
//        list1.add(2.0);
//        list1.add(3.0);
//        list1.add(4.0);
//
//        List<Double> list2 = new ArrayList<>();
//        list2.add(3.0);
//        list2.add(4.0);
//        list2.add(5.0);
//        list2.add(6.0);
//
//        ClassicSet set1 = new ClassicSet(list1);
//        ClassicSet set2 = new ClassicSet(list2);
//
//        ClassicSet intersection = set1.intersection(set2);
//        ClassicSet sum = set1.sum(set2);
//
//        System.out.println("set1: " + set1.getElements());
//        System.out.println("set2: " + set2.getElements());
//        System.out.println("intersection: " + intersection.getElements());
//        System.out.println("sum: " + sum.getElements());

//        List<Double> list = new ArrayList<>();
//        for (int i = 1; i <= 10; i++) {
//            list.add((double) i);
//        }

//        System.out.println(list);
//        DiscreteUniverse universe = new DiscreteUniverse(list);
//        System.out.println(universe.getElements());
//        MembershipFunction func = new TrapezoidalFunction(2.0, 4.0, 7.0, 9.0);
//        FuzzySet fuzzySet = new FuzzySet(universe, func);
//
//        MembershipFunction func2 = x -> {
//            if (x == 3.0 || x == 8.0) return 1.0;
//            if (x == 5.0) return 0.2;
//            return 0.0;
//        };
//
//        FuzzySet fuzzySet2 = new FuzzySet(universe, func2);
//
//        System.out.println("fuzzySet isConvex: " + fuzzySet.isConvex());
//        System.out.println("fuzzySet2 isConvex: " + fuzzySet2.isConvex());


//        List<Car> cars = DataLoader.loadFromDb();
//        LinguisticVariable mileage = VariableInitializer.initializeMileage();
//        LinguisticVariable price = VariableInitializer.initializePrice();
//        LinguisticVariable horsepower = VariableInitializer.initializeHorsepower();
//        LinguisticVariable year = VariableInitializer.initializeYear();
//
//        Quantifier almostNone = new Quantifier(
//                "almost none",
//                new FuzzySet(new DenseUniverse(0.0, 1.0), new TrapezoidalFunction(0.0, 0.0, 0.10, 0.125)),
//                true
//        );
//
//        Quantifier most = new Quantifier(
//                "most",
////                new FuzzySet(new DenseUniverse(0.0, 1.0), new TrapezoidalFunction(0.6, 0.8, 1.0, 1.0)),
//                new FuzzySet(new DenseUniverse(0.0, 1.0), new TrapezoidalFunction(0.0, 0.5, 0.5, 1.0)),
//                true
//        );
//
//        Quantifier aboutHalf = new Quantifier(
//                "abput half",
//                new FuzzySet(new DenseUniverse(0.0, 1.0), new TrapezoidalFunction(0.35, 0.45, 0.55, 0.65)),
//                true
//        );
//
//        Summarizer about180000 = new Summarizer(
//                mileage,
//                "about 180000"
//        );
//
//        FirstFormSummary summary1 = new FirstFormSummary(
//                most,
//                List.of(about180000),
//                List.of(Car::mileage),
//                cars
//        );
//        System.out.print(summary1.getSummary());
//        System.out.println(" [" + summary1.degreeOfTruth() + "]");
//
//        FirstFormSummary summary2 = new FirstFormSummary(
//                almostNone,
//                List.of(about180000),
//                List.of(Car::mileage),
//                cars
//        );
//        System.out.print(summary2.getSummary());
//        System.out.println(" [" + summary2.degreeOfTruth() + "]");
//
//        Summarizer muchOver380 = new Summarizer(
//                horsepower,
//                "much over 380"
//        );
//
//        Summarizer about50000 = new Summarizer(
//                price,
//                "about 50000"
//        );
//
//        FirstFormSummary summary3 = new FirstFormSummary(
//                almostNone,
//                List.of(muchOver380, about50000),
//                List.of(Car::horsepower, Car::price),
//                LogicalOperator.AND,
//                cars
//        );
//        System.out.print(summary3.getSummary());
//        System.out.println(" [" + summary3.degreeOfTruth() + "]");
//
//        FirstFormSummary summary4 = new FirstFormSummary(
//                most,
//                List.of(muchOver380, about50000),
//                List.of(Car::horsepower, Car::price),
//                LogicalOperator.OR,
//                cars
//        );
//        System.out.print(summary4.getSummary());
//        System.out.println(" [" + summary4.degreeOfTruth() + "]");
//
//        System.out.println("\n==============================\n");
//
//        Qualifier priceAbout7500 = new Qualifier(
//                price,
//                "about 7500"
//        );
//
//        Summarizer mileageAbout300000 = new Summarizer(
//                mileage,
//                "about 300000"
//        );
//
//        SecondFormSummary summary5 = new SecondFormSummary(
//                most,
//                List.of(priceAbout7500),
//                List.of(Car::price),
//                List.of(mileageAbout300000),
//                List.of(Car::mileage),
//                cars
//        );
//        System.out.print(summary5.getSummary());
//        System.out.println(" [" + summary5.degreeOfTruth() + "]");
//
//
//
//        Qualifier yearAbout1983 = new Qualifier(
//                year,
//                "about 1983"
//        );
//
////        Qualifier qPriceAbout7500 = new Qualifier(
////                price,
////                "about 7500"
////        );
//
//
//
//        SecondFormSummary summary6 = new SecondFormSummary(
//                most,
//                List.of(priceAbout7500, yearAbout1983),
//                List.of(Car::price, car -> (double) car.year()),
//                LogicalOperator.AND,
//                List.of(mileageAbout300000),
//                List.of(Car::mileage),
//                LogicalOperator.AND,
//                cars
//        );
//        System.out.print(summary6.getSummary());
//        System.out.println(" [" + summary6.degreeOfTruth() + "]");
//
//
//        Quantifier few = new Quantifier(
//                "few",
//                new FuzzySet(new DenseUniverse(0.0, 1.0), new TrapezoidalFunction(0.05, 0.15, 0.25, 0.40)),
//                true
//        );
//
//        FirstFormSummary summary67 = new FirstFormSummary(
//                few,
//                List.of(about50000, muchOver380),
//                List.of(Car::price, Car::horsepower),
//                LogicalOperator.OR,
//                cars
//        );
//
//        System.out.print(summary67.getSummary());
//        System.out.println(" [" + summary67.degreeOfTruth() + "]");

//        UniverseOfDiscourse denseUniverse = new DenseUniverse(3.0, 7.0);
//        System.out.println(denseUniverse.getUniverseSize());
//
//
//        List<Double> list = new ArrayList<>();
//        list.add(0.3);
//        list.add(0.4);
//        list.add(0.5);
//        UniverseOfDiscourse discreteUniverse = new DiscreteUniverse(list);
//        System.out.println(discreteUniverse.getUniverseSize());


    }
}
