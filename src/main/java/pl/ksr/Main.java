package pl.ksr;


import pl.ksr.db.DataLoader;
import pl.ksr.measures.*;
import pl.ksr.membershipFunctions.TrapezoidalFunction;
import pl.ksr.sets.FuzzySet;
import pl.ksr.summary.FirstFormSummary;
import pl.ksr.universe.DenseUniverse;
import java.util.List;

public class Main {
    static void main() {
        List<Car> cars = DataLoader.loadFromDb();
        LinguisticVariable mileage = VariableInitializer.initializeMileage();


        Quantifier aboutHalf = new Quantifier(
                "about half",
                new FuzzySet(new DenseUniverse(0.0, 1.0), new TrapezoidalFunction(0.30, 0.35, 0.65, 0.70)),
                true
        );


        Summarizer about180000 = new Summarizer(
                mileage,
                "about 180000"
        );

        FirstFormSummary summary1 = new FirstFormSummary(
                aboutHalf,
                List.of(about180000),
                List.of(Car::mileage),
                cars
        );

        To to = new To();
        to.addMeasure(new T1(), 0.5);
        to.addMeasure(new T2(), 0.25);
        to.addMeasure(new T3(),  0.05);
        to.addMeasure(new T4(),  0.025);
        to.addMeasure(new T5(),  0.025);
        to.addMeasure(new T6(),  0.025);
        to.addMeasure(new T7(),  0.025);
        to.addMeasure(new T8(),  0.025);
        to.addMeasure(new T9(),  0.025);
        to.addMeasure(new T10(), 0.025);
        to.addMeasure(new T11(), 0.025);

        double degree = to.calculate(summary1);

        System.out.print(summary1.getSummary());
        System.out.println(" [" + degree + "]");

        List<QualityMeasure> allMeasures = List.of(
                new T1(), new T2(), new T3(), new T4(), new T5(),
                new T6(), new T7(), new T8(), new T9(), new T10(), new T11()
        );

        System.out.println("--- DIAGNOSTYKA MIAR ---");
        for (int i = 0; i < allMeasures.size(); i++) {
            QualityMeasure m = allMeasures.get(i);
            double val = m.calculate(summary1);
            System.out.printf("T%d: %f%n", (i + 1), val);
        }
        System.out.println("------------------------");
    }
}
