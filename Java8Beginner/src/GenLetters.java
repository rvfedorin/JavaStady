import java.util.HashMap;
import java.util.Map;

public class GenLetters {
    public static void main(String[] args) {
        String engPart = "ABCDEFGHabcdefgHJKLMNOPQRSTUVhjklmnopqrstuv";
        String eng = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        String rus = "ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ";
        int count = 20;
        int row = 10;

        randomEngLet(eng, row, count);
//        randomRus(rus);

    }

    private static void randomEngLet(String str, int row, int count) {
        char[] chars = str.toCharArray();

        for (int i = 0; i < row; i++) {
            for (int a = 0; a < count; a++) {
                System.out.print(chars[(int) (str.length() * Math.random())] + " ");
            }
            System.out.println();
        }
    } // ** randomEngLet

    private static void randomRus(String str) {
        int row = 10;
        Map<Character, Integer> countLet = new HashMap<>();

        char[] chars = str.toCharArray();

        for(int i = 0; i < row; i++) {
            for(int n=0; n < chars.length; n++) {
                char c = chars[(int) (str.length()*Math.random())];
                countLet.put(c, countLet.getOrDefault(c, 0)+1);
                System.out.print(c + " ");
            }
            System.out.println();
        }
        System.out.println(countLet);
    }
}
