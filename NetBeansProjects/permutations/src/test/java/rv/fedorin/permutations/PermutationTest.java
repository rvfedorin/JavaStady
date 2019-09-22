/*
 * Proprietary software.
 * 
 */
package rv.fedorin.permutations;

import java.util.HashSet;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author R. V. Fedorin
 */
public class PermutationTest {

    public PermutationTest() {
    }

    @Test
    public void testWithCycle() {
        System.out.println("withCycle");
        int[] data = null;
        HashSet expResult = null;
        HashSet result = Permutation.withCycle(data);
        assertEquals(expResult, result);

        data = new int[1];
        int len = 1;
        assertEquals(Permutation.withCycle(data).size(), len);
        
        int[] data1 = Stream.of("123".split(""))
                .mapToInt(Integer::parseInt)
                .toArray();
        assertEquals(Permutation.withCycle(data1).size(), 6);
        
        int[] data2 = Stream.of("12345".split(""))
                .mapToInt(Integer::parseInt)
                .toArray();
        assertEquals(Permutation.withCycle(data2).size(), 120);
    }

    @Test
    public void testCountCombinations() {
        System.out.println("countCombinations");
        int lenElements = 0;
        int expResult = 1;
        int result = Permutation.countCombinations(lenElements);
        assertEquals(expResult, result);

        int[] results = {6, 24, 120, 720};
        for (int i = 3; i < 7; i++) {
            assertEquals(Permutation.countCombinations(i), results[i - 3]);
        }

    }

    @Test
    public void testMain() {
    }

    @Test
    public void testTailReverse() {
        System.out.println("tailReverse");
        int start = 0;
        int[] data = new int[1];
        int[] result = data.clone();
        Permutation.tailReverse(start, result);
        Assert.assertArrayEquals(result, data);

        int[] data1 = {1, 2, 3};
        int[] result1 = {3, 2, 1};
        Permutation.tailReverse(start, data1);
        Assert.assertArrayEquals(result1, data1);

        start = 2;
        int[] data2 = {1, 2, 3, 4, 5, 6};
        int[] result2 = {1, 2, 6, 5, 4, 3};
        Permutation.tailReverse(start, data2);
        Assert.assertArrayEquals(result1, data1);
    }

}
