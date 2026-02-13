// Binary Search Algorithm in Java

public class BinarySearch {

    /**
     * Binary search algorithm to find the index of a target element in a sorted array.
     *
     * @param arr   the input sorted array
     * @param target the target element to be searched
     * @return the index of the target element if found, -1 otherwise
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        // Start from both ends and move towards the center until we find the target or exceed it
        while (left <= right) {
            // Calculate the middle index
            int mid = left + (right - left) / 2;

            // If the target is found at the middle index, return it
            if (arr[mid] == target) {
                return mid;
            }

            // If the target is less than the middle element, move towards the left half
            else if (arr[mid] > target) {
                right = mid - 1;
            }

            // If the target is greater than the middle element, move towards the right half
            else {
                left = mid + 1;
            }
        }

        // If the target is not found after exceeding both ends, return -1
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {2, 5, 8, 12, 16, 23, 38, 56, 72, 91};
        int target = 23;

        int result = binarySearch(arr, target);

        if (result != -1) {
            System.out.println("Target found at index " + result);
        } else {
            System.out.println("Target not found in the array");
        }
    }
}