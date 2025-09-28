package algorithms;

import metrics.Metrics;
import util.ArrayUtils;
import util.PartitionUtils;

/**
 * Deterministic Select using Median-of-Medians (O(n) worst-case)
 */
public class DeterministicSelect {
    private final Metrics metrics;
    private static final int GROUP_SIZE = 5;

    public DeterministicSelect(Metrics metrics) {
        this.metrics = metrics;
    }

    /**
     * Finds k-th smallest element in array (0-indexed)
     */
    public int select(int[] array, int k) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        if (k < 0 || k >= array.length) {
            throw new IllegalArgumentException("k must be between 0 and " + (array.length - 1));
        }

        metrics.recordDepth();
        return select(array, 0, array.length - 1, k);
    }

    private int select(int[] array, int left, int right, int k) {
        metrics.recordDepth();

        // Base case: small array
        if (right - left + 1 <= GROUP_SIZE) {
            insertionSort(array, left, right);
            return array[left + k];
        }

        // 1. Divide into groups of 5 and find medians
        int numGroups = (right - left + 1 + GROUP_SIZE - 1) / GROUP_SIZE;
        int[] medians = new int[numGroups];
        metrics.recordAllocation(numGroups);

        for (int i = 0; i < numGroups; i++) {
            int groupStart = left + i * GROUP_SIZE;
            int groupEnd = Math.min(groupStart + GROUP_SIZE - 1, right);
            medians[i] = findMedianOfGroup(array, groupStart, groupEnd);
        }

        // 2. Find median of medians recursively
        int medianOfMedians = select(medians, 0, medians.length - 1, medians.length / 2);

        // 3. Partition around median of medians
        int pivotIndex = PartitionUtils.partitionAroundPivot(array, left, right, medianOfMedians, metrics);
        int pivotRank = pivotIndex - left;

        // 4. Recurse into the appropriate partition
        if (k == pivotRank) {
            return array[pivotIndex];
        } else if (k < pivotRank) {
            // Prefer recursing into smaller side (left)
            return select(array, left, pivotIndex - 1, k);
        } else {
            // Right side
            return select(array, pivotIndex + 1, right, k - pivotRank - 1);
        }
    }

    /**
     * Finds median of a small group using insertion sort
     */
    private int findMedianOfGroup(int[] array, int left, int right) {
        insertionSort(array, left, right);
        int mid = left + (right - left) / 2;
        return array[mid];
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