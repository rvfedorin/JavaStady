package part9;

public class ExcDemo5 {
    public static void main(String[] args) {
        int[] mas1 = {1, 2, 3, 4, 5, 6, 7, 8};
        int[] mas2 = {2, 4, 0, 4, 0, 2};

        for (int i = 0; i < mas1.length; i++) {
            try {
                System.out.println(mas1[i] + " / " + mas2[i] + " = " + mas1[i]/mas2[i]);
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Нет соответсвующего элеьента.");
            } catch (ArithmeticException ex) {
                System.out.println("Деление на нуль.");
            }
        }
     }
}
