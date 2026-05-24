package pl.ksr;

public class Car {
    private int id;

    // atrybuty nierozmywalne
    private String makeName;
    private String modelName;
    private String fuelType;
    private String segment;

    // atrybuty rozmywalne
    private int year;
    private double price;
    private double engineDisplacement;
    private double horsepower;
    private double torque;
    private double mileage;
    private double fuelTankVolume;
    private double length;
    private double wheelbase;
    private int daysonmarket;

    public Car(String makeName, String modelName, String fuelType, String segment, int year, double price, double engineDisplacement, double horsepower, double torque, double mileage, double fuelTankVolume, double length, double wheelbase, int daysonmarket) {
        this.makeName = makeName;
        this.modelName = modelName;
        this.fuelType = fuelType;
        this.segment = segment;
        this.year = year;
        this.price = price;
        this.engineDisplacement = engineDisplacement;
        this.horsepower = horsepower;
        this.torque = torque;
        this.mileage = mileage;
        this.fuelTankVolume = fuelTankVolume;
        this.length = length;
        this.wheelbase = wheelbase;
        this.daysonmarket = daysonmarket;
    }

    public String getMakeName() {
        return makeName;
    }

    public String getModelName() {
        return modelName;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getSegment() {
        return segment;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public double getEngineDisplacement() {
        return engineDisplacement;
    }

    public double getHorsepower() {
        return horsepower;
    }

    public double getTorque() {
        return torque;
    }

    public double getMileage() {
        return mileage;
    }

    public double getFuelTankVolume() {
        return fuelTankVolume;
    }

    public double getLength() {
        return length;
    }

    public double getWheelbase() {
        return wheelbase;
    }

    public int getDaysonmarket() {
        return daysonmarket;
    }
}
