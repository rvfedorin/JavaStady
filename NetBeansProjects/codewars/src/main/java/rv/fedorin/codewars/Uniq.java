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
public class Uniq {

    public static double findUniq(double arr[]) {
        double all;
        if (arr[0] == arr[1] || arr[0] == arr[2]) {
            all = arr[0];
        } else {
            return arr[0];
        }
        return Arrays.stream(arr).parallel().filter(n -> n != all).findFirst().getAsDouble();
    }

    public static void main(String[] args) {
        System.out.println(Uniq.findUniq(new double[]{0, 0, 0.55, 0, 0}));
    }
}
