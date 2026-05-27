package pl.ksr.db;

import pl.ksr.Car;
import pl.ksr.DataLoader;

import java.util.List;

public class DbTest {
    public static void main() {
        List<Car> cars = DataLoader.loadFromDb();

        if (!cars.isEmpty()) {
            Car test = cars.getFirst();

            System.out.println(
                    "id: " + test.id() + "\n" +
                            "make name: " + test.makeName() + "\n" +
                            "model name: " + test.modelName() + "\n" +
                            "fuel type: " + test.fuelType() + "\n" +
                            "segment: " + test.segment() + "\n" +
                            "year: " + test.year() + "\n" +
                            "price: " + test.price() + "\n" +
                            "engine displacement: " + test.engineDisplacement() + "\n" +
                            "horsepower: " + test.horsepower() + "\n" +
                            "torque: " + test.torque() + "\n" +
                            "mileage: " + test.mileage() + "\n" +
                            "fuel tank volume: " + test.fuelTankVolume() + "\n" +
                            "length: " + test.length() + "\n" +
                            "wheelbase: " + test.wheelbase() + "\n" +
                            "days on market: " + test.daysOnMarket()
            );
        }
    }
}
