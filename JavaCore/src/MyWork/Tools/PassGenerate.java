package MyWork.Tools;

import java.util.Random;

import static MyWork.Config.PASS_LENGHT;
import static MyWork.Config.PASS_SIMBOLS;

public class PassGenerate {

    public static void main(String[] args) {
        System.out.println(getPass());
    }

    public static String getPass() {
        StringBuilder result = new StringBuilder();
        Random random = new Random();

        for(int i=0; i <= PASS_LENGHT; i++) {
            result.append(PASS_SIMBOLS.charAt(random.nextInt(PASS_SIMBOLS.length()-1)));
        }

        return result.toString();
    } // ** getPass()
} // class PassGenerate
