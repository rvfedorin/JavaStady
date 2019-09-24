/*
 * Proprietary software.
 * 
 */
package rv.fedorin.codewars;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author R. V. Fedorin
 */
public class Braces {

    /*
        "(){}[]"   =>  True
        "([{}])"   =>  True
        "(}"       =>  False
        "[(])"     =>  False
        "[({})](]" =>  False
     */
    public boolean isValid(String braces) {
        boolean result = false;

        ArrayList<String> bracesArray = new ArrayList(Arrays.asList(braces.split("")));

        int size = bracesArray.size();
        for (int i = 1; i < size; i++) {
            String braceNow = bracesArray.get(i);
            String braceBefore = bracesArray.get(i - 1);

            if (braceNow.equals(")") && braceBefore.equals("(")) {
                bracesArray.remove(braceNow);
                bracesArray.remove(braceBefore);
                i = 0;
            } else if (braceNow.equals("]") && braceBefore.equals("[")) {
                bracesArray.remove(braceNow);
                bracesArray.remove(braceBefore);
                i = 0;
            } else if (braceNow.equals("}") && braceBefore.equals("{")) {
                bracesArray.remove(braceNow);
                bracesArray.remove(braceBefore);
                i = 0;
            }
            size = bracesArray.size();
            if (size == 1) {
                break;
            }
        }

        if (bracesArray.isEmpty()) {
            result = true;
        }
        return result;
    }

    public boolean isValid2(String braces) {
        String b = braces;
        System.out.println(braces);
        for (int i = 0; i < braces.length() / 2; i++) {
            b = b.replaceAll("\\(\\)", "");
            b = b.replaceAll("\\[\\]", "");
            b = b.replaceAll("\\{\\}", "");
            System.out.println("b = " + b);
            if (b.length() == 0) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Braces b = new Braces();
        System.out.println(b.isValid("(){}[]"));
        System.out.println(b.isValid("([{}])"));
        System.out.println(b.isValid("[({})](]"));

        System.out.println("v2 " + b.isValid2("(([))]"));
        System.out.println("v2 " + b.isValid2("(())"));
    }
}
