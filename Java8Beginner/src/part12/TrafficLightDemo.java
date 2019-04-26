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
    GREEN, RED, YELLOW
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
                        Thread.sleep(10000);
                        break;
                    case YELLOW:
                        Thread.sleep(2000);
                        break;
                    case RED:
                        Thread.sleep(12000);
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