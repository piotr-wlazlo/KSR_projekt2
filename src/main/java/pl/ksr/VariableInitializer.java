package pl.ksr;

import pl.ksr.membershipFunctions.BellFunction;
import pl.ksr.membershipFunctions.GaussianFunction;
import pl.ksr.membershipFunctions.MembershipFunction;
import pl.ksr.membershipFunctions.TrapezoidalFunction;
import pl.ksr.sets.FuzzySet;
import pl.ksr.universe.DiscreteUniverse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VariableInitializer {
        public static LinguisticVariable initializeYear() {
                List<Double> yearList = new ArrayList<>();
                for (int i = 1981; i <= 2021; i++) {
                        yearList.add((double) i);
                }

                DiscreteUniverse yearUniverse = new DiscreteUniverse(yearList);

                MembershipFunction funcAbout1983 = new TrapezoidalFunction(1981, 1981, 1985, 1987);
                MembershipFunction funcAbout1995 = new TrapezoidalFunction(1985, 1987, 2000, 2005);
                MembershipFunction funcAbout2010 = new TrapezoidalFunction(2000, 2005, 2017, 2020);
                MembershipFunction funcAfter2021 = new TrapezoidalFunction(2017, 2020, 2021, 2021);

                FuzzySet about1983 = new FuzzySet(yearUniverse, funcAbout1983);
                FuzzySet about1995 = new FuzzySet(yearUniverse, funcAbout1995);
                FuzzySet about2010 = new FuzzySet(yearUniverse, funcAbout2010);
                FuzzySet about2021 = new FuzzySet(yearUniverse, funcAfter2021);

                LinguisticVariable year = new LinguisticVariable("Year", yearUniverse);
                year.addLabel("about 1983", about1983);
                year.addLabel("about 1995", about1995);
                year.addLabel("about 2010", about2010);
                year.addLabel("about 2021", about2021);

                return year;
        }

        public static LinguisticVariable initializePrice() {
                List<Double> priceList = new ArrayList<>();
                for (int i = 165; i <= 3299995; i++) {
                        priceList.add((double) i);
                }

                DiscreteUniverse priceUniverse = new DiscreteUniverse(priceList);

                MembershipFunction funcAbout7500 = new TrapezoidalFunction(165, 165, 15000, 20000);
                MembershipFunction funcAbout25000 = new TrapezoidalFunction(10000, 20000, 30000, 40000);
                MembershipFunction funcAbout50000 = new TrapezoidalFunction(30000, 40000, 60000, 70000);
                MembershipFunction funcMuchOver60000 = new TrapezoidalFunction(60000, 70000, 3299995, 3299995);

                FuzzySet about7500 = new FuzzySet(priceUniverse, funcAbout7500);
                FuzzySet about25000 = new FuzzySet(priceUniverse, funcAbout25000);
                FuzzySet about50000 = new FuzzySet(priceUniverse, funcAbout50000);
                FuzzySet muchOver60000 = new FuzzySet(priceUniverse, funcMuchOver60000);

                LinguisticVariable price = new LinguisticVariable("Price", priceUniverse);
                price.addLabel("about 7500", about7500);
                price.addLabel("about 25000", about25000);
                price.addLabel("about 50000", about50000);
                price.addLabel("much over 60000", muchOver60000);

                return price;
        }

        public static LinguisticVariable initializeEngineDisplacement() {
                List<Double> engineDisplacementList = new ArrayList<>();
                for (int i = 1000; i <= 84000; i++) {
                        engineDisplacementList.add((double) i);
                }

                DiscreteUniverse engineDisplacementUniverse = new DiscreteUniverse(engineDisplacementList);

                MembershipFunction funcAbout1500 = new TrapezoidalFunction(1000, 1000, 1800, 2200);
                MembershipFunction funcAbout2500 = new TrapezoidalFunction(1800, 2200, 3200, 3800);
                MembershipFunction funcAbout4500 = new TrapezoidalFunction(3200, 3800, 5200, 6000);
                MembershipFunction funcMuchOver5200 = new TrapezoidalFunction(5200, 6000, 8400, 8400);

                FuzzySet about1500 = new FuzzySet(engineDisplacementUniverse, funcAbout1500);
                FuzzySet about2500 = new FuzzySet(engineDisplacementUniverse, funcAbout2500);
                FuzzySet about4500 = new FuzzySet(engineDisplacementUniverse, funcAbout4500);
                FuzzySet muchOver5200 = new FuzzySet(engineDisplacementUniverse, funcMuchOver5200);

                LinguisticVariable engineDisplacement = new LinguisticVariable("Engine Displacement", engineDisplacementUniverse);
                engineDisplacement.addLabel("about 1500", about1500);
                engineDisplacement.addLabel("about 2500", about2500);
                engineDisplacement.addLabel("about 4500", about4500);
                engineDisplacement.addLabel("much over 5200", muchOver5200);

                return engineDisplacement;
        }

        public static LinguisticVariable initializeHorsepower() {
                List<Double> horsepowerList = new ArrayList<>();
                for (int i = 55; i <= 1001; i++) {
                        horsepowerList.add((double) i);
                }

                DiscreteUniverse horsepowerUniverse = new DiscreteUniverse(horsepowerList);

                MembershipFunction funcAbout100 = new TrapezoidalFunction(55, 55, 120, 170);
                MembershipFunction funcAbout200 = new TrapezoidalFunction(120, 170, 230, 280);
                MembershipFunction funcAbout350 = new TrapezoidalFunction(230, 280, 380, 450);
                MembershipFunction funcMuchOver380 = new TrapezoidalFunction(380, 450, 1001, 1001);

                FuzzySet about100 = new FuzzySet(horsepowerUniverse, funcAbout100);
                FuzzySet about200 = new FuzzySet(horsepowerUniverse, funcAbout200);
                FuzzySet about350 = new FuzzySet(horsepowerUniverse, funcAbout350);
                FuzzySet muchOver380 = new FuzzySet(horsepowerUniverse, funcMuchOver380);

                LinguisticVariable horsepower = new LinguisticVariable("Horsepower", horsepowerUniverse);
                horsepower.addLabel("about 100", about100);
                horsepower.addLabel("about 200", about200);
                horsepower.addLabel("about 350", about350);
                horsepower.addLabel("much over 380", muchOver380);

                return horsepower;
        }

        public static LinguisticVariable initializeTorque() {
                List<Double> torqueList = new ArrayList<>();
                for (int i = 79; i <= 1280; i++) {
                        torqueList.add((double) i);
                }

                DiscreteUniverse torqueUniverse = new DiscreteUniverse(torqueList);

                MembershipFunction funcAbout125 = new TrapezoidalFunction(79, 79, 180, 240);
                MembershipFunction funcAbout280 = new TrapezoidalFunction(180, 240, 320, 380);
                MembershipFunction funcAbout450 = new TrapezoidalFunction(320, 380, 500, 580);
                MembershipFunction funcMuchOver500 = new TrapezoidalFunction(500, 580, 1280, 1280);

                FuzzySet about125 = new FuzzySet(torqueUniverse, funcAbout125);
                FuzzySet about280 = new FuzzySet(torqueUniverse, funcAbout280);
                FuzzySet about450 = new FuzzySet(torqueUniverse, funcAbout450);
                FuzzySet muchOver500 = new FuzzySet(torqueUniverse, funcMuchOver500);

                LinguisticVariable torque = new LinguisticVariable("Torque", torqueUniverse);
                torque.addLabel("about 125", about125);
                torque.addLabel("about 280", about280);
                torque.addLabel("about 450", about450);
                torque.addLabel("much over 500", muchOver500);

                return torque;
        }

        public static LinguisticVariable initializeMileage() {
                List<Double> mileageList = new ArrayList<>();
                for (int i = 0; i <= 6904811; i++) {
                        mileageList.add((double) i);
                }

                DiscreteUniverse mileageUniverse = new DiscreteUniverse(mileageList);

                MembershipFunction funcAbout25000 = new GaussianFunction(0, 25000);
                MembershipFunction funcAbout85000 = new GaussianFunction(85000, 30000);
                MembershipFunction funcAbout180000 = new GaussianFunction(180000, 40000);
                MembershipFunction funcAbout300000 = new GaussianFunction(300000, 50000);

                FuzzySet about25000 = new FuzzySet(mileageUniverse, funcAbout25000);
                FuzzySet about85000 = new FuzzySet(mileageUniverse, funcAbout85000);
                FuzzySet about180000 = new FuzzySet(mileageUniverse, funcAbout180000);
                FuzzySet about300000 = new FuzzySet(mileageUniverse, funcAbout300000);

                LinguisticVariable mileage = new LinguisticVariable("Mileage", mileageUniverse);
                mileage.addLabel("about 25000", about25000);
                mileage.addLabel("about 85000", about85000);
                mileage.addLabel("about 180000", about180000);
                mileage.addLabel("about 300000", about300000);

                return mileage;
        }

        public static LinguisticVariable initializeFuelTankCapacity() {
                List<Double> fuelTankCapacityList = new ArrayList<>();
                for (int i = 29; i <= 242; i++) {
                        fuelTankCapacityList.add((double) i);
                }

                DiscreteUniverse fuelTankUniverse = new DiscreteUniverse(fuelTankCapacityList);

                MembershipFunction funcAbout35 = new TrapezoidalFunction(29, 29, 40, 55);
                MembershipFunction funcAbout50 = new TrapezoidalFunction(40, 55, 65, 75);
                MembershipFunction funcAbout85 = new TrapezoidalFunction(65, 75, 95, 105);
                MembershipFunction funcMuchOver95 = new TrapezoidalFunction(95, 105, 242, 242);

                FuzzySet about35 = new FuzzySet(fuelTankUniverse, funcAbout35);
                FuzzySet about50 = new FuzzySet(fuelTankUniverse, funcAbout50);
                FuzzySet about85 = new FuzzySet(fuelTankUniverse, funcAbout85);
                FuzzySet muchOver95 = new FuzzySet(fuelTankUniverse, funcMuchOver95);

                LinguisticVariable fuelTankCapacity = new LinguisticVariable("Fuel Tank Capacity", fuelTankUniverse);
                fuelTankCapacity.addLabel("about 35", about35);
                fuelTankCapacity.addLabel("about 50", about50);
                fuelTankCapacity.addLabel("about 85", about85);
                fuelTankCapacity.addLabel("much over 95", muchOver95);

                return fuelTankCapacity;
        }

        public static LinguisticVariable initializeLength() {
                List<Double> lengthList = new ArrayList<>();
                for (int i = 269; i <= 757; i++) {
                        lengthList.add((double) i);
                }

                DiscreteUniverse lengthUniverse = new DiscreteUniverse(lengthList);

                MembershipFunction funcMuchUnder430 = new TrapezoidalFunction(269, 269, 400, 430);
                MembershipFunction funcAbout440 = new TrapezoidalFunction(400, 430, 450, 470);
                MembershipFunction funcAbout480 = new TrapezoidalFunction(450, 470, 490, 510);
                MembershipFunction funcMuchOver490 = new TrapezoidalFunction(490, 510, 757, 757);

                FuzzySet muchUnder430 = new FuzzySet(lengthUniverse, funcMuchUnder430);
                FuzzySet about440 = new FuzzySet(lengthUniverse, funcAbout440);
                FuzzySet about480 = new FuzzySet(lengthUniverse, funcAbout480);
                FuzzySet muchOver490 = new FuzzySet(lengthUniverse, funcMuchOver490);

                LinguisticVariable length = new LinguisticVariable("Length", lengthUniverse);
                length.addLabel("much under 430", muchUnder430);
                length.addLabel("about 440", about440);
                length.addLabel("about 480", about480);
                length.addLabel("much over 490", muchOver490);

                return length;
        }

        public static LinguisticVariable initializeWheelbase() {
                List<Double> wheelbaseList = new ArrayList<>();
                for (int i = 187; i <= 519; i++) {
                        wheelbaseList.add((double) i);
                }

                DiscreteUniverse wheelbaseUniverse = new DiscreteUniverse(wheelbaseList);

                MembershipFunction funcAbout220 = new TrapezoidalFunction(187, 187, 245, 260);
                MembershipFunction funcAbout265 = new TrapezoidalFunction(245, 260, 270, 285);
                MembershipFunction funcAbout290 = new TrapezoidalFunction(270, 285, 295, 310);
                MembershipFunction funcMuchOver295 = new TrapezoidalFunction(295, 310, 519, 519);

                FuzzySet about220 = new FuzzySet(wheelbaseUniverse, funcAbout220);
                FuzzySet about265 = new FuzzySet(wheelbaseUniverse, funcAbout265);
                FuzzySet about290 = new FuzzySet(wheelbaseUniverse, funcAbout290);
                FuzzySet muchOver295 = new FuzzySet(wheelbaseUniverse, funcMuchOver295);

                LinguisticVariable wheelbase = new LinguisticVariable("Wheelbase", wheelbaseUniverse);
                wheelbase.addLabel("about 220", about220);
                wheelbase.addLabel("about 265", about265);
                wheelbase.addLabel("about 290", about290);
                wheelbase.addLabel("much over 295", muchOver295);

                return wheelbase;
        }

        public static LinguisticVariable initializeDaysOnMarket() {
                List<Double> daysOnMarketList = new ArrayList<>();
                for (int i = 0; i <= 3573; i++) {
                        daysOnMarketList.add((double) i);
                }

                DiscreteUniverse daysOnMarketUniverse = new DiscreteUniverse(daysOnMarketList);

                MembershipFunction funcFresh = new BellFunction(10, 2.5, 0);
                MembershipFunction funcActive = new BellFunction(15, 2.5, 22);
                MembershipFunction funcSlowMoving = new BellFunction(15, 2.5, 48);
                MembershipFunction funcStale = new BellFunction(20, 2.5, 80);

                FuzzySet fresh = new FuzzySet(daysOnMarketUniverse, funcFresh);
                FuzzySet active = new FuzzySet(daysOnMarketUniverse, funcActive);
                FuzzySet slowMoving = new FuzzySet(daysOnMarketUniverse, funcSlowMoving);
                FuzzySet stale = new FuzzySet(daysOnMarketUniverse, funcStale);

                LinguisticVariable daysOnMarket = new LinguisticVariable("Days On Market", daysOnMarketUniverse);
                daysOnMarket.addLabel("fresh", fresh);
                daysOnMarket.addLabel("active", active);
                daysOnMarket.addLabel("slow-moving", slowMoving);
                daysOnMarket.addLabel("stale", stale);

                return daysOnMarket;
        }

        public static final List<String> FUEL_TYPES = List.of(
                "Biodiesel", "Compressed Natural Gas", "Diesel",
                "Flex Fuel Vehicle", "Gasoline", "Hybrid", "Propane"
        );

        public static final List<String> MAKES = List.of(
                "AM General", "Acura", "Alfa Romeo", "Aston Martin", "Audi",
                "BMW", "Bentley", "Bugatti", "Buick", "Cadillac", "Chevrolet",
                "Chrysler", "Daewoo", "Dodge", "Eagle", "FIAT", "Ferrari",
                "Ford", "GMC", "Genesis", "Geo", "Honda", "Hummer",
                "Hyundai", "INFINITI", "Isuzu", "Jaguar", "Jeep", "Kia",
                "Lamborghini", "Land Rover", "Lexus", "Lincoln", "Lotus", "MINI",
                "Maserati", "Maybach", "Mazda", "McLaren", "Mercedes-Benz",
                "Mercury", "Mitsubishi", "Nissan", "Oldsmobile", "Pininfarina",
                "Plymouth", "Pontiac", "Porsche", "RAM", "Rolls-Royce", "SRT",
                "Saab", "Saturn", "Scion", "Subaru", "Suzuki", "Toyota",
                "Volkswagen", "Volvo", "smart"
        );

        public static LinguisticVariable initializeMakeName() {
                List<Double> makeList = new ArrayList<>();
                for (int i = 0; i < MAKES.size(); i++) {
                        makeList.add((double) i);
                }
                DiscreteUniverse universe = new DiscreteUniverse(makeList);
                LinguisticVariable variable = new LinguisticVariable("Make Name", universe);

                for (int i = 0; i < MAKES.size(); i++) {
                        final double targetIndex = i;
                        FuzzySet crispSet = new FuzzySet(universe, x -> x == targetIndex ? 1.0 : 0.0);
                        variable.addLabel(MAKES.get(i), crispSet);
                }
                return variable;
        }

        public static LinguisticVariable initializeFuelType() {
                List<Double> fuelList = new ArrayList<>();
                for (int i = 0; i < FUEL_TYPES.size(); i++) {
                        fuelList.add((double) i);
                }
                DiscreteUniverse universe = new DiscreteUniverse(fuelList);
                LinguisticVariable variable = new LinguisticVariable("Fuel Type", universe);

                for (int i = 0; i < FUEL_TYPES.size(); i++) {
                        final double targetIndex = i;
                        FuzzySet crispSet = new FuzzySet(universe, x -> x == targetIndex ? 1.0 : 0.0);
                        variable.addLabel(FUEL_TYPES.get(i), crispSet);
                }
                return variable;
        }

        public static LinguisticVariable initializeSegment() {
                List<Double> segmentList = List.of(0.0, 1.0, 2.0, 3.0, 4.0, 5.0);
                DiscreteUniverse universe = new DiscreteUniverse(segmentList);
                LinguisticVariable variable = new LinguisticVariable("Segment", universe);

                String[] labels = {"A", "B", "C", "D", "E", "F"};
                for (int i = 0; i < labels.length; i++) {
                        final double targetIndex = i;
                        FuzzySet crispSet = new FuzzySet(universe, x -> x == targetIndex ? 1.0 : 0.0);
                        variable.addLabel(labels[i], crispSet);
                }
                return variable;
        }
}