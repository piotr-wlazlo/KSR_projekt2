package pl.ksr.summary;

import pl.ksr.Car;
import pl.ksr.Quantifier;
import pl.ksr.Summarizer;

import java.util.List;

public interface LinguisticSummary {
    String getSummary();
    List<Summarizer> getSummarizers();
    List<Car> getCars();
    Quantifier getQuantifier();
}
