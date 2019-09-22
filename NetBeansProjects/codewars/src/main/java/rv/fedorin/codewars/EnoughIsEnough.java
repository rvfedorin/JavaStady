/*
 * Proprietary software.
 * 
 */
package rv.fedorin.codewars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author R. V. Fedorin
 */
public class EnoughIsEnough {

    public static int[] deleteNth(int[] elements, int maxOccurrences) {
        ArrayList<Integer> out = new ArrayList<>();
        HashMap<Integer, Integer> count = new HashMap<>();

        for (int element : elements) {
            int c = count.getOrDefault(element, 0);
            count.put(element, c + 1);
            if (!(count.get(element) > maxOccurrences)) {
                out.add(element);
            }
        }
        return out.stream().mapToInt(Integer::valueOf).toArray();
    }

    public static void main(String[] args) {
        int[] integers = new int[]{1, 2, 3, 1, 2, 1, 2, 3};
        int N = 2;

        System.out.println(Arrays.toString(EnoughIsEnough.deleteNth(new int[] {1,1,3,3,7,2,2,2,2}, 3)));
        System.out.println(Arrays.toString(EnoughIsEnough.deleteNth(new int[] {20,37,20,21}, 1)));

    }
}
