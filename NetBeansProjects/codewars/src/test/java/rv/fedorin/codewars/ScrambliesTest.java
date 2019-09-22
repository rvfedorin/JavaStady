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
public class ScrambliesTest {
    
    public ScrambliesTest() {
    }

    @Test
    public void testScramble() {
        System.out.println("scramble");
        assertEquals(Scramblies.scramble("rkqodlw", "world"), true);
        assertEquals(Scramblies.scramble("cedewaraaossoqqyt", "codewars"), true);
        assertEquals(Scramblies.scramble("katas", "steak"), false);
        assertEquals(Scramblies.scramble("scriptjavx", "javascript"), false);
        assertEquals(Scramblies.scramble("scriptingjava", "javascript"), true);
        assertEquals(Scramblies.scramble("scriptsjava", "javascripts"), true);
        assertEquals(Scramblies.scramble("javscripts", "javascript"), false);
        assertEquals(Scramblies.scramble("aabbcamaomsccdd", "commas"), true);
        assertEquals(Scramblies.scramble("commas", "commas"), true);
        assertEquals(Scramblies.scramble("sammoc", "commas"), true);
    }
   
}
