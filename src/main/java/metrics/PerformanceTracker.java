package metrics;

import java.util.concurrent.atomic.LongAdder;

public class PerformanceTracker {
    private final LongAdder comparisons = new LongAdder();
    private final LongAdder ops = new LongAdder();
    private final LongAdder maxDepth = new LongAdder(); // для совместимости (ShellSort даёт 0)

    public void countComparison() { comparisons.increment(); }
    public void countSwapOrAllocation() { ops.increment(); }

    public long getComparisons() { return comparisons.sum(); }
    public long getSwapsOrAllocations() { return ops.sum(); }
    public long getDeepestRecursion() { return maxDepth.sum(); }

    public void reset() {
        comparisons.reset();
        ops.reset();
        maxDepth.reset();
    }
}