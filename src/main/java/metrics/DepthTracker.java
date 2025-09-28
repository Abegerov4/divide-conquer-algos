package metrics;

/**
 * Auto-closable depth tracker for try-with-resources
 */
public class DepthTracker implements AutoCloseable {
    private final Metrics metrics;

    public DepthTracker(Metrics metrics) {
        this.metrics = metrics;
        metrics.recordDepth();
    }

    @Override
    public void close() {
        metrics.exitDepth();
    }
}