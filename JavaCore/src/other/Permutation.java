package other;

import java.util.Arrays;
import java.util.HashSet;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Permutation {


    public static void main(String[] args) {
        int[] original = {1, 2, 3, 4};
        HashSet<String> allCombinationC = withCycle(original);
        HashSet<String> allCombinationR = withRecurs(original);
        System.out.println(allCombinationC.equals(allCombinationR));
    } // ** main()

    public static HashSet<String> withCycle(int[] original) {
        Logger logger = Logger.getLogger("frv.permutation");
        logger.setLevel(Level.WARNING);
        StringBuilder sb = new StringBuilder();

        int size = original.length;
        int count = countCombination(size);
        HashSet<String> allCombination = new HashSet<>();
        allCombination.add(arrayIntToString(original));

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
            allCombination.add(arrayIntToString(original));

        }
        logger.info(sb.toString());
        return allCombination;
    }

    public static HashSet<String> withRecurs(int[] original) {
        Object[] allComb = combinationPrefix(original);

        HashSet<String> allCombSet = new HashSet<>();
        for(Object o: allComb) {
            allCombSet.add((String) o);
        }

        return allCombSet;
    }

    private static Object[] combinationPrefix(int[] data) {
        HashSet<String> comb = new HashSet<>();


        if(data.length == 2) {
            comb.add(data[0] + "" + data[1]);
            comb.add(data[1] + "" + data[0]);
        } else {

            Object[] prefixes = combinationPrefix(Arrays.copyOfRange(data, 1, data.length));

            for (Object prefix : prefixes) {
                String newString = String.valueOf(data[0]) + prefix;
                comb.add(newString);
                String[] symbols = newString.split("");

                for (int i = 0; i < symbols.length - 1; i++) {
                    String temp = symbols[i];
                    symbols[i] = symbols[i + 1];
                    symbols[i + 1] = temp;
                    comb.add(String.join("", symbols));
                }
            }
        }
        return comb.toArray();
    }

    private static int countCombination(int len) {
        if (len == 1) return 1;
        int result = 1;
        for (int i = len; i > 0; i--) {
            result = result * i;
        }
        return result;
    }

    private static <T> void printComb(HashSet<T> s) {
        int num = 1;
        for (T s1 : s) {
            System.out.println(num++ + " " + s1);
        }
    }

    private static String arrayIntToString(int[] data) {
        StringBuilder sb = new StringBuilder();
        for(int element: data) {
            sb.append(element);
        }
        return sb.toString();
    }
}
