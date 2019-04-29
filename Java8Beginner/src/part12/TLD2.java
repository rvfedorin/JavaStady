package part12;

public class TLD2 {

    public static void main(String[] args) {
        TLS tls = new TLS();
        int i = 0;
        while(i < 5) {
            if(tls.isChanged())
                i++;
        }

        tls.cancel();
    }
}

enum TL {
    GREEN(5000), YELLOW(1000), RED(6000);

    private int delay;

    TL(int delay) {
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }
}

class TLS implements Runnable {
    private Thread thrd;
    private TL trafficLight;
    private boolean stop = false;
    private boolean changed = false;
    private boolean startColor = true;

    TLS(TL init) {
        trafficLight = init;
        thrd = new Thread(this);
        thrd.start();
    }

    TLS() {
        this(TL.RED);
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                for(TL tl: TL.values()) {
                    if(tl != trafficLight && startColor)
                        continue;
                    System.out.println(tl);
                    Thread.sleep(tl.getDelay());
                    changed = true;
                    startColor = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    } // ** run()

    public boolean isChanged() {
        if(changed) {
            changed = false;
            return true;
        } else {
            return false;
        }
    }

    void cancel() {
        stop = true;
    }

} // ** TrafficLightSimulator