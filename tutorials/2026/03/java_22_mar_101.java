/**
 * Divide and Conquer Algorithm
 * 
 * This Java program implements the divide and conquer algorithm to find the maximum sum of a subarray in an array.
 */

public class Main {

    public static void main(String[] args) {
        int[] arr = {3, -1, 4, 2, -3};
        System.out.println("Maximum Sum Subarray: " + maxSubArray(arr));
    }

    /**
     * Find the maximum sum of a subarray in an array using divide and conquer.
     * 
     * @param arr The input array
     * @return The maximum sum of a subarray
     */
    public static int maxSubArray(int[] arr) {
        return maxSubArrayHelper(arr, 0, arr.length - 1);
    }

    /**
     * Helper function to find the maximum sum of a subarray using divide and conquer.
     * 
     * @param arr The input array
     * @param low The lower bound of the current subarray
     * @param high The upper bound of the current subarray
     * @return The maximum sum of a subarray
     */
    public static int maxSubArrayHelper(int[] arr, int low, int high) {
        // Base case: if the subarray has only one element, return that element
        if (low == high)
            return arr[low];

        // Find the middle index of the current subarray
        int mid = low + (high - low) / 2;

        // Recursively find the maximum sum of two halves
        int leftMax = maxSubArrayHelper(arr, low, mid);
        int rightMax = maxSubArrayHelper(arr, mid + 1, high);

        // Find the maximum sum that includes the middle element
        int includeMiddle = arr[mid];

        // Initialize variables to keep track of the maximum sum that includes the middle element
        double leftSum = -Double.MAX_VALUE;
        double rightSum = -Double.MAX_VALUE;

        // Calculate the sum of elements on the left side of the middle index
        for (int i = mid; i >= low; i--) {
            leftSum += arr[i];
            if (leftSum > 0)
                break;
        }

        // Calculate the sum of elements on the right side of the middle index
        for (int i = mid + 1; i <= high; i++) {
            rightSum += arr[i];
            if (rightSum > 0)
                break;
        }

        // Return the maximum sum
        return Math.max(leftMax, Math.max(rightMax, includeMiddle + leftSum + rightSum));
    }
}