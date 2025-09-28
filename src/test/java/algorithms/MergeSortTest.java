package algorithms;

import metrics.Metrics;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {

    @Test
    void testSortRandomArray() {
        Metrics metrics = new Metrics();
        MergeSort sorter = new MergeSort(metrics);
        int[] array = {5, 2, 8, 1, 9, 3};
        int[] expected = {1, 2, 3, 5, 8, 9};

        sorter.sort(array);

        assertArrayEquals(expected, array);
        assertTrue(metrics.getComparisons() > 0);
        assertTrue(metrics.getMaxDepth() > 0);
    }

    @Test
    void testSortAlreadySorted() {
        Metrics metrics = new Metrics();
        MergeSort sorter = new MergeSort(metrics);
        int[] array = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};

        sorter.sort(array);

        assertArrayEquals(expected, array);
    }

    @Test
    void testSortEmptyArray() {
        Metrics metrics = new Metrics();
        MergeSort sorter = new MergeSort(metrics);
        int[] array = {};

        sorter.sort(array); // Should not throw exception

        assertEquals(0, array.length);
    }

    @Test
    void testSortSingleElement() {
        Metrics metrics = new Metrics();
        MergeSort sorter = new MergeSort(metrics);
        int[] array = {42};
        int[] expected = {42};

        sorter.sort(array);

        assertArrayEquals(expected, array);
    }
}