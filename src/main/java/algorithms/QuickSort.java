package algorithms;

import metrics.Metrics;
import util.PartitionUtils;

/**
 * Robust QuickSort with randomized pivot and smaller-first recursion
 */
public class QuickSort {
    private final Metrics metrics;
    private static final int INSERTION_SORT_CUTOFF = 16;

    public QuickSort(Metrics metrics) {
        this.metrics = metrics;
    }

    public void sort(int[] array) {
        if (array == null || array.length <= 1) return;

        metrics.recordDepth();
        sort(array, 0, array.length - 1);
    }

    private void sort(int[] array, int left, int right) {
        metrics.recordDepth();

        // Use iterative approach for large partitions, recursive for small
        while (left < right) {
            // Use insertion sort for small arrays
            if (right - left < INSERTION_SORT_CUTOFF) {
                insertionSort(array, left, right);
                break;
            }

            int pivotIndex = PartitionUtils.partition(array, left, right, metrics);

            // Recurse into smaller partition first to bound stack depth
            if (pivotIndex - left < right - pivotIndex) {
                sort(array, left, pivotIndex - 1);
                left = pivotIndex + 1;  // Iterate on larger partition
            } else {
                sort(array, pivotIndex + 1, right);
                right = pivotIndex - 1;  // Iterate on larger partition
            }
        }
    }

    private void insertionSort(int[] array, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= left) {
                metrics.recordComparison();
                if (array[j] > key) {
                    array[j + 1] = array[j];
                    j--;
                } else {
                    break;
                }
            }
            array[j + 1] = key;
        }
    }
}