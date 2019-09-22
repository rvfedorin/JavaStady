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
public class FindOutlier {

    static int find(int[] integers) {
        int sum = Arrays.stream(integers).limit(3).map(n -> Math.abs(n) % 2).sum();
        int mod = sum > 1 ? 0 : 1;
        
        return Arrays.stream(integers).parallel().filter(n -> Math.abs(n) % 2 == mod)
                .findFirst().getAsInt();
    }

    public static void main(String[] args) {
        int[] a = new int[]{2, 4, 0, 100, 4, 11, 2602, 36};
        int[] b = new int[]{160, 3, 1719, 19, 11, 13, -21};

        System.out.println("a = " + FindOutlier.find(a));
        System.out.println("b = " + FindOutlier.find(b));

    }

}
