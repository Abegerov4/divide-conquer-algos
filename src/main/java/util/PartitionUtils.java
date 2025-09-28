package util;

import metrics.Metrics;
import java.util.Random;

/**
 * Utility methods for partitioning arrays (used in QuickSort and Select)
 */
public class PartitionUtils {

    /**
     * Partitions array around a random pivot
     * @return final position of the pivot
     */
    public static int partition(int[] array, int left, int right, Metrics metrics) {
        // Randomized pivot selection
        int pivotIndex = left + new Random().nextInt(right - left + 1);
        ArrayUtils.swap(array, pivotIndex, right);

        int pivot = array[right];
        int i = left - 1;

        for (int j = left; j < right; j++) {
            metrics.recordComparison();
            if (array[j] <= pivot) {
                i++;
                ArrayUtils.swap(array, i, j);
            }
        }

        ArrayUtils.swap(array, i + 1, right);
        return i + 1;
    }

    /**
     * Partitions array around given pivot value
     */
    public static int partitionAroundPivot(int[] array, int left, int right, int pivot, Metrics metrics) {
        // Find pivot index and move to end
        for (int i = left; i <= right; i++) {
            if (array[i] == pivot) {
                ArrayUtils.swap(array, i, right);
                break;
            }
        }

        return partition(array, left, right, metrics);
    }
}