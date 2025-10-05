package algorithm;

import metrics.PerformanceTracker;

public class ShellSort {
    public static void sort(int[] arr, PerformanceTracker tracker) {
        if (arr == null || arr.length <= 1) return;

        int n = arr.length;
        int gap = 1;
        while (gap < n / 3) gap = 3 * gap + 1;

        while (gap >= 1) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i]; tracker.countSwapOrAllocation();
                int j = i;
                while (j >= gap) {
                    tracker.countComparison();
                    if (arr[j - gap] > temp) {
                        arr[j] = arr[j - gap]; tracker.countSwapOrAllocation();
                        j -= gap;
                    } else break;
                }
                arr[j] = temp; tracker.countSwapOrAllocation();
            }
            gap /= 3;
        }
    }
}