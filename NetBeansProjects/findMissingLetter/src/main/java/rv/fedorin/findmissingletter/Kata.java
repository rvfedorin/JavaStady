/*
 * Proprietary software.
 * 
 */
package rv.fedorin.findmissingletter;

/**
 *
 * @author R. V. Fedorin
 */
public class Kata {

    public static char findMissingLetter(char[] array) {
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char missedLetter = ' ';

        int start = abc.indexOf(array[0]);
        if (start == -1) {
            abc = abc.toLowerCase();
            start = abc.indexOf(array[0]);
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] != abc.charAt(i + start)) {
                missedLetter = abc.charAt(i + start);
                break;
            }
        }

        return missedLetter;
    }

    public static void main(String[] args) {
        char[] array = new char[]{'a', 'b', 'c', 'd', 'f'};
        char[] array2 = new char[]{'O', 'Q', 'R', 'S'};
        System.out.println("Missing letter is: " + findMissingLetter(array));
        System.out.println("Missing letter is: " + findMissingLetter(array2));
        System.out.println("Missing letter is: " + findMissingLetter(new char[]{'a', 'b'}));
    }
}
