package part11;

public class ExtendThread {
    public static void main(String[] args) {
        System.out.println("MAIN SART");
        MyThread myThread = new MyThread("Поток 1");
        myThread.setPriority(Thread.NORM_PRIORITY+1);
        MyThread myThread2 = new MyThread("Поток 2");
        myThread2.setPriority(Thread.NORM_PRIORITY+2);
        MyThread myThread3 = new MyThread("Поток 3");
        myThread3.setPriority(Thread.NORM_PRIORITY+3);

        try {
            myThread.join();
            System.out.println("Присоединён: " + myThread.getName());
            myThread2.join();
            System.out.println("Присоединён: " + myThread2.getName());
            myThread3.join();
            System.out.println("Присоединён: " + myThread3.getName());

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
