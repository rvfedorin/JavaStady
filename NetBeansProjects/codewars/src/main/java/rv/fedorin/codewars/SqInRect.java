/*
 * Proprietary software.
 * 
 */
package rv.fedorin.codewars;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author R. V. Fedorin
 */
public class SqInRect {

    public static List<Integer> sqInRect(int lng, int wdth) {
        int toCut = lng * wdth;
        int sideSq;
        List<Integer> out = new ArrayList<>();
        while(toCut > 0) {
            if(lng > wdth) {
                sideSq = wdth;
                lng -= wdth;
            } else {
                sideSq = lng;
                wdth -= lng;
            }
            out.add(sideSq);
            toCut -= sideSq * sideSq;
        }
        return out;
    }
    
    public static void main(String[] args) {
        System.out.println(sqInRect(5, 3));
        System.out.println(sqInRect(3, 5));
    }
}
