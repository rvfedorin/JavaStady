package MyWork.Tools;

import java.util.Random;

import static MyWork.Config.PASS_LENGTH;
import static MyWork.Config.PASS_SYMBOLS;

public class PassGenerate {

    public static void main(String[] args) {
        System.out.println(getPass());
    }

    public static String getPass() {
        StringBuilder result = new StringBuilder();
        Random random = new Random();

        for(int i = 0; i <= PASS_LENGTH; i++) {
            result.append(PASS_SYMBOLS.charAt(random.nextInt(PASS_SYMBOLS.length())));
        }

        return result.toString();
    } // ** getPass()
} // class PassGenerate
