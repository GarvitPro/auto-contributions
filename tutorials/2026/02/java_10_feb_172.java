import java.util.Arrays;

public class SlidingWindow {
    // Function to find the maximum sum of a subarray of size 'k'
    public static int maxSubArraySum(int[] arr, int k) {
        // Initialize variables to store the maximum sum and the current window sum
        int max_sum = Integer.MIN_VALUE;
        int window_sum = 0;

        // Calculate the initial window sum by taking the first 'k' elements of the array
        for (int i = 0; i < k; i++) {
            window_sum += arr[i];
        }

        // Update the maximum sum if the current window sum is greater
        max_sum = Math.max(max_sum, window_sum);

        // Slide the window to the right by subtracting the leftmost element and adding the next element
        for (int i = k; i < arr.length; i++) {
            window_sum = window_sum - arr[i - k] + arr[i];
            max_sum = Math.max(max_sum, window_sum);
        }

        // Return the maximum sum found
        return max_sum;
    }

    public static void main(String[] args) {
        int[] arr = {1, 4, -3, 0, 1, 3, 10, 5};
        int k = 4;

        System.out.println("Maximum sum of subarray of size " + k + ": " + maxSubArraySum(arr, k));
    }
}