/*
 * Proprietary software.
 * 
 */
package rv.fedorin.codewars;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
//import org.junit.runners.JUnit4;

// TODO: Replace examples and use TDD development by writing your own tests

public class FinderTest {
    
    @Test public void sampleTests() {

      String a = ".W.\n"+
                 ".W.\n"+
                 "...",
               
             b = ".W.\n"+
                 ".W.\n"+
                 "W..",
               
             c = "......\n"+
                 "......\n"+
                 "......\n"+
                 "......\n"+
                 "......\n"+
                 "......",
               
             d = "......\n"+
                 "......\n"+
                 "......\n"+
                 "......\n"+
                 ".....W\n"+
                 "....W.",
              
              e = "W.....\n"+
                 "......\n"+
                 "W.W.W.\n"+
                 "......\n"+
                 ".....W\n"+
                 "......",
              
              f = ".W.W..\n"+
                 "...W..\n"+
                 "W.W.W.\n"+
                 "......\n"+
                 "WW.W.W\n"+
                 "......",
              
              g = ".W....\n"+
                  "...W..\n"+
                  "WWWWW.\n"+
                  "......\n"+
                  "WWWWW.\n"+
                  "......";

        assertEquals(true,  Finder.pathFinder(a));
        assertEquals(false, Finder.pathFinder(b));
        assertEquals(true,  Finder.pathFinder(c));
        assertEquals(false,  Finder.pathFinder(d));
        assertEquals(false, Finder.pathFinder(e));
        assertEquals(true, Finder.pathFinder(f));
        assertEquals(true, Finder.pathFinder(g));
    }
}


