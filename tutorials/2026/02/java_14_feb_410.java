// This Java program teaches the Two Pointer Approach.
// It is a useful technique used in array and string problems to solve certain types of problems efficiently.

public class TwoPointerApproach {
    // Function to find maximum sum of subarray that can be obtained by adding elements from two arrays.
    public static int maxSum(int[] arr1, int[] arr2) {
        // Initialize variables to store the result and the index for both arrays.
        int res = 0;
        int i = 0, j = 0;

        // Traverse through both arrays.
        while (i < arr1.length && j < arr2.length) {
            // Choose the maximum element from the current position in both arrays.
            if (arr1[i] > arr2[j]) {
                res += arr1[i];
                i++;
            } else if (arr2[j] > arr1[i]) {
                res += arr2[j];
                j++;
            } else { // If elements are equal, add one to the result and increment both indices.
                res += arr1[i] + 1;
                i++;
                j++;
            }
        }

        // Add remaining elements from array1
        while (i < arr1.length) {
            res += arr1[i];
            i++;
        }

        // Return the maximum sum found.
        return res;
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3};
        int[] arr2 = {4, 5, 6};

        System.out.println("Maximum Sum: " + maxSum(arr1, arr2));

        // Example usage:
        int[] testArr1 = {5, 10, -20, 7};
        int[] testArr2 = {-15, 3, 1, 8};
        System.out.println("Expected Output: 22 and Actual " + maxSum(testArr1, testArr2));
    }
}