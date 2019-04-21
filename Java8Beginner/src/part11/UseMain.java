package part11;

public class UseMain {
    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();

        System.out.println("Имя основного потока: " + mainThread.getName());
        System.out.println("Приоритет основного потока: " + mainThread.getPriority());

        System.out.println("============================== Установка имени и приоритета. ============================");

        mainThread.setName("Thread №1");
        mainThread.setPriority(Thread.NORM_PRIORITY + 1);

        System.out.println("Новое имя основного потока: " + mainThread.getName());
        System.out.println("Новый приоритет основного потока: " + mainThread.getPriority());

    }
}
