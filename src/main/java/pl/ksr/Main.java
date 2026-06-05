package pl.ksr;

import pl.ksr.db.DataLoader;
import pl.ksr.measures.*;
import pl.ksr.membershipFunctions.TrapezoidalFunction;
import pl.ksr.sets.FuzzySet;
import pl.ksr.summary.FirstFormSummary;
import pl.ksr.summary.LinguisticSummary;
import pl.ksr.summary.SecondFormSummary;
import pl.ksr.universe.DenseUniverse;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Function;

public class Main {

    static class VarConfig {
        LinguisticVariable variable;
        Function<Car, Double> extractor;

        public VarConfig(LinguisticVariable variable, Function<Car, Double> extractor) {
            this.variable = variable;
            this.extractor = extractor;
        }
    }

    public static void main(String[] args) {
        System.out.println("Ładowanie danych z bazy...");
        List<Car> cars = DataLoader.loadFromDb();

        // 1. Zdefiniowanie naszych 20 konkretnych "celów" z obu tabel
        Set<String> targetSummaries = new HashSet<>(List.of(
                // Z Tabeli 1
                "about 500K cars are/have Mileage about 85000",
                "about half cars are/have Mileage about 25000",
                "about 250K cars are/have Mileage about 180000",
                "almost none cars are/have Mileage about 300000",
                "few cars are/have Mileage about 85000",
                "about half cars are/have Price about 25000",
                "about 500K cars are/have Price about 7500",
                "about 500K cars are/have Price about 50000",
                "about half cars are/have Year about 2021",
                "few cars are/have Price about 7500",

                // Z Tabeli 2
                "many cars being/having Price about 50000 are/have Mileage about 25000",
                "almost none cars being/having Price about 50000 are/have Mileage about 300000",
                "few cars being/having Price about 50000 are/have Mileage about 85000",
                "less than 50K cars being/having Mileage about 180000 are/have Price about 50000",
                "almost none cars being/having Mileage about 25000 are/have Price about 7500",
                "less than 50K cars being/having Price much over 60000 are/have Mileage about 25000",
                "less than 50K cars being/having Price much over 60000 are/have Mileage about 85000",
                "few cars being/having Mileage about 85000 are/have Price about 50000",
                "few cars being/having Mileage about 180000 are/have Price about 7500",
                "many cars being/having Price much over 60000 are/have Mileage about 25000"
        ));

        DenseUniverse relativeUniverse = new DenseUniverse(0.0, 1.0);
        DenseUniverse absoluteUniverse = new DenseUniverse(0.0, 2335706.0);

        List<Quantifier> quantifiers = List.of(
                new Quantifier("almost none", new FuzzySet(relativeUniverse, new TrapezoidalFunction(0.0, 0.0, 0.10, 0.15)), true),
                new Quantifier("few", new FuzzySet(relativeUniverse, new TrapezoidalFunction(0.05, 0.15, 0.25, 0.40)), true),
                new Quantifier("about half", new FuzzySet(relativeUniverse, new TrapezoidalFunction(0.35, 0.45, 0.55, 0.65)), true),
                new Quantifier("many", new FuzzySet(relativeUniverse, new TrapezoidalFunction(0.55, 0.65, 0.8, 0.9)), true),
                new Quantifier("almost all", new FuzzySet(relativeUniverse, new TrapezoidalFunction(0.8, 0.9, 1.0, 1.0)), true),
                new Quantifier("less than 50K", new FuzzySet(absoluteUniverse, new TrapezoidalFunction(0, 0, 45000, 50000)), false),
                new Quantifier("about 100K", new FuzzySet(absoluteUniverse, new TrapezoidalFunction(45000, 90000, 110000, 170000)), false),
                new Quantifier("about 250K", new FuzzySet(absoluteUniverse, new TrapezoidalFunction(150000, 240000, 260000, 345000)), false),
                new Quantifier("about 500K", new FuzzySet(absoluteUniverse, new TrapezoidalFunction(350000, 400000, 600000, 800000)), false),
                new Quantifier("about 1M", new FuzzySet(absoluteUniverse, new TrapezoidalFunction(750000, 900000, 1100000, 1250000)), false),
                new Quantifier("about 1.5M", new FuzzySet(absoluteUniverse, new TrapezoidalFunction(1200000, 1400000, 1600000, 2100000)), false),
                new Quantifier("over 2M", new FuzzySet(absoluteUniverse, new TrapezoidalFunction(2000000, 2001000, 2335706, 2335706)), false)
        );

        // Używamy wszystkich 3 zmiennych, bo Year pojawia się w jednym podsumowaniu pierwszej formy
        List<VarConfig> configs = List.of(
                new VarConfig(VariableInitializer.initializeYear(), car -> (double) car.year()),
                new VarConfig(VariableInitializer.initializePrice(), Car::price),
                new VarConfig(VariableInitializer.initializeMileage(), Car::mileage)
        );

        To toMeasure = new To();
        toMeasure.addMeasure(new T1(), 0.50);
        toMeasure.addMeasure(new T2(), 0.05);
        toMeasure.addMeasure(new T3(), 0.05);
        toMeasure.addMeasure(new T4(), 0.05);
        toMeasure.addMeasure(new T5(), 0.05);
        toMeasure.addMeasure(new T6(), 0.05);
        toMeasure.addMeasure(new T7(), 0.05);
        toMeasure.addMeasure(new T8(), 0.05);
        toMeasure.addMeasure(new T9(), 0.05);
        toMeasure.addMeasure(new T10(), 0.05);
        toMeasure.addMeasure(new T11(), 0.05);

        System.out.println("\n--- DANE DO ZAŁĄCZNIKA (FORMAT CSV, SEPARATOR: ŚREDNIK) ---");
        System.out.println("Treść podsumowania;T_opt;T1;T2;T3;T4;T5;T6;T7;T8;T9;T10;T11");

        // Generujemy wszystkie możliwości, ale wypisujemy TYLKO te 20 docelowych
        for (Quantifier q : quantifiers) {
            for (VarConfig config1 : configs) {

                // FORMA PIERWSZA
                for (String label1 : config1.variable.getLabels().keySet()) {
                    FirstFormSummary summary1 = new FirstFormSummary(
                            q, List.of(new Summarizer(config1.variable, label1)), List.of(config1.extractor), cars);

                    if (targetSummaries.contains(summary1.getSummary())) {
                        printMeasures(summary1, toMeasure);
                    }
                }

                // FORMA DRUGA
                for (VarConfig config2 : configs) {
                    if (config1.variable.getName().equals(config2.variable.getName())) continue;

                    for (String qualLabel : config1.variable.getLabels().keySet()) {
                        for (String sumLabel : config2.variable.getLabels().keySet()) {
                            SecondFormSummary summary2 = new SecondFormSummary(
                                    q, List.of(new Qualifier(config1.variable, qualLabel)), List.of(config1.extractor),
                                    List.of(new Summarizer(config2.variable, sumLabel)), List.of(config2.extractor), cars);

                            if (targetSummaries.contains(summary2.getSummary())) {
                                printMeasures(summary2, toMeasure);
                            }
                        }
                    }
                }
            }
        }
    }

    // ZMIANA: %.2f wymusza dokładne zaokrąglenie i wyświetlenie dwóch miejsc po przecinku
    private static void printMeasures(LinguisticSummary summary, To toMeasure) {
        double opt = toMeasure.calculate(summary);
        double t1 = new T1().calculate(summary);
        double t2 = new T2().calculate(summary);
        double t3 = new T3().calculate(summary);
        double t4 = new T4().calculate(summary);
        double t5 = new T5().calculate(summary);
        double t6 = new T6().calculate(summary);
        double t7 = new T7().calculate(summary);
        double t8 = new T8().calculate(summary);
        double t9 = new T9().calculate(summary);
        double t10 = new T10().calculate(summary);
        double t11 = new T11().calculate(summary);

        String resultLine = String.format(Locale.US,
                "%s;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f",
                summary.getSummary(), opt, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11
        ).replace(".", ","); // Zmiana na polskie przecinki

        System.out.println(resultLine);
    }
}