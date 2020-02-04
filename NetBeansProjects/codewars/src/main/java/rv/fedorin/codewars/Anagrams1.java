/*
 * Proprietary software.
 * 
 */
package rv.fedorin.codewars;

import java.math.BigInteger;
import java.util.HashMap;

/**
 *
 * @author R. V. Fedorin
 */
public class Anagrams1 {

    static HashMap<Integer, BigInteger> cache = new HashMap<>();

    public BigInteger listPosition(String word) {
        BigInteger result = BigInteger.ONE;
        if (word.length() == 1) {
            return result;
        }

        int len = word.length();

        for (int i = 0; i < len - 1; i++) {
            int inv = 0;

            var repeated = new HashMap<Character, Integer>();
            char first = word.charAt(i);
            repeated.put(first, 1);
            for (int j = i + 1; j < len; j++) {
                char next = word.charAt(j);

                if (next < first) {
                    inv++;
                }
                if (!repeated.containsKey(next)) {
                    repeated.put(next, 1);
                } else {
                    repeated.put(next, repeated.get(next) + 1);
                }

            }
            BigInteger devider = BigInteger.ONE;
            for (int v : repeated.values()) {
                if (v > 1) {
                    devider = devider.multiply(factorial(v));
                }
            }
            BigInteger totalComb = BigInteger.valueOf(inv).multiply(factorial(len - (i + 1)));

            if (devider.compareTo(BigInteger.ONE) == 1) {
                totalComb = totalComb.divide(devider);
            }
            result = result.add(totalComb);

        }

        return result;
    }

    public static BigInteger factorial(int n) {
        BigInteger ret;

        if (n == 0) {
            return BigInteger.ONE;
        }
        if (null != (ret = cache.get(n))) {
            return ret;
        }
        ret = BigInteger.valueOf(n).multiply(factorial(n - 1));
        cache.put(n, ret);
        return ret;
    }

    public static void main(String[] args) {
        Anagrams1 anagram = new Anagrams1();

        System.out.println("Position for 'A'  " + BigInteger.ONE + " -> " + anagram.listPosition("A"));
        System.out.println("Position for 'ABAB' " + BigInteger.valueOf(2) + " -> " + anagram.listPosition("ABAB"));
        System.out.println("Position for 'ABDC' " + BigInteger.valueOf(2) + " -> " + anagram.listPosition("ABDC"));
        System.out.println("Position for 'AAAB' " + BigInteger.ONE + " -> " + anagram.listPosition("AAAB"));
        System.out.println("Position for 'BAAA' " + BigInteger.valueOf(4) + " -> " + anagram.listPosition("BAAA"));
        System.out.println("Position for 'BAAAA' " + BigInteger.valueOf(5) + " -> " + anagram.listPosition("BAAAA"));
        System.out.println("Position for 'QUESTION' " + BigInteger.valueOf(24572) + " -> " + anagram.listPosition("QUESTION"));
        System.out.println("Position for 'BOOKKEEPER' " + BigInteger.valueOf(10743) + " -> " + anagram.listPosition("BOOKKEEPER"));
//        System.out.println("===== " + Anagrams.swap("12345", 2, 3));
    }
}
