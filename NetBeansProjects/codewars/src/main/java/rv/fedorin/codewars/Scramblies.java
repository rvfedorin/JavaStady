/*
 * Proprietary software.
 * 
 */
package rv.fedorin.codewars;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 *
 * @author R. V. Fedorin
 */
public class Scramblies {

    public static boolean scramble(String str1, String str2) {
        for (int i = 0; i < str1.length(); i ++) {
            str2 = str2.replaceFirst(String.valueOf(str1.charAt(i)), "");
            if (str2.length() == 0) {
                return true;
            } 
        }
        return false;
    }
}
