package pl.ksr.measures;

import pl.ksr.Car;
import pl.ksr.summary.single.SingleFirstFormSummary;
import pl.ksr.summary.LinguisticSummary;
import pl.ksr.summary.single.SingleSecondFormSummary;

import java.util.List;

public class T4 implements QualityMeasure {
    // degree of appropriateness
//    @Override
//    public double calculate(LinguisticSummary summary) {
//        List<Summarizer> summarizers = summary.getSummarizers();
//        int m = summary.getCars().size();
//
//        double temp = 1.0;
//        for (Summarizer s : summarizers) {
//            temp *= (double) s.getFuzzySet().getSupport().cardinality() / m;
//        }
//
////        List<Summarizer> summarizers = summary.getSummarizers();
////        List<Car> cars = summary.getCars();
////        int m = cars.size();
////
////        double temp = 1.0;
////        for (Summarizer s : summarizers) {
////            int count = 0;
////            for (Car car : cars) {
////                if (s.getFuzzySet().getMembership(car))
////            }
////        }
//
//        double t3 = new T3().calculate(summary);
//
//        return Math.abs(temp - t3);
//    }

    @Override
    public double calculate(LinguisticSummary summary) {
        List<Car> cars = summary.getCars();
        int m = cars.size();

        double temp = 1.0;

        if (summary instanceof SingleFirstFormSummary s) {
            for (int i = 0; i < s.getSummarizers().size(); i++) {
                int count = 0;
                for (Car car : cars) {
                    double membership = s.getSummarizers().get(i).getFuzzySet()
                            .getMembership(s.getAttributes().get(i).apply(car));
                    if (membership > 0.0) count++;
                }
                temp *= (double) count / m;
            }
        } else if (summary instanceof SingleSecondFormSummary s) {
            for (int i = 0; i < s.getSummarizers().size(); i++) {
                int count = 0;
                for (Car car : cars) {
                    double membership = s.getSummarizers().get(i).getFuzzySet()
                            .getMembership(s.getSummarizerAttributes().get(i).apply(car));
                    if (membership > 0.0) count++;
                }
                temp *= (double) count / m;
            }
        }

        double t3 = new T3().calculate(summary);
        return Math.abs(temp - t3);
    }
}
