/**
 * Sliding Window Technique in Java
 */

public class SlidingWindow {
    public static void main(String[] args) {
        int[] nums = {1,2,3,1};
        int k = 3;
        System.out.println("Maximum Sum Subarray of Size K: " + maxSumSubarray(nums, k));
    }

    /**
     * Returns the maximum sum of a subarray of size k
     * 
     * @param nums input array
     * @param k window size
     * @return maximum sum of a subarray of size k
     */
    public static int maxSumSubarray(int[] nums, int k) {
        // Handle edge cases
        if (nums.length < k) {
            return 0;
        }

        // Initialize variables
        int maxSum = Integer.MIN_VALUE;
        int windowStart = 0;

        // Calculate the sum of the first window
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }
        maxSum = Math.max(maxSum, windowSum);

        // Slide the window to the right
        for (int windowEnd = k; windowEnd < nums.length; windowEnd++) {
            // Remove the element going out of the window
            windowSum -= nums[windowStart];
            windowStart++;

            // Add the new element entering the window
            windowSum += nums[windowEnd];

            // Update the maximum sum
            maxSum = Math.max(maxSum, windowSum);
        }

        return maxSum;
    }
}