/*
 * Proprietary software.
 * 
 */
package rv.fedorin.permutations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

/**
 *
 * @author R. V. Fedorin
 */
public class Permutation {

    public static HashSet<String> withCycle(int[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        HashSet<String> allCombinations = new HashSet<String>();
        int count = countCombinations(data.length);
        allCombinations.add(inString(data));

        if (data.length == 1) {
            allCombinations.add(inString(data));
        } else {
            while (count >= 0) {
                int size = data.length;
                int i = 0;
                int j;

                // find out the first position for change
                for (int a = 0; a < size - 1; a++) {
                    if (data[a] < data[a + 1]) {
                        i = a;
                    }
                }

                j = i + 1;
                for (int a = j; a < size; a++) {
                    if (data[a] > data[i]) {
                        j = a;
                    }
                }

//                System.out.println("i=" + i + ", j=" + j);
                if (j == i) { // if not found position then return data
                    allCombinations.add(inString(data));
                    break;
                }

                int temp = data[i];
                data[i] = data[j];
                data[j] = temp;
                tailReverse(i + 1, data);

                allCombinations.add(inString(data));
                count--;
            }
        } // if data.lengh > 1

        return allCombinations;
    }

    public static HashSet<String> withRecurs(int[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        HashSet<String> allCombinations = new HashSet<String>();

        return allCombinations;
    }

    public static int countCombinations(int lenElements) {
        int result = 1;
        if (lenElements <= 1) {
            return result;
        }

        int loops = lenElements;

        while (loops > 1) {
            result *= loops--;
        }

        return result;
    }

    public static void tailReverse(int start, int[] data) {
        if (data == null || data.length <= 1) {
            return;
        }

        int opposite = data.length - 1;
        for (int i = start; i < data.length; i++) {
            if (i >= opposite || i >= data.length) {
                return;
            }

            int temp = data[i];
            data[i] = data[opposite];
            data[opposite--] = temp;
        }
    }

    private static String inString(int[] data) {
        StringBuffer sb = new StringBuffer();
        for (int i : data) {
            sb.append(i);
        }
        return sb.toString();
    }

    public static void main(String[] args) {

        int test[] = {1, 2, 3, 4, 5};
        int count = Permutation.countCombinations(test.length);
        System.out.println("Искомое количесвто комбинаций: " + count);
        HashSet<String> res = Permutation.withCycle(test);
        System.out.println("Полученое количесвто комбинаций: " + res.size());

        int lineNum = 1;
        for (String comb : res) {
            System.out.println(lineNum++ + ": " + comb);
        }
    }

}
