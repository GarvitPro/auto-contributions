// Greedy Interval Scheduling in Java

public class GreedyIntervalScheduling {

    // Method to find the maximum number of non-overlapping intervals that can be scheduled
    public static int greedyIntervalScheduling(int[][] intervals) {
        // Sort the intervals by their end points
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);

        // Initialize the count of non-overlapping intervals to 0
        int count = 0;

        // Initialize the end point of the last scheduled interval to negative infinity
        int lastEnd = Integer.MIN_VALUE;

        // Iterate over each interval in the sorted list
        for (int[] interval : intervals) {
            // If the current interval does not overlap with the last scheduled interval, schedule it
            if (interval[0] >= lastEnd) {
                count++;
                // Update the end point of the last scheduled interval to be the end point of the current interval
                lastEnd = interval[1];
            }
        }

        return count;
    }

    public static void main(String[] args) {
        int[][] intervals = {{0, 30}, {5, 10}, {15, 20}};
        System.out.println("Maximum number of non-overlapping intervals that can be scheduled: " + greedyIntervalScheduling(intervals));
    }
}