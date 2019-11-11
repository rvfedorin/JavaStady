/*
 * Proprietary software.
 * 
 */
package rv.fedorin.codewars;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author R. V. Fedorin
 */
public class SpiralizorTest {
    
    public SpiralizorTest() {
    }

    @Test
    public void testSpiralize() {
        System.out.println("spiralize");
        int size = 8;
        int[][] expResult = new int[][] {
                        { 1, 1, 1, 1, 1, 1, 1, 1 },
                        { 0, 0, 0, 0, 0, 0, 0, 1 },
                        { 1, 1, 1, 1, 1, 1, 0, 1 },
                        { 1, 0, 0, 0, 0, 1, 0, 1 },
                        { 1, 0, 1, 0, 0, 1, 0, 1 },
                        { 1, 0, 1, 1, 1, 1, 0, 1 },
                        { 1, 0, 0, 0, 0, 0, 0, 1 },
                        { 1, 1, 1, 1, 1, 1, 1, 1 },
                };
        int[][] result = Spiralizor.spiralize(size);
        assertArrayEquals(expResult, result);
        
        size = 5;
        expResult = new int[][] {
                        { 1, 1, 1, 1, 1 },
                        { 0, 0, 0, 0, 1 },
                        { 1, 1, 1, 0, 1 },
                        { 1, 0, 0, 0, 1 },
                        { 1, 1, 1, 1, 1 }
                };
        result = Spiralizor.spiralize(size);
        assertArrayEquals(expResult, result);
    }

}