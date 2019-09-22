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
public class TwoSum {

    public static int[] twoSum(int[] numbers, int target) {
        int[] out = new int[]{0, 1};
        firstLoop:
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if ((numbers[i] + numbers[j]) == target) {
                    out[0] = i;
                    out[1] = j;
                    break firstLoop;
                }
            }
        }
        return out;
    }

    public static int[] twoSum2(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i] + numbers[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSum(new int[]{1, 2, 3}, 4)));;        // new int[]{0,2});
        System.out.println(Arrays.toString(twoSum(new int[]{1234, 5678, 9012}, (5678 + 9012)))); //new int[]{1,2});
        System.out.println(Arrays.toString(twoSum(new int[]{2, 2, 3}, 4)));          //new int[]{0,1});
    }
}
