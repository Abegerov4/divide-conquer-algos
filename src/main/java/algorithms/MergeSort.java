package algorithms;

import metrics.Metrics;
import util.ArrayUtils;

/**
 * MergeSort with linear merge, buffer reuse, and insertion sort cutoff
 */
public class MergeSort {
    private final Metrics metrics;
    private static final int INSERTION_SORT_CUTOFF = 15;

    public MergeSort(Metrics metrics) {
        this.metrics = metrics;
    }

    public void sort(int[] array) {
        if (array == null || array.length <= 1) return;

        int[] buffer = new int[array.length];
        metrics.recordAllocation(array.length);
        sort(array, 0, array.length - 1, buffer);
    }

    private void sort(int[] array, int left, int right, int[] buffer) {
        metrics.recordDepth();

        // Use insertion sort for small arrays
        if (right - left <= INSERTION_SORT_CUTOFF) {
            insertionSort(array, left, right);
            return;
        }

        int mid = left + (right - left) / 2;
        sort(array, left, mid, buffer);
        sort(array, mid + 1, right, buffer);
        merge(array, left, mid, right, buffer);
    }

    private void merge(int[] array, int left, int mid, int right, int[] buffer) {
        // Copy to buffer
        System.arraycopy(array, left, buffer, left, right - left + 1);

        int i = left, j = mid + 1, k = left;
        while (i <= mid && j <= right) {
            metrics.recordComparison();
            if (buffer[i] <= buffer[j]) {
                array[k++] = buffer[i++];
            } else {
                array[k++] = buffer[j++];
            }
        }

        // Copy remaining elements from left side
        while (i <= mid) {
            array[k++] = buffer[i++];
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