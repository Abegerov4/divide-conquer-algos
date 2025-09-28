package algorithms;

import metrics.Metrics;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

class DeterministicSelectTest {

    @Test
    void testSelectKthSmallest() {
        Metrics metrics = new Metrics();
        DeterministicSelect selector = new DeterministicSelect(metrics);
        int[] array = {5, 2, 8, 1, 9, 3, 7, 4, 6};

        // Test various k values
        assertEquals(1, selector.select(array.clone(), 0)); // smallest
        assertEquals(5, selector.select(array.clone(), 4)); // median
        assertEquals(9, selector.select(array.clone(), 8)); // largest

        assertTrue(metrics.getComparisons() > 0);
    }

    @Test
    void testSelectComparedWithSorting() {
        Metrics metrics = new Metrics();
        DeterministicSelect selector = new DeterministicSelect(metrics);

        // Test on multiple random arrays
        for (int i = 0; i < 10; i++) {
            int[] array = generateRandomArray(100);
            int[] sorted = array.clone();
            Arrays.sort(sorted);

            for (int k = 0; k < array.length; k += 20) { // Test every 20th element
                int[] testArray = array.clone();
                int result = selector.select(testArray, k);
                assertEquals(sorted[k], result,
                        "k=" + k + " should be " + sorted[k] + " but got " + result);
                metrics.reset();
            }
        }
    }

    @Test
    void testSelectEdgeCases() {
        Metrics metrics = new Metrics();
        DeterministicSelect selector = new DeterministicSelect(metrics);

        // Single element
        int[] single = {42};
        assertEquals(42, selector.select(single, 0));

        // Two elements
        int[] two = {2, 1};
        assertEquals(1, selector.select(two.clone(), 0));
        assertEquals(2, selector.select(two.clone(), 1));

        // All duplicates
        int[] duplicates = {5, 5, 5, 5, 5};
        assertEquals(5, selector.select(duplicates, 2));
    }

    @Test
    void testSelectInvalidInput() {
        Metrics metrics = new Metrics();
        DeterministicSelect selector = new DeterministicSelect(metrics);

        // Empty array
        assertThrows(IllegalArgumentException.class, () -> selector.select(new int[0], 0));

        // k out of bounds
        int[] array = {1, 2, 3};
        assertThrows(IllegalArgumentException.class, () -> selector.select(array, -1));
        assertThrows(IllegalArgumentException.class, () -> selector.select(array, 3));
    }

    private int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * size * 10);
        }
        return array;
    }
}