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
        Point[] leftPointsByY = new Point[mid - left + 1];
        Point[] rightPointsByY = new Point[right - mid];
        metrics.recordAllocation(leftPointsByY.length + rightPointsByY.length);

        splitPointsByY(pointsByY, midPoint, leftPointsByY, rightPointsByY);

        // 4. Conquer: find closest pairs in left and right halves
        Point[] leftClosest = findClosestPair(pointsByX, leftPointsByY, left, mid);
        Point[] rightClosest = findClosestPair(pointsByX, rightPointsByY, mid + 1, right);

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
        Point[] closest = new Point[]{points[left], points[left + 1]};
        double minDistance = distance(points[left], points[left + 1]);

        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                metrics.recordComparison();
                double dist = distance(points[i], points[j]);
                if (dist < minDistance) {
                    minDistance = dist;
                    closest[0] = points[i];
                    closest[1] = points[j];
                }
            }
        }
        return closest;
    }

    /**
     * Split points by y-coordinate around vertical line through midPoint
     */
    private void splitPointsByY(Point[] pointsByY, Point midPoint,
                                Point[] leftPoints, Point[] rightPoints) {
        int leftIdx = 0, rightIdx = 0;

        for (Point point : pointsByY) {
            metrics.recordComparison();
            if (point.x < midPoint.x || (point.x == midPoint.x && point.y < midPoint.y)) {
                leftPoints[leftIdx++] = point;
            } else {
                rightPoints[rightIdx++] = point;
            }
        }
    }

    /**
     * Find closest pair in the strip (check only 7 neighbors for each point)
     */
    private Point[] findClosestInStrip(List<Point> strip, double minDistance) {
        if (strip.size() < 2) return null;

        Point[] closest = null;
        double minStripDistance = minDistance;

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
                    closest = new Point[]{p1, p2};
                }
            }
        }

        return closest;
    }

    private double distance(Point p1, Point p2) {
        return p1.distanceTo(p2);
    }
}