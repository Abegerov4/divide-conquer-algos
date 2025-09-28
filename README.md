# Divide & Conquer Algorithms

## 📋 Assignment Completion

### ✅ Implemented Algorithms

#### 1. MergeSort
- **Strategy**: Classic divide-and-conquer with linear merge
- **Optimizations**: Buffer reuse, insertion sort cutoff (n ≤ 15)
- **Recurrence**: T(n) = 2T(n/2) + O(n)
- **Master Theorem**: Case 2 - Θ(n log n)
- **Depth**: O(log n) - confirmed by measurements

#### 2. QuickSort
- **Strategy**: Randomized pivot with smaller-first recursion
- **Optimizations**: Recurse into smaller partition first, insertion sort cutoff
- **Complexity**: O(n log n) average case
- **Depth**: O(log n) bounded by smaller-first strategy

#### 3. Deterministic Select
- **Strategy**: Median-of-medians with groups of 5
- **Complexity**: O(n) worst-case guaranteed
- **Optimization**: Prefer recursing into smaller partition

#### 4. Closest Pair of Points
- **Strategy**: Divide plane, recursive halves, strip optimization
- **Complexity**: O(n log n)
- **Optimization**: 7-neighbor check in strip

### 📊 Architecture & Metrics

**Metrics Tracking**:
- Comparisons between elements
- Memory allocations
- Recursion depth
- Execution time

**Safe Recursion Patterns**:
- MergeSort: Standard binary recursion
- QuickSort: Smaller-first to bound stack depth
- Select: Single recursion with size reduction
- Closest Pair: Divide with strip optimization

### 🧪 Testing Strategy

- **Correctness**: Compare with Arrays.sort() and brute force
- **Edge Cases**: Empty, single element, duplicates, sorted/reverse
- **Performance**: Time vs n, depth vs n measurements
- **Validation**: Recursion depth bounds verification

### 📈 Performance Analysis

**Theoretical vs Empirical**:
- MergeSort: Consistent O(n log n) as expected
- QuickSort: O(n log n) average case observed
- Select: Linear growth confirmed
- Closest Pair: O(n log n) scaling verified

**Constant Factors**:
- QuickSort generally faster due to cache efficiency
- MergeSort consistent but higher memory usage
- Select has higher constants but guaranteed bounds

### 🔧 Build & Run

```bash
# Compile
mvn compile

# Run all tests
mvn test

# Run benchmarks (используйте эту команду!)
mvn exec:java -Dexec.mainClass=cli.BenchmarkRunner

# Run demo of all algorithms
mvn exec:java -Dexec.mainClass=Main

# Generate CSV results
# - sorting_benchmark.csv
# - select_benchmark.csv  
# - closest_pair_benchmark.csv