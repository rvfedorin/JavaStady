package part9;

public class FirstCatchTest {
    public static void main(String[] args) {
        int[] testM = new int[2];

        try {
            System.out.println("До генерации исключения.");
            testM[3]=10;
            System.out.println("Эта строка не будет отображаться.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Блок катч.");
            System.out.println(e);
        }
        System.out.println("Блок после трай и катч.");
    }



}
