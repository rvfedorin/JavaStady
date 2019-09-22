/*
 * Proprietary software.
 * 
 */
package rv.fedorin.codewars;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author R. V. Fedorin
 */
public class TwoSumTest
{
    @Test
    public void basicTests()
    {
        doTest(new int[]{1,2,3},          new int[]{0,2});
        doTest(new int[]{1234,5678,9012}, new int[]{1,2});
        doTest(new int[]{2,2,3},          new int[]{0,1});
    }
    @Test
    public void randomTests()
    {
        for (int tries = 0; tries < 50; tries++)
        {
            int[] numbers = new int[randInt(2, 1000000)], expected = new int[2];
            for (int i = 0; i < numbers.length; i++)
                numbers[i] = randInt(0, 1000) * (randInt(0, 1) < 1 ? 1 : -1);
            expected[0] = expected[1] = randInt(0, numbers.length-1);
            while ( expected[0] == expected[1] ) expected[1] = randInt(0, numbers.length-1);
            doTest(numbers, expected);
        }
    }
    private int randInt(int min, int max)
    {
        return (int)(Math.random() * (max - min + 1)) + min;
    }
    private void doTest(int[] numbers, int[] expected)
    {
        int target = numbers[expected[0]] + numbers[expected[1]];
        int[] actual = TwoSum.twoSum(numbers, target);
        if ( null == actual )
        {
            System.out.format("Received a null\n");
            assertNotNull(actual);
        }
        if ( actual.length != 2 )
        {
            System.out.format("Received an array that's not of length 2\n");
            assertTrue(false);
        }
        int received = numbers[actual[0]] + numbers[actual[1]];
        assertEquals(target, received);
    }
}
