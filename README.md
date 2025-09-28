# Divide & Conquer Algorithms

## Algorithms Implemented

### 1. MergeSort
- **Strategy**: Divide-and-conquer with linear merge
- **Optimizations**: Buffer reuse, insertion sort cutoff (n ≤ 15)
- **Recurrence**: T(n) = 2T(n/2) + O(n)
- **Master Theorem**: Case 2 - Θ(n log n)
- **Depth**: O(log n)

### 2. QuickSort
- **Strategy**: Randomized divide-and-conquer
- **Optimizations**: Smaller-first recursion, insertion sort cutoff (n ≤ 16)
- **Complexity**: O(n log n) average, O(n²) worst-case
- **Depth**: O(log n) with smaller-first recursion

### 3. Deterministic Select
- **Strategy**: Median-of-medians with groups of 5
- **Complexity**: O(n) worst-case
- **Depth**: O(log n)

### 4. Closest Pair
- **Strategy**: Divide plane, conquer halves, check strip
- **Complexity**: O(n log n)
- **Optimization**: 7-neighbor check in strip

## Benchmark Results

Run benchmarks to generate CSV files:
- `sorting_benchmark.csv` - MergeSort vs QuickSort
- `select_benchmark.csv` - Deterministic Select performance
- `closest_pair_benchmark.csv` - Closest Pair scaling

## Running

```bash
# Compile
mvn compile

# Run all benchmarks
mvn exec:java -Dexec.mainClass="cli.BenchmarkRunner"

# Run tests
mvn test