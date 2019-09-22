/*
 * Proprietary software.
 * 
 */
package rv.fedorin.codewars;

/**
 *
 * @author R. V. Fedorin
 */
public class ReplaceVowel {

    public static String disemvowel(String str) {
        return str.replaceAll("(?i)[euioa]", "");
    }
    
    public static void main(String[] args) {
        String str = "This website is for losers LOL!";
        System.out.println("[*] result: " + disemvowel(str));
    }
}
