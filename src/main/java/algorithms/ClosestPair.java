package algorithms;

import metrics.Metrics;
import util.Point;
import java.util.*;

/**
 * Closest Pair of Points algorithm (O(n log n))
 * Uses divide-and-conquer with strip optimization
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
        metrics.recordAllocation(points.length * 2); // For sorted arrays

        // 1. Sort points by x-coordinate
        Point[] pointsByX = points.clone();
        Arrays.sort(pointsByX);

        // 2. Also need points sorted by y-coordinate
        Point[] pointsByY = points.clone();
        Arrays.sort(pointsByY, Point::compareByY);

        return findClosestPair(pointsByX, pointsByY, 0, points.length - 1);
    }

    private Point[] findClosestPair(Point[] pointsByX, Point[] pointsByY, int left, int right) {
        metrics.recordDepth();

        // Base case: small number of points - use brute force
        if (right - left + 1 <= 3) {
            return bruteForceClosestPair(pointsByX, left, right);
        }

        int mid = left + (right - left) / 2;
        Point midPoint = pointsByX[mid];

        // 3. Divide: split points by y-coordinate around vertical line
        List<Point> leftPointsByY = new ArrayList<>();
        List<Point> rightPointsByY = new ArrayList<>();

        for (Point point : pointsByY) {
            metrics.recordComparison();
            if (point.x < midPoint.x || (point.x == midPoint.x && point.y < midPoint.y)) {
                leftPointsByY.add(point);
            } else {
                rightPointsByY.add(point);
            }
        }

        // Convert lists back to arrays for recursion
        Point[] leftArray = leftPointsByY.toArray(new Point[0]);
        Point[] rightArray = rightPointsByY.toArray(new Point[0]);
        metrics.recordAllocation(leftArray.length + rightArray.length);

        // 4. Conquer: find closest pairs in left and right halves
        Point[] leftClosest = findClosestPair(pointsByX, leftArray, left, mid);
        Point[] rightClosest = findClosestPair(pointsByX, rightArray, mid + 1, right);

        // 5. Find minimum distance from both halves
        double leftDistance = distance(leftClosest[0], leftClosest[1]);
        double rightDistance = distance(rightClosest[0], rightClosest[1]);
        double minDistance = Math.min(leftDistance, rightDistance);
        Point[] closest = leftDistance <= rightDistance ? leftClosest : rightClosest;

        // 6. Check points in the strip (within minDistance of vertical line)
        List<Point> strip = new ArrayList<>();
        for (Point point : pointsByY) {
            if (Math.abs(point.x - midPoint.x) < minDistance) {
                strip.add(point);
            }
        }

        // 7. Check pairs in the strip (only need to check 7 neighbors)
        Point[] stripClosest = findClosestInStrip(strip, minDistance);
        if (stripClosest != null) {
            double stripDistance = distance(stripClosest[0], stripClosest[1]);
            if (stripDistance < minDistance) {
                return stripClosest;
            }
        }

        return closest;
    }

    /**
     * Brute force closest pair for small arrays (≤ 3 points)
     */
    private Point[] bruteForceClosestPair(Point[] points, int left, int right) {
        Point point1 = points[left];
        Point point2 = points[left + 1];
        double minDistance = distance(point1, point2);

        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                metrics.recordComparison();
                double dist = distance(points[i], points[j]);
                if (dist < minDistance) {
                    minDistance = dist;
                    point1 = points[i];
                    point2 = points[j];
                }
            }
        }
        return new Point[]{point1, point2};
    }

    /**
     * Find closest pair in the strip (check only 7 neighbors for each point)
     */
    private Point[] findClosestInStrip(List<Point> strip, double minDistance) {
        if (strip.size() < 2) return null;

        Point point1 = strip.get(0);
        Point point2 = strip.get(1);
        double minStripDistance = distance(point1, point2);

        for (int i = 0; i < strip.size(); i++) {
            // Check only the next 7 points (geometric property)
            for (int j = i + 1; j < strip.size() && j < i + 8; j++) {
                metrics.recordComparison();
                Point p1 = strip.get(i);
                Point p2 = strip.get(j);

                // If y-difference is already too big, skip further checks
                if (p2.y - p1.y >= minStripDistance) {
                    break;
                }

                double dist = distance(p1, p2);
                if (dist < minStripDistance) {
                    minStripDistance = dist;
                    point1 = p1;
                    point2 = p2;
                }
            }
        }

        return minStripDistance < minDistance ? new Point[]{point1, point2} : null;
    }

    private double distance(Point p1, Point p2) {
        return p1.distanceTo(p2);
    }
}