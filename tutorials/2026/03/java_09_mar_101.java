// Sliding Window Problem
// Problem Statement: Given an array of integers nums and an integer k, 
// find all the contiguous subarrays of size k in the given array.

public class SlidingWindow {
    public static void main(String[] args) {
        int[] nums = {1, 2, 1, 2, 3, 4, 5, 2};
        int k = 3;
        System.out.println(findSubarrays(nums, k));  // Output: [1, 2, 3]
    }

    public static int[] findSubarrays(int[] nums, int k) {
        // Initialize an empty array to store the subarrays
        int[] subarrays = new int[k];
        int count = 0;
        int j = 0;
        // Iterate over the array using two pointers
        for (int i = 0; i < nums.length; i++) {
            // Expand the window to the right
            while (j < i && i - j + 1 < k) {
                j++;
            }
            // If the window size is equal to k, add the subarray to the result
            if (i - j + 1 == k) {
                subarrays[count++] = nums[j];
            }
            // If the window size is greater than k, shrink the window from the left
            if (i - j + 1 > k) {
                subarrays[count++] = nums[i - k + 1];
                j++;
            }
        }
        // Trim the result array
        int[] result = new int[count];
        System.arraycopy(subarrays, 0, result, 0, count);
        return result;
    }
}