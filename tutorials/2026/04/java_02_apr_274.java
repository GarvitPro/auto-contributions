public class BinarySearch {

    /**
     * This method performs binary search on an array of integers.
     *
     * @param arr   the array to be searched
     * @param target the number to be found in the array
     * @return the index of the target number if found, -1 otherwise
     */
    public static int binarySearch(int[] arr, int target) {
        // Initialize two pointers, one at the start and one at the end of the array
        int left = 0;
        int right = arr.length - 1;

        // Continue searching while the two pointers haven't crossed each other
        while (left <= right) {
            // Calculate the middle index
            int mid = left + (right - left) / 2;

            // If the target is found at the middle index, return it
            if (arr[mid] == target) {
                return mid;
            }

            // If the target is less than the middle element, move the right pointer
            else if (arr[mid] > target) {
                right = mid - 1; // exclusive upper bound
            }

            // If the target is greater than the middle element, move the left pointer
            else {
                left = mid + 1;
            }
        }

        // If the loop ends without finding the target, return -1
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {2, 5, 8, 12, 16, 23, 38, 56, 72, 91};
        int target = 23;

        // Call the binarySearch method
        int result = binarySearch(arr, target);

        if (result != -1) {
            System.out.println("Element found at index " + result);
        } else {
            System.out.println("Element not found in array");
        }
    }
}