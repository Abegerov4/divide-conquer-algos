package cli;

import algorithms.*;
import metrics.Metrics;
import metrics.CSVWriter;
import util.Point;
import util.ArrayUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * Comprehensive benchmark runner for all algorithms
 */
public class BenchmarkRunner {
    private static final Random RANDOM = new Random(42);

    public static void main(String[] args) {
        try {
            runSortingBenchmarks();
            runSelectBenchmarks();
            runClosestPairBenchmarks();
            System.out.println("All benchmarks completed!");
        } catch (IOException e) {
            System.err.println("Error running benchmarks: " + e.getMessage());
        }
    }

    private static void runSortingBenchmarks() throws IOException {
        System.out.println("=== Sorting Algorithms Benchmark ===");

        try (CSVWriter csv = new CSVWriter("sorting_benchmark.csv")) {
            csv.writeHeader("Algorithm", "Size", "Time(ns)", "Comparisons", "Allocations", "MaxDepth");

            int[] sizes = {100, 500, 1000, 5000, 10000, 50000};

            for (int size : sizes) {
                System.out.println("Testing size: " + size);
                int[] array = generateRandomArray(size);

                // Benchmark MergeSort
                benchmarkSortingAlgorithm("MergeSort", array.clone(), csv);

                // Benchmark QuickSort
                benchmarkSortingAlgorithm("QuickSort", array.clone(), csv);
            }
        }
    }

    private static void runSelectBenchmarks() throws IOException {
        System.out.println("\n=== Select Algorithm Benchmark ===");

        try (CSVWriter csv = new CSVWriter("select_benchmark.csv")) {
            csv.writeHeader("Size", "K", "Time(ns)", "Comparisons", "Allocations", "MaxDepth");

            int[] sizes = {100, 500, 1000, 5000, 10000};

            for (int size : sizes) {
                System.out.println("Testing size: " + size);
                int[] array = generateRandomArray(size);
                int k = size / 2; // Median

                benchmarkSelectAlgorithm(array.clone(), k, csv);
            }
        }
    }

    private static void runClosestPairBenchmarks() throws IOException {
        System.out.println("\n=== Closest Pair Benchmark ===");

        try (CSVWriter csv = new CSVWriter("closest_pair_benchmark.csv")) {
            csv.writeHeader("Size", "Time(ns)", "Comparisons", "Allocations", "MaxDepth");

            int[] sizes = {100, 500, 1000, 2000, 5000};

            for (int size : sizes) {
                System.out.println("Testing size: " + size);
                Point[] points = generateRandomPoints(size, 1000);

                benchmarkClosestPair(points, csv);
            }
        }
    }

    private static void benchmarkSortingAlgorithm(String name, int[] array, CSVWriter csv) throws IOException {
        Metrics metrics = new Metrics();

        long startTime = System.nanoTime();

        switch (name) {
            case "MergeSort":
                new MergeSort(metrics).sort(array);
                break;
            case "QuickSort":
                new QuickSort(metrics).sort(array);
                break;
        }

        long endTime = System.nanoTime();

        // Verify sorting is correct
        if (!ArrayUtils.isSorted(array)) {
            System.err.println(name + " failed to sort array correctly!");
        }

        csv.writeRow(name, array.length, endTime - startTime,
                metrics.getComparisons(), metrics.getAllocations(), metrics.getMaxDepth());

        System.out.printf("%s - Size: %d, Time: %d ns, Depth: %d\n",
                name, array.length, endTime - startTime, metrics.getMaxDepth());
    }

    private static void benchmarkSelectAlgorithm(int[] array, int k, CSVWriter csv) throws IOException {
        Metrics metrics = new Metrics();
        DeterministicSelect selector = new DeterministicSelect(metrics);

        long startTime = System.nanoTime();
        int result = selector.select(array, k);
        long endTime = System.nanoTime();

        // Verify result against sorting
        int[] sorted = array.clone();
        Arrays.sort(sorted);
        if (result != sorted[k]) {
            System.err.println("Select returned wrong result! Expected " + sorted[k] + " got " + result);
        }

        csv.writeRow(array.length, k, endTime - startTime,
                metrics.getComparisons(), metrics.getAllocations(), metrics.getMaxDepth());

        System.out.printf("Select - Size: %d, Time: %d ns, Depth: %d\n",
                array.length, endTime - startTime, metrics.getMaxDepth());
    }

    private static void benchmarkClosestPair(Point[] points, CSVWriter csv) throws IOException {
        Metrics metrics = new Metrics();
        ClosestPair closestPair = new ClosestPair(metrics);

        long startTime = System.nanoTime();
        Point[] result = closestPair.findClosestPair(points);
        long endTime = System.nanoTime();

        double distance = result[0].distanceTo(result[1]);

        csv.writeRow(points.length, endTime - startTime,
                metrics.getComparisons(), metrics.getAllocations(), metrics.getMaxDepth());

        System.out.printf("ClosestPair - Size: %d, Time: %d ns, Distance: %.3f\n",
                points.length, endTime - startTime, distance);
    }

    private static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = RANDOM.nextInt(size * 10);
        }
        return array;
    }

    private static Point[] generateRandomPoints(int count, int maxCoord) {
        Point[] points = new Point[count];
        for (int i = 0; i < count; i++) {
            points[i] = new Point(
                    RANDOM.nextDouble() * maxCoord,
                    RANDOM.nextDouble() * maxCoord
            );
        }
        return points;
    }
}