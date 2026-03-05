// Binary Search in Java
public class BinarySearch {
    // Function to perform binary search
    public static int binarySearch(int[] arr, int target) {
        // Initialize two pointers, one at the start and one at the end of the array
        int left = 0;
        int right = arr.length - 1;

        // Continue the search until the two pointers meet
        while (left <= right) {
            // Calculate the middle index
            int mid = left + (right - left) / 2;

            // If the middle element is equal to the target, return its index
            if (arr[mid] == target) {
                return mid;
            }
            // If the middle element is less than the target, move the left pointer to the right
            else if (arr[mid] < target) {
                left = mid + 1;
            }
            // If the middle element is greater than the target, move the right pointer to the left
            else {
                right = mid - 1;
            }
        }

        // If the target is not found, return -1
        return -1;
    }

    public static void main(String[] args) {
        // Create an array
        int[] arr = {2, 5, 8, 12, 16, 23, 38, 56, 72, 91};

        // Set the target
        int target = 23;

        // Perform binary search
        int result = binarySearch(arr, target);

        // Print the result
        if (result != -1) {
            System.out.println("Element " + target + " is found at index " + result);
        } else {
            System.out.println("Element " + target + " is not found in the array");
        }
    }
}