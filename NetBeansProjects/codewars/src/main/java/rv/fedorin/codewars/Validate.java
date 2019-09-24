/*
 * Proprietary software.
 * 
 */
package rv.fedorin.codewars;

import java.util.Arrays;

/**
 *
 * @author R. V. Fedorin
 */
public class Validate {

    public static boolean validate(String n) {
        
        int[] convertedNum = everySecondToDouble(n);
        int sum = Arrays.stream(convertedNum).sum();
        return (sum % 10) != 0;
    }

    public static int[] everySecondToDouble(String n) {
        boolean second = false;
        int[] out = new int[n.length()];
        String[] byNum = n.split("");
        for (int i = n.length() - 1; i >= 0; i--) {
            int num = Integer.parseInt(byNum[i]);
            if (second ^= true) {
                out[i] = num;
            } else {
                num += num;
                if (num > 9) {
                    num = num - 9;
                }
                out[i] = num;
            }
        }

        return out;
    }

    public static void main(String[] args) {
        System.out.println(validate("12345"));
        System.out.println(validate("891"));
        System.out.println(Arrays.toString(everySecondToDouble("4111111111111111")));
        System.out.println(validate("4111111111111111"));
        System.out.println("5 " + (int)'5' + " " + (int)('5' - '0'));
    }
}
