import algorithm.ShellSort;
import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ShellSortTest {
    private static final String CSV = "target/metrics.csv";

    private void runCase(String label, int[] arr) throws Exception {
        int[] expected = arr.clone();
        Arrays.sort(expected);

        PerformanceTracker tracker = new PerformanceTracker();
        long start = System.nanoTime();
        ShellSort.sort(arr, tracker);
        long timeNs = System.nanoTime() - start;

        assertArrayEquals(expected, arr);

        File file = new File(CSV);
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        boolean newFile = !file.exists();

        try (PrintWriter out = new PrintWriter(new FileWriter(file, true))) {
            if (newFile) out.println("algorithm,time_ns,comparisons,allocations,maxDepth");
            out.printf("%s,%d,%d,%d,%d%n",
                    label,
                    timeNs,
                    tracker.getComparisons(),
                    tracker.getSwapsOrAllocations(),
                    tracker.getDeepestRecursion());
        }
    }

    @Test
    void testSmall() throws Exception {
        runCase("ShellSort_small", new int[]{5, 2, 9, 1, 5, 6});
    }

    @Test
    void testSorted() throws Exception {
        runCase("ShellSort_sorted", new int[]{1, 2, 3, 4, 5});
    }

    @Test
    void testReversed() throws Exception {
        runCase("ShellSort_reversed", new int[]{5, 4, 3, 2, 1});
    }

    @Test
    void testEmpty() throws Exception {
        runCase("ShellSort_empty", new int[]{});
    }
}