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
public class PhoneNumber {
    public static String createPhoneNumber(int[] numbers) {
        return "(" + numbers[0] + numbers[1] + numbers[2] + ") "
                + numbers[3] + numbers[4] + numbers[5] + "-"
                + numbers[6] + numbers[7] + numbers[8] + numbers[9];
  }
    
    public static void main(String[] args) {
        // => returns "(123) 456-7890"
        String out = PhoneNumber.createPhoneNumber(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 0}); 
        System.out.println(out);
    }
}
