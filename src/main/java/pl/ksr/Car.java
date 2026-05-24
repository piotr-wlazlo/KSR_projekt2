package pl.ksr;

public record Car(
        int id,

        // atrybuty nierozmywalne
        String makeName,
        String modelName,
        String fuelType,
        String segment,

        // atrybuty rozmywalne
        int year,
        double price,
        double engineDisplacement,
        double horsepower,
        double torque,
        double mileage,
        double fuelTankVolume,
        double length,
        double wheelbase,
        int daysonmarket
) {}
