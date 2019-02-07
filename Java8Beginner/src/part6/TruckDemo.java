package part6;

public class TruckDemo {
    public static void main(String[] args) {
        Truck semi = new Truck(2, 200, 7, 44000);
        Truck pickup = new Truck(3, 28, 15, 2000);

        double gallons;
        int dist =252;

        gallons = semi.fuelneeded(dist);

        System.out.println("Гpyзoвик может перевезти " + semi.getCargo() + " фунтов.");
        System.out.println("Для преодоления " + dist + " миль грузовику требуется " + gallons + " галлонов топлива.\n");
        gallons = pickup.fuelneeded(dist);
        System.out.println("Пикaп может перевезти " + pickup.getCargo() + " фунтов.");
        System.out.println("Для преодоления " + dist + " миль пикапу требуется " + gallons + " галлонов топлива.");
    }
} // close class TruckDemo

class Truck extends Vehicle {
    private int cargocap;

    Truck(int p, int f, int m, int c) {
        super(p, f, m);
        cargocap = c;
    }

    int getCargo() {
        return cargocap;
    }

    void putCargo(int c) {
        cargocap = c;
    }

} // close class Truck
