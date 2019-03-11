package part6;

class Vehicle implements IVehicle {
        private int passengers; // количество пассажров
        private int fuelcap; // емкость топливного бака
        private int mpg; // потребление топлива в млях на галлон

        Vehicle(int р, int f, int m) {
            passengers = р;
            fuelcap = f;
            mpg = m;
        }

// Определить дальность поездк транспортного средства
        public int range() {
                return mpg * fuelcap;
        }

// Рассчитать объем топлива, необходимого транспортному
// средству для преодоления заданного расстояния
        public double fuelneeded ( int miles){
            return (double) miles / mpg;

        }
}

interface IVehicle {
    int range();
    double fuelneeded(int miles);
}