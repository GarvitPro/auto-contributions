import java.util.Arrays;

public class TwoPointerApproach {
    /**
     * This program teaches the two-pointer approach, a technique used in sorting and searching algorithms.
     * It is useful because it allows us to solve problems efficiently by iterating through arrays or lists
     * with both ends, often reducing time complexity from O(n) to O(n/2).
     *
     * The two-pointer approach is widely used in problems involving sorted arrays,
     * such as finding the first and last occurrence of an element in a sorted array.
     */
    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9};
        System.out.println("Original Array: " + Arrays.toString(arr));
        
        // Example usage: finding the first and last occurrence of an element in a sorted array
        findFirstAndLastOccurrence(arr, 5);
    }

    /**
     * Finds the first and last occurrence of an element in a sorted array using the two-pointer approach.
     *
     * @param arr      The input array.
     * @param target   The target element to search for.
     */
    public static void findFirstAndLastOccurrence(int[] arr, int target) {
        // Initialize pointers at both ends of the array
        int left = 0; // Start from the beginning of the array
        int right = arr.length - 1; // Start from the end of the array
        
        // Find the first occurrence of the target element
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                // If the middle element is equal to the target, move the left pointer to mid + 1
                // This will help us find all occurrences of the target element
                if (mid == 0 || arr[mid - 1] != target) {
                    System.out.println("First occurrence: " + mid);
                }
                left = mid + 1;
            } else if (arr[mid] < target) {
                // If the middle element is less than the target, move the left pointer to mid + 1
                left = mid + 1;
            } else {
                // If the middle element is greater than the target, move the right pointer to mid - 1
                right = mid - 1;
            }
        }

        // Reset pointers and find the last occurrence of the target element
        left = 0; 
        right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                // If the middle element is equal to the target, move the right pointer to mid - 1
                // This will help us find all occurrences of the target element
                if (mid == arr.length - 1 || arr[mid + 1] != target) {
                    System.out.println("Last occurrence: " + mid);
                }
                right = mid - 1;
            } else if (arr[mid] < target) {
                // If the middle element is less than the target, move the left pointer to mid + 1
                left = mid + 1;
            } else {
                // If the middle element is