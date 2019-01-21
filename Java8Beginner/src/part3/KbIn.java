package part3;

public class KbIn {
    public static void main(String[] args) throws java.io.IOException {
        char io;

        System.out.println("Ввведите любой символ и нажмите Enter: ");

        io = (char) System.in.read();

        System.out.println(io);
    }
}
