import java.util.Arrays;

public class BinarySearch {
    public static void main(String[] args) {
        // Initialize an array with elements
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        
        // Print the original array
        System.out.println("Original Array: " + Arrays.toString(arr));
        
        // Find the index of a specific element using binary search
        int[] result = binarySearch(arr, 0, arr.length - 1, 6);
        
        if (result[0] != -1) {
            System.out.println("Element found at index: " + result[0]);
        } else {
            System.out.println("Element not found in array.");
        }
    }

    /**
     * This function performs binary search on a sorted array.
     *
     * @param arr   the input array
     * @param left  the starting index of the array
     * @param right the ending index of the array
     * @param target the element to be searched
     * @return an array containing the result (index if found, -1 otherwise)
     */
    public static int[] binarySearch(int[] arr, int left, int right, int target) {
        // If the array is empty or has only one element, return -1 as the target cannot be found
        if (left > right) {
            return new int[]{-1};
        }
        
        // Calculate the middle index of the current range
        int mid = left + (right - left) / 2;
        
        // Compare the middle element with the target
        if (arr[mid] == target) {
            // If they are equal, check for duplicates and return all indices
            boolean duplicateFound = false;
            for (int i = left; i <= right; i++) {
                if (arr[i] == target && i != mid) {
                    duplicateFound = true;
                    break;
                }
            }
            if (!duplicateFound) {
                return new int[]{mid};
            } else {
                return new int[]{left, mid, right};
            }
        } else if (arr[mid] < target) {
            // If the middle element is less than the target, search in the right half
            return binarySearch(arr, mid + 1, right, target);
        } else {
            // If the middle element is greater than the target, search in the left half
            return binarySearch(arr, left, mid - 1, target);
        }
    }
}