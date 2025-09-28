package algorithms;

import metrics.Metrics;
import util.Point;
import java.util.*;

/**
 * Simplified Closest Pair of Points algorithm
 */
public class ClosestPair {
    private final Metrics metrics;

    public ClosestPair(Metrics metrics) {
        this.metrics = metrics;
    }

    /**
     * Finds the closest pair of points using divide-and-conquer
     */
    public Point[] findClosestPair(Point[] points) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("At least 2 points required");
        }

        metrics.recordDepth();

        // For small arrays, use brute force
        if (points.length <= 3) {
            return bruteForce(points, 0, points.length - 1);
        }

        // Sort by x coordinate
        Point[] sortedX = points.clone();
        Arrays.sort(sortedX);

        // Split into left and right halves
        int mid = sortedX.length / 2;
        Point[] left = Arrays.copyOfRange(sortedX, 0, mid);
        Point[] right = Arrays.copyOfRange(sortedX, mid, sortedX.length);

        metrics.recordAllocation(left.length + right.length);

        // Recursively find closest in halves
        Point[] leftClosest = findClosestPair(left);
        Point[] rightClosest = findClosestPair(right);

        // Find minimum distance
        double leftDist = leftClosest[0].distanceTo(leftClosest[1]);
        double rightDist = rightClosest[0].distanceTo(rightClosest[1]);
        double minDist = Math.min(leftDist, rightDist);
        Point[] closest = leftDist < rightDist ? leftClosest : rightClosest;

        // Check points near the dividing line
        double midX = sortedX[mid].x;
        List<Point> strip = new ArrayList<>();

        for (Point p : sortedX) {
            if (Math.abs(p.x - midX) < minDist) {
                strip.add(p);
            }
        }

        // Sort strip by y-coordinate
        strip.sort(Point::compareByY);

        // Check pairs in strip (only need to check 7 neighbors)
        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size() && j < i + 8; j++) {
                metrics.recordComparison();
                Point p1 = strip.get(i);
                Point p2 = strip.get(j);
                double dist = p1.distanceTo(p2);
                if (dist < minDist) {
                    minDist = dist;
                    closest = new Point[]{p1, p2};
                }
            }
        }

        return closest;
    }

    private Point[] bruteForce(Point[] points, int left, int right) {
        Point closest1 = points[left];
        Point closest2 = points[left + 1];
        double minDist = closest1.distanceTo(closest2);

        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                metrics.recordComparison();
                double dist = points[i].distanceTo(points[j]);
                if (dist < minDist) {
                    minDist = dist;
                    closest1 = points[i];
                    closest2 = points[j];
                }
            }
        }
        return new Point[]{closest1, closest2};
    }
}