package algorithm;

import metrics.PerformanceTracker;

public class ShellSort {

    public static void sort(int[] arr, PerformanceTracker tracker) {
        if (arr == null || arr.length <= 1) return;

        int n = arr.length;
        int[] gaps = generateSedgewickGaps(n);

        for (int g = gaps.length - 1; g >= 0; g--) {
            int gap = gaps[g];
            if (gap >= n) continue;

            boolean moved = false;
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                tracker.countSwapOrAllocation();
                int j = i;

                while (j >= gap && arr[j - gap] > temp) {
                    tracker.countComparison();
                    arr[j] = arr[j - gap];
                    tracker.countSwapOrAllocation();
                    j -= gap;
                    moved = true;
                }

                arr[j] = temp;
                tracker.countSwapOrAllocation();
            }

            if (gap == 1 && !moved) break;
        }
    }

    private static int[] generateSedgewickGaps(int n) {
        int[] seq = {
                1, 5, 19, 41, 109, 209, 505,
                929, 2161, 3905, 8929, 16001,
                36289, 64769, 146305, 260609, 587521
        };
        int count = 0;
        for (int gap : seq) if (gap < n) count++;
        int[] gaps = new int[count];
        System.arraycopy(seq, 0, gaps, 0, count);
        return gaps;
    }
}