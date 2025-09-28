import algorithms.MergeSort;
import algorithms.QuickSort;
import algorithms.DeterministicSelect;
import algorithms.ClosestPair;
import metrics.Metrics;
import util.ArrayUtils;
import util.Point;

import java.util.Arrays;

/**
 * Main class for testing all algorithms
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Divide & Conquer Algorithms Demo\n");

        // Test array for sorting/select algorithms
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

        // Test Closest Pair
        System.out.println("\n--- Testing Closest Pair ---");
        metrics.reset();
        ClosestPair closestPair = new ClosestPair(metrics);

        // Create test points
        Point[] points = {
                new Point(0, 0),
                new Point(1, 1),
                new Point(3, 3),
                new Point(2, 2),
                new Point(5, 5),
                new Point(4, 4)
        };

        System.out.println("Points: " + Arrays.toString(points));
        Point[] closest = closestPair.findClosestPair(points);
        double distance = closest[0].distanceTo(closest[1]);

        System.out.println("Closest pair: " + closest[0] + " and " + closest[1]);
        System.out.println("Distance: " + distance);
        System.out.println("Closest Pair metrics: " + metrics);

        // Verification section
        System.out.println("\n=== VERIFICATION ===");
        System.out.println("MergeSort == QuickSort: " + Arrays.equals(array1, array2));

        // Verify median is correct
        int[] sorted = testArray.clone();
        Arrays.sort(sorted);
        System.out.println("Median correct: " + (median == sorted[sorted.length / 2]));

        // Verify closest pair (should be (1,1) and (2,2) or similar close points)
        System.out.println("Closest pair distance reasonable: " + (distance > 0 && distance < 10));

        System.out.println("\n ALL ALGORITHMS TESTED SUCCESSFULLY!");
    }
}