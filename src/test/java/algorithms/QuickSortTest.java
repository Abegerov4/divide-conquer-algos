package algorithms;

import metrics.Metrics;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

class QuickSortTest {

    @Test
    void testSortRandomArray() {
        Metrics metrics = new Metrics();
        QuickSort sorter = new QuickSort(metrics);
        int[] array = {5, 2, 8, 1, 9, 3};
        int[] expected = {1, 2, 3, 5, 8, 9};

        sorter.sort(array);

        assertArrayEquals(expected, array);
        assertTrue(metrics.getComparisons() > 0);
        // QuickSort should have bounded depth ~2*log2(n)
        int n = array.length;
        int expectedMaxDepth = (int) (2 * (Math.log(n) / Math.log(2))) + 5;
        assertTrue(metrics.getMaxDepth() <= expectedMaxDepth,
                "Depth should be bounded by O(log n), got: " + metrics.getMaxDepth());
    }

    @Test
    void testSortAlreadySorted() {
        Metrics metrics = new Metrics();
        QuickSort sorter = new QuickSort(metrics);
        int[] array = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};

        sorter.sort(array);

        assertArrayEquals(expected, array);
    }

    @Test
    void testSortWithDuplicates() {
        Metrics metrics = new Metrics();
        QuickSort sorter = new QuickSort(metrics);
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6, 5};
        int[] expected = {1, 1, 2, 3, 4, 5, 5, 6, 9};

        sorter.sort(array);

        assertArrayEquals(expected, array);
    }

    @Test
    void testSortLargeArray() {
        Metrics metrics = new Metrics();
        QuickSort sorter = new QuickSort(metrics);
        int[] array = new int[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = array.length - i - 1; // Reverse sorted (worst case for naive QuickSort)
        }

        sorter.sort(array);

        // Verify sorted
        for (int i = 1; i < array.length; i++) {
            assertTrue(array[i] >= array[i - 1]);
        }

        // Verify depth is bounded (thanks to smaller-first recursion)
        int n = array.length;
        int expectedMaxDepth = (int) (2 * (Math.log(n) / Math.log(2))) + 10;
        assertTrue(metrics.getMaxDepth() <= expectedMaxDepth,
                "Depth should be bounded, got: " + metrics.getMaxDepth());
    }
}