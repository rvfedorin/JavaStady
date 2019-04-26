package part12;

public class TrafficLightDemo {

    public static void main(String[] args) {

    }
}

enum TrafficLight {
    GREEN, RED, YELLOW
}

class TrafficLightSimulator implements Runnable {
    private Thread thrd;
    private TrafficLight trafficLight;
    boolean stop = false;
    boolean change = false;

    TrafficLightSimulator(TrafficLight init) {
        trafficLight = init;

        thrd = new Thread(this);
        thrd.start();
    }

    TrafficLightSimulator() {
        this(TrafficLight.RED);
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                switch (trafficLight){
                    case GREEN:
                        Thread.sleep(10000);
                        break;
                    case YELLOW:
                        Thread.sleep(2000);
                        break;
                    case RED:
                        Thread.sleep(12000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            changeColor();
        }
    } // ** run()

    synchronized void changeColor() {
        switch (trafficLight) {
            case RED:
                trafficLight = TrafficLight.GREEN;
                break;
            case YELLOW:
                trafficLight = TrafficLight.RED;
                break;
            case GREEN:
                trafficLight = TrafficLight.YELLOW;
                break;
        }
    }
} // ** TrafficLightSimulator