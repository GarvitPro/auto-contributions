/**
 * This class demonstrates the use of two pointers technique in Java.
 */

public class TwoPointers {

    /**
     * The bubble sort algorithm uses two pointers to sort an array in ascending order.
     * @param arr the input array
     */
    public static void bubbleSort(int[] arr) {
        int n = arr.length;

        // We use two pointers, i and j. 
        for (int i = 0; i < n - 1; i++) {
            // Pointer j is used to compare elements.
            for (int j = 0; j < n - i - 1; j++) {

                // If the element at index j is greater than the one at index j + 1, we swap them
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    /**
     * The merge sort algorithm uses two pointers to divide an array into smaller subarrays.
     * @param arr the input array
     */
    public static void mergeSort(int[] arr) {
        int n = arr.length;

        // If the array has only one element, it is already sorted.
        if (n <= 1)
            return;

        // Divide the array into two halves.
        int mid = n / 2;
        int leftHalf[] = new int[mid];
        int rightHalf[] = new int[n - mid];

        System.arraycopy(arr, 0, leftHalf, 0, mid);
        System.arraycopy(arr, mid, rightHalf, 0, n - mid);

        // Recursively call mergeSort on both halves.
        mergeSort(leftHalf);
        mergeSort(rightHalf);

        // Merge the two sorted halves back into a single array.
        merge(leftHalf, rightHalf, arr);
    }

    /**
     * The merge function is used to combine two sorted arrays into one.
     * @param left the first sorted array
     * @param right the second sorted array
     * @param result the final array that will be merged with the input arrays
     */
    public static void merge(int[] left, int[] right, int[] result) {
        int i = 0, j = 0, k = 0;

        // Merge smaller elements first.
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        // If there are any remaining elements in the left or right arrays, append them to the result.
        while (i < left.length) {
            result[k++] = left[i++];
        }
        while (j < right.length) {
            result[k++] = right[j++];
        }
    }

    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Original array: ");
        for (int i : arr)
            System.out.print(i + " ");
        System.out.println();

        bubbleSort(arr);
        System.out.println("Sorted array using Bubble Sort: ");
        for (int i