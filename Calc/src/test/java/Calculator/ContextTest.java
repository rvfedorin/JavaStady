package Calculator;

import static org.junit.Assert.*;

public class ContextTest {

    @org.junit.Test
    public void run() {
        Context context = new Context();
//        int actual = context.run("c2+3=");
//        int expect = 5;
//        assertEquals(expect, actual);

        assertEquals(0, context.run("c"));
        assertEquals(5, context.run("c5"));
        assertEquals(25, context.run("c25"));
        assertEquals(5, context.run("c2+3="));
        assertEquals(254756, context.run("c254756"));
        assertEquals(777, context.run("c123+654="));
        assertEquals(8, context.run("c2+2*2="));
        assertEquals(10, context.run("c5+="));
        assertEquals(100, context.run("c125-25="));
        assertEquals(56, context.run("c7*8="));
        assertEquals(0, context.run("c7234*000="));
        assertEquals(1, context.run("c9/5="));
        assertEquals(5625, context.run("c75*75="));
        assertEquals(45, context.run("c5+15=30="));
    }
}