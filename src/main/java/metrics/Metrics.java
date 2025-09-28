package metrics;

/**
 * Tracks algorithm metrics: comparisons, allocations, recursion depth
 */
public class Metrics {
    private int comparisons;
    private int allocations;
    private int maxDepth;
    private int currentDepth;

    public Metrics() {
        reset();
    }

    public void recordComparison() {
        comparisons++;
    }

    public void recordAllocation(int size) {
        allocations += size;
    }

    public void recordDepth() {
        currentDepth++;
        maxDepth = Math.max(maxDepth, currentDepth);
    }

    public void exitDepth() {
        currentDepth--;
    }

    // Getters
    public int getComparisons() { return comparisons; }
    public int getAllocations() { return allocations; }
    public int getMaxDepth() { return maxDepth; }
    public int getCurrentDepth() { return currentDepth; }

    public void reset() {
        comparisons = 0;
        allocations = 0;
        maxDepth = 0;
        currentDepth = 0;
    }

    @Override
    public String toString() {
        return String.format("Metrics{comparisons=%d, allocations=%d, maxDepth=%d}",
                comparisons, allocations, maxDepth);
    }
}