/*
 * Proprietary software.
 * 
 */
package rv.fedorin.codewars;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
//import org.junit.runners.JUnit4;

public class SmallfuckTest {
    @Test
    public void testExamples() {
        // Flips the leftmost cell of the tape
        assertEquals("10101100", Smallfuck.interpreter("*", "00101100"));
        
        // Flips the second and third cell of the tape
        assertEquals("01001100", Smallfuck.interpreter(">*>*", "00101100"));
        
        // Flips all the bits in the tape
        assertEquals("11010011", Smallfuck.interpreter("*>*>*>*>*>*>*>*", "00101100"));
        
        // Flips all the bits that are initialized to 0
        assertEquals("11111111", Smallfuck.interpreter("*>*>>*>>>*>*", "00101100"));
        
        // Goes somewhere to the right of the tape and then flips all bits that are initialized to 1, progressing leftwards through the tape
        assertEquals("00000000", Smallfuck.interpreter(">>>>>*<*<<*", "00101100"));
        
        assertEquals("11111111111111111100000000", Smallfuck.interpreter("*>*>*>*>*>*>*>*>*>*>*>*>*>*>*>*>*>*>", "00000000000000000000000000"));
        assertEquals("000", Smallfuck.interpreter("[[]*>*>*>]", "000"));
        assertEquals("01100", Smallfuck.interpreter("[*>[>*>]>]", "11001"));
        assertEquals("10101", Smallfuck.interpreter("[>[*>*>*>]>]", "10110"));
        assertEquals("11111111", Smallfuck.interpreter("*>*>>*>>>*>*", "00101100"));
        assertEquals("11111111111111111111111111111111111111111111111111111111111111111111111111", Smallfuck.interpreter("*[>*]", "00000000000000000000000000000000000000000000000000000000000000000000000000"));
        assertEquals("000000000000000000000000000000000000000000000000000000000000000000000000000", Smallfuck.interpreter("[>*]", "000000000000000000000000000000000000000000000000000000000000000000000000000"));
        assertEquals("0000000000000000", Smallfuck.interpreter(("<<<*>*>*>*>*>>>*>>>>>*>*"), "0000000000000000"));
        
    }
}
