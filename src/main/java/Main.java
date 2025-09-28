import algorithms.MergeSort;
import algorithms.QuickSort;
import algorithms.DeterministicSelect;
import metrics.Metrics;
import util.ArrayUtils;

import java.util.Arrays;

/**
 * Main class for testing algorithms
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Divide & Conquer Algorithms Demo\n");

        // Test array
        int[] testArray = {5, 2, 8, 1, 9, 3, 7, 4, 6};
        System.out.println("Original array: " + Arrays.toString(testArray));

        // Test MergeSort
        Metrics metrics = new Metrics();
        MergeSort mergeSorter = new MergeSort(metrics);
        int[] array1 = testArray.clone();
        mergeSorter.sort(array1);
        System.out.println("\nMergeSort result: " + Arrays.toString(array1));
        System.out.println("MergeSort metrics: " + metrics);

        // Test QuickSort
        metrics.reset();
        QuickSort quickSorter = new QuickSort(metrics);
        int[] array2 = testArray.clone();
        quickSorter.sort(array2);
        System.out.println("\nQuickSort result: " + Arrays.toString(array2));
        System.out.println("QuickSort metrics: " + metrics);

        // Test Deterministic Select
        System.out.println("\n--- Testing Deterministic Select ---");
        metrics.reset();
        DeterministicSelect selector = new DeterministicSelect(metrics);
        int[] selectArray = testArray.clone();
        int median = selector.select(selectArray, selectArray.length / 2);
        System.out.println("Median: " + median);
        System.out.println("Select metrics: " + metrics);

        // Verify all produce same sorted result
        System.out.println("\nMergeSort == QuickSort: " + Arrays.equals(array1, array2));

        // Verify median is correct
        int[] sorted = testArray.clone();
        Arrays.sort(sorted);
        System.out.println("Median correct: " + (median == sorted[sorted.length / 2]));
    }
}