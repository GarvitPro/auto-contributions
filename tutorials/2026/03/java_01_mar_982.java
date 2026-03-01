import java.util.*;

public class GreedyIntervalScheduling {

    public static void main(String[] args) {
        int[] start = {1, 2, 3, 4, 5};
        int[] end = {2, 4, 6, 8, 9};
        int[][] intervals = new int[start.length][2];
        for (int i = 0; i < start.length; i++) {
            intervals[i][0] = start[i];
            intervals[i][1] = end[i];
        }

        int[] result = greedyIntervalScheduling(intervals);
        System.out.println("Selected Intervals: ");
        for (int i = 0; i < result.length; i++) {
            System.out.println("(" + result[i][0] + ", " + result[i][1] + ")");
        }
    }

    public static int[] greedyIntervalScheduling(int[][] intervals) {
        // Sort the intervals based on their end time
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);

        // Initialize the result array with the first interval
        int[] result = new int[intervals.length];
        result[0] = intervals[0][0];
        result[1] = intervals[0][1];

        // Iterate over the sorted intervals
        for (int i = 1; i < intervals.length; i++) {
            // Check if the current interval starts after the last selected interval ends
            if (intervals[i][0] >= result[result.length - 1]) {
                // If yes, add it to the result array
                result = addInterval(result, intervals[i]);
            }
        }

        return result;
    }

    public static int[] addInterval(int[] result, int[] interval) {
        // Increase the end time of the last selected interval by 1
        result[result.length - 1] = interval[1] + 1;
        return result;
    }
}