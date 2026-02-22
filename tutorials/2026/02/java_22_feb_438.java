import java.util.Arrays;
import java.util.Comparator;

public class GreedyIntervalScheduling {
    public static void main(String[] args) {
        int[] intervals = {1, 3, 6, 9, 10, 15};
        Arrays.sort(intervals, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        int i = 0;
        int count = 0;
        int last = 0;
        while (i < intervals.length) {
            if (intervals[i] - last >= intervals[i] - intervals[i - 1]) {
                count++;
                last = intervals[i];
            }
            i++;
        }
        System.out.println("Number of non-overlapping intervals: " + count);
    }

    public static void greedyIntervalScheduling(int[] intervals) {
        // Sort the intervals based on their end points
        Arrays.sort(intervals, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o1Last) {
                return o1 - o1Last;
            }
        });

        int i = 1;
        int count = 0;
        int last = 0;
        while (i < intervals.length) {
            if (intervals[i] - last >= intervals[i] - intervals[i - 1]) {
                count++;
                last = intervals[i];
            }
            i++;
        }

        // Print the result
        System.out.println("Number of non-overlapping intervals: " + count);
    }

    // Greedy Interval Scheduling algorithm implementation
    public static int greedyIntervalSchedulingAlgorithm(int[] intervals) {
        // Sort the intervals based on their end points
        Arrays.sort(intervals, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o1Last) {
                return o1 - o1Last;
            }
        });

        int count = 1;
        int last = intervals[0];

        // Iterate over the intervals
        for (int i = 1; i < intervals.length; i++) {
            // If the current interval does not overlap with the last selected interval
            if (intervals[i] - last >= intervals[i] - intervals[i - 1]) {
                count++;
                last = intervals[i];
            }
        }

        return count;
    }

    public static int[] merge(int[] nums) {
        if (nums.length <= 1) {
            return nums;
        }
        Arrays.sort(nums);
        int start = 0;
        int end = 1;
        while (end < nums.length) {
            if (nums[end] - nums[start] > 1) {
                nums[start] = nums[start] + nums[end];
                nums[end] = nums[start] + 1;
            }
            end++;
        }
        return nums;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}