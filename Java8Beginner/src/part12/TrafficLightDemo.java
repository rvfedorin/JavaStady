package part12;

public class TrafficLightDemo {

    public static void main(String[] args) {
        TrafficLightSimulator tls = new TrafficLightSimulator();

        for(int i=0; i < 10; i++) {
            System.out.println(tls.getColor());
            tls.waitForChange();
        }

        tls.cancel();
    }
}

enum TrafficLight {
    GREEN(10000), YELLOW(2000), RED(12000);

    private int delay;

    TrafficLight(int delay) {
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }
}

class TrafficLightSimulator implements Runnable {
    private Thread thrd;
    private TrafficLight trafficLight;
    private boolean stop = false;
    private boolean change = false;

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
                        Thread.sleep(TrafficLight.GREEN.getDelay());
                        break;
                    case YELLOW:
                        Thread.sleep(TrafficLight.YELLOW.getDelay());
                        break;
                    case RED:
                        Thread.sleep(TrafficLight.RED.getDelay());
                        break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            changeColor();
        }
    } // ** run()

    private synchronized void changeColor() {
        switch (trafficLight) {
            case RED:
                trafficLight = TrafficLight.GREEN;
                break;
            case YELLOW:
                trafficLight = TrafficLight.RED;
                break;
            case GREEN:
                trafficLight = TrafficLight.YELLOW;
        }
        change = true;
        notify();
    }

    public synchronized void waitForChange(){
        try {
            while (!change)
                wait();
            change = false;


        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    TrafficLight getColor() {
        return trafficLight;
    }

    void cancel() {
        stop = true;
    }

} // ** TrafficLightSimulator