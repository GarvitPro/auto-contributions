// Binary Search in Java
// This program demonstrates the binary search algorithm, which is used to find an element in a sorted array by repeatedly dividing the array in half.

public class BinarySearch {
    // Method to perform binary search
    public static int binarySearch(int[] array, int target) {
        // Initialize the low and high pointers
        int low = 0;
        int high = array.length - 1;

        // Continue searching while the low pointer is less than or equal to the high pointer
        while (low <= high) {
            // Calculate the mid index
            int mid = low + (high - low) / 2;

            // If the target element is found, return the mid index
            if (array[mid] == target) {
                return mid;
            }

            // If the target element is less than the middle element, move the high pointer
            if (array[mid] > target) {
                high = mid - 1;
            }

            // If the target element is greater than the middle element, move the low pointer
            else {
                low = mid + 1;
            }
        }

        // If the target element is not found, return -1
        return -1;
    }

    // Main method to test the binary search algorithm
    public static void main(String[] args) {
        // Create a sorted array
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        // Test the binary search algorithm
        int target = 5;
        int result = binarySearch(array, target);

        // Print the result
        if (result != -1) {
            System.out.println("Element found at index " + result);
        } else {
            System.out.println("Element not found in the array");
        }
    }
}