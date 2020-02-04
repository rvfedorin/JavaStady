/*
 * Proprietary software.
 * 
 */
package rv.fedorin.codewars;

import java.math.BigInteger;

/**
 *
 * @author R. V. Fedorin
 */
public class Anagrams {

    public BigInteger listPosition(String word) {
        BigInteger result = BigInteger.ONE;
        if (word.length() == 1) {
            return result;
        }

        char[] wordChar = word.toCharArray();
        int len = word.length() - 1;
        for (int j = 0; j <= len; j++) {

            int i = 1;
            while (i != 0) {
                i = len - 1;
                while (i < len && i > 0 && wordChar[i] <= wordChar[i + 1]) {
                    i--;
                }
                j = len;
                while (j > 0 && wordChar[j] >= wordChar[i]) {
                    j--;
                }

                if (i == j) {
                    return result;
                }

                swap(wordChar, i, j);
                int start = i + 1;
                int end = len;

                swapFromAtoB(wordChar, start, end);
                result = result.add(BigInteger.ONE);
                if (wordChar[0] < wordChar[1]) {
                    i = 1;
                }
            }
        }
        return result;
    }

    public static void swapFromAtoB(char[] word, int start, int end) {
        if (start == 0 && end == 0) {
            return;
        }

        for (int i = 0; i <= (end - start) / 2; i++) {
            swap(word, i + start, end - i);
        }
    }

    public static void swap(char[] word, int a, int b) {
        if (a == b) {
            return;
        }
        char t = word[a];
        word[a] = word[b];
        word[b] = t;

    }

    public static void main(String[] args) {
        Anagrams anagram = new Anagrams();

//        System.out.println("Position for 'A'  " + BigInteger.ONE + " -> " + anagram.listPosition("A"));
//        System.out.println("Position for 'ABAB' " + BigInteger.valueOf(2) + " -> " + anagram.listPosition("ABAB"));
//        System.out.println("Position for 'ABDC' " + BigInteger.valueOf(2) + " -> " + anagram.listPosition("ABDC"));
//        System.out.println("Position for 'AAAB' " + BigInteger.ONE + " -> " + anagram.listPosition("AAAB"));
        System.out.println("Position for 'BAAA' " + BigInteger.valueOf(4) + " -> " + anagram.listPosition("BAAA"));
//        System.out.println("Position for 'QUESTION' " + BigInteger.valueOf(24572) + " -> " + anagram.listPosition("QUESTION"));
//        System.out.println("Position for 'BOOKKEEPER' " + BigInteger.valueOf(10743) + " -> " + anagram.listPosition("BOOKKEEPER"));

//        System.out.println("===== " + Anagrams.swap("12345", 2, 3));
    }
}
