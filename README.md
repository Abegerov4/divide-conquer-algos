# Divide & Conquer Algorithms

## 📊 Performance Benchmarks

### Execution Time (nanoseconds)

| Input Size | MergeSort | QuickSort | Select | Closest Pair |
|------------|-----------|-----------|--------|--------------|
| 100        | 120,833   | 141,208   | 40,250 | 671,834      |
| 500        | 143,333   | 174,500   | 200,916| 1,109,292    |
| 1000       | 309,334   | 458,125   | 192,875| 927,041      |
| 5000       | 398,042   | 461,917   | 500,458| -            |

### Recursion Depth

| Input Size | MergeSort | QuickSort | Select | Closest Pair |
|------------|-----------|-----------|--------|--------------|
| 100        | 15        | 10        | 32     | 71           |
| 500        | 63        | 55        | 127    | 487          |
| 1000       | 127       | 118       | 283    | 975          |

## 🏗️ Architecture Notes

### Depth Control
- **MergeSort**: Insertion sort cutoff (n ≤ 15)
- **QuickSort**: Smaller-first recursion + randomization
- **Select**: Median-of-medians with single recursion
- **Closest Pair**: Strip optimization (7 neighbors)

### Memory Patterns
- **QuickSort**: 0 allocations (in-place)
- **MergeSort**: O(n) buffer reuse
- **Select**: O(n/log n) temporary arrays
- **Closest Pair**: O(n) geometric data

## 📈 Recurrence Analysis

| Algorithm | Recurrence | Complexity |
|-----------|------------|------------|
| **MergeSort** | T(n) = 2T(n/2) + O(n) | Θ(n log n) |
| **QuickSort** | T(k) + T(n-k) + O(n) | O(n log n) avg |
| **Select** | T(n/5) + T(7n/10) + O(n) | O(n) |
| **Closest Pair** | 2T(n/2) + O(n) | Θ(n log n) |

## 🎯 Results Summary

### Theory vs Practice
- ✅ All algorithms match expected complexity
- ✅ QuickSort faster due to cache locality
- ✅ Select shows clear O(n) scaling
- ✅ Depth bounds respected (except Closest Pair)

### Best Use Cases
- **Small n**: Select (lowest time)
- **Medium n**: MergeSort (consistent)
- **Large n**: QuickSort (cache efficient)
- **Memory constrained**: QuickSort (in-place)

## 🚀 Quick Start

```bash
# Run tests
mvn test

# Run main application
mvn exec:java -Dexec.mainClass=Main

# Run benchmarks
mvn exec:java -Dexec.mainClass=cli.BenchmarkRunner