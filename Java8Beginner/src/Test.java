
public class Test {
    public static void main(String[] args) {
        String str = "ABCDEFGHabcdefgHJKLMNOPQRSTUVhjklmnopqrstuv";
        int count = 20;
        int row = 10;

        for (int i=0; i < row; i++) {
            for (int a = 0; a < count; a++) {
                char[] chars = str.toCharArray();
                System.out.print(chars[(int) (str.length() * Math.random())] + " ");
            }
            System.out.println();
        }
    }
}
