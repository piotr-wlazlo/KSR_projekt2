package pl.ksr;

import pl.ksr.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    public static List<Car> loadFromDb() {
        List<Car> cars = new ArrayList<>();

        String query = """
                select 
                    id, 
                    make_name, 
                    model_name, 
                    fuel_type, 
                    segment,
                       
                    year, 
                    price, 
                    engine_displacement, 
                    horsepower, 
                    torque,
                    mileage, 
                    fuel_tank_volume, 
                    length, 
                    wheelbase, 
                    daysonmarket
                from 
                    car
                """;

        try (Connection connection = DB.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String segmentStr = resultSet.getString("segment");
                Segment segment = segmentStr != null ? Segment.valueOf(segmentStr.toUpperCase()) : null;

                Car car = new Car(
                        resultSet.getInt("id"),
                        resultSet.getString("make_name"),
                        resultSet.getString("model_name"),
                        resultSet.getString("fuel_type"),
                        segment,
                        resultSet.getInt("year"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("engine_displacement"),
                        resultSet.getDouble("horsepower"),
                        resultSet.getDouble("torque"),
                        resultSet.getDouble("mileage"),
                        resultSet.getDouble("fuel_tank_volume"),
                        resultSet.getDouble("length"),
                        resultSet.getDouble("wheelbase"),
                        resultSet.getInt("daysonmarket")
                );

                cars.add(car);
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return cars;
    }
}