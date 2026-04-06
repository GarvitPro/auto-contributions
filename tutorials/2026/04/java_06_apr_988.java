import java.util.Arrays;

public class GreedyIntervalScheduling {
    public static void main(String[] args) {
        int[][] intervals = {{0, 30}, {5, 10}, {15, 20}};
        System.out.println("Intervals: ");
        printIntervals(intervals);
        int[] result = greedyIntervalScheduling(intervals);
        System.out.println("\nGreedy Interval Scheduling:");
        printArray(result);
    }

    public static void printIntervals(int[][] intervals) {
        for (int i = 0; i < intervals.length; i++) {
            System.out.print("(" + intervals[i][0] + ", " + intervals[i][1] + ") ");
        }
        System.out.println();
    }

    public static int[] greedyIntervalScheduling(int[][] intervals) {
        // Sort the intervals based on their end times
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);

        int end = intervals[0][1];
        int[] result = {intervals[0][0], end};

        for (int i = 1; i < intervals.length; i++) {
            // If the current interval's start time is greater than the end time of the last scheduled interval, 
            // we schedule it
            if (intervals[i][0] >= end) {
                result[1] = intervals[i][1];
                result[0] = intervals[i][0];
            }
        }

        return result;
    }

    public static void printArray(int[] array) {
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}