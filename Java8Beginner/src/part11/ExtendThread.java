package part11;

public class ExtendThread {
    public static void main(String[] args) {
        System.out.println("MAIN SART");
        MyThread myThread = new MyThread("Поток 1");
        MyThread myThread2 = new MyThread("Поток 2");
        MyThread myThread3 = new MyThread("Поток 3");

        try {
            int i = 0;
            do {
                if (i % 10 == 0) {
                    System.out.println(" Проход номер " + i);
                } // if
                i++;
                Thread.sleep(100);
            } while (myThread.isAlive() || myThread2.isAlive() || myThread3.isAlive());
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
        System.out.println("MAIN STOP");
    }
} // class ExtendThread

class MyThread extends Thread {

    MyThread(String name) {
        super(name);
        this.start();
    } // const

    @Override
    public void run() {
        System.out.println("Начало выполнения потока " + getName());
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println("\t sleep in " + getName());
                Thread.sleep(400);
            } // for
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
        System.out.println("Завершение потока " + getName());
    } // run()
}
