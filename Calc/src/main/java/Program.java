import Calculator.Context;

public class Program {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome, enter keys.");

        Context calc = new Context();
        while (true) {
            char key = (char) System.in.read();
            if (key >= ' ') {
                calc.press(key);
                System.out.println(calc);
            }
        }
    }
}
