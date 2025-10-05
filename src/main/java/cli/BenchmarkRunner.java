package cli;

import algorithm.ShellSort;
import metrics.PerformanceTracker;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;

public class BenchmarkRunner {
    private static final String CSV = "target/metrics.csv";

    public static void main(String[] args) throws Exception {
        int[] sizes = {10, 100, 1000, 10000, 100000};
        int trials = 5;

        ensureHeader();

        Random rnd = new Random(42);
        for (int n : sizes) {
            for (int t = 1; t <= trials; t++) {
                int[] a = rnd.ints(n, -1_000_000, 1_000_000).toArray();

                PerformanceTracker tracker = new PerformanceTracker();
                long start = System.nanoTime();
                ShellSort.sort(a, tracker);
                long timeNs = System.nanoTime() - start;

                if (!isSorted(a)) throw new IllegalStateException("Not sorted for n=" + n);

                writeRow("ShellSort", "Knuth", n, timeNs, tracker.getComparisons(),
                        tracker.getSwapsOrAllocations(), tracker.getDeepestRecursion(), t);

                System.out.printf("n=%-7d trial=%d  time=%6.2f ms  cmp=%d  ops=%d%n",
                        n, t, timeNs / 1_000_000.0, tracker.getComparisons(), tracker.getSwapsOrAllocations());
            }
        }
    }

    private static void ensureHeader() throws Exception {
        File f = new File(CSV);
        if (!f.getParentFile().exists()) f.getParentFile().mkdirs();
        if (!f.exists()) {
            try (PrintWriter out = new PrintWriter(new FileWriter(f, true))) {
                out.println("algorithm,gap_seq,n,time_ns,comparisons,allocations,maxDepth,trial");
            }
        }
    }

    private static void writeRow(String algo, String gap, int n, long timeNs,
                                 long cmp, long ops, long depth, int trial) throws Exception {
        try (PrintWriter out = new PrintWriter(new FileWriter(CSV, true))) {
            out.printf("%s,%s,%d,%d,%d,%d,%d,%d%n", algo, gap, n, timeNs, cmp, ops, depth, trial);
        }
    }

    private static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i - 1] > a[i]) return false;
        return true;
    }
}