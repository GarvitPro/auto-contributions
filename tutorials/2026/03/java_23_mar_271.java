import java.util.Scanner;

public class TwoPointers {
    public static void main(String[] args) {
        // Create a sorted array of integers
        int[] arr = {1, 2, 3, 4, 5};
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the value to search for:");
        int target = scanner.nextInt();
        System.out.println("Array: " + java.util.Arrays.toString(arr));
        boolean found = findElement(arr, target);
        if (found) {
            System.out.println("Element found at index: " + findIndex(arr, target));
        } else {
            System.out.println("Element not found in array");
        }
    }

    /**
     * Find the first occurrence of a value in an array using two pointers.
     *
     * @param arr   the input array
     * @param target the value to search for
     * @return true if the element is found, false otherwise
     */
    public static boolean findElement(int[] arr, int target) {
        // Initialize two pointers, one at the start and one at the end of the array
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            // Calculate the middle index
            int mid = left + (right - left) / 2;

            // Check if the element is found at the middle index
            if (arr[mid] == target) {
                return true;
            } else if (arr[mid] < target) {
                // If the element is greater than the middle element, move the left pointer to mid + 1
                left = mid + 1;
            } else {
                // If the element is less than the middle element, move the right pointer to mid - 1
                right = mid - 1;
            }
        }

        return false; // Element not found in array
    }

    /**
     * Find the index of an element in an array using two pointers.
     *
     * @param arr   the input array
     * @param target the value to search for
     * @return the index of the element if found, -1 otherwise
     */
    public static int findIndex(int[] arr, int target) {
        // Initialize two pointers, one at the start and one at the end of the array
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            // Calculate the middle index
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                return mid; // Element found, return its index
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1; // Element not found in array
    }
}