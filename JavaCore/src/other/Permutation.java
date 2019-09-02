package other;

import java.util.Arrays;
import java.util.HashSet;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Permutation {


    public static void main(String[] args) {
        Logger logger = Logger.getLogger("frv.permutation");
        logger.setLevel(Level.WARNING);
        StringBuilder sb = new StringBuilder();


        int[] original = {1, 2, 3, 4};
        int size = original.length;
        int count = countCombination(size);
        System.out.println("Counts: " + count);
        HashSet<String> allCombination = new HashSet<>();
        allCombination.add(Arrays.toString(original));

        while (count > 0) {
            int i = 0;
            int j = 0;

            for (int a = 0; a < size - 1; a++) {
                if (original[a] < original[a + 1]) {
                    i = a;
                }
            }

            for (int a = i + 1; a < size; a++) {
                if (original[a] > original[i]) {
                    j = a;
                }
            }


            int temp = original[i];
            original[i] = original[j];
            original[j] = temp;

            sb.append("\n\ti=").append(i).append("; j=").append(j)
                    .append("\n\tbefore ").append(Arrays.toString(original));

            int end = size - 1;
            for (int a = i + 1; a < size; a++) {
                if (end <= a) break;
                sb.append("\n\t\tend=").append(end).append(" ").append(" a=").append(a);
                temp = original[a];
                original[a] = original[end];
                original[end] = temp;
                end--;
            }
            sb.append("\n\tafter ").append(Arrays.toString(original)).append("\n=============\n");

            count--;
            allCombination.add(Arrays.toString(original));

        }
        logger.info(sb.toString());
        printComb(allCombination);

    } // ** main()

    private static int countCombination(int len) {
        if (len == 1) return 1;
        int result = 1;
        for (int i = len; i > 0; i--) {
            result = result * i;
        }
        return result;
    }

    private static void printComb(HashSet<String> s) {
        int num = 1;
        for (String s1 : s) {
            System.out.println(num++ + " " + s1);
        }
    }
}
