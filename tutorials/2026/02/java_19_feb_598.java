public class MergeSort {
    // Function to merge two sorted subarrays into one sorted array
    public static int[] merge(int[] left, int[] right) {
        // Initialize the result array with the same length as both input arrays
        int[] result = new int[left.length + right.length];
        
        // Initialize indices for the left and right arrays
        int i = 0, j = 0;
        int k = 0; // Index in the result array
        
        // Compare elements from both arrays and add smaller one to the result array
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }
        
        // Add remaining elements from both arrays to the result array
        while (i < left.length) {
            result[k++] = left[i++];
        }
        while (j < right.length) {
            result[k++] = right[j++];
        }
        
        return result;
    }

    // Function to perform merge sort on an array of integers
    public static int[] mergeSort(int[] arr) {
        if (arr.length <= 1) {
            // Base case: If the array has one or zero elements, it's already sorted
            return arr;
        }
        
        // Find the middle index of the array
        int mid = arr.length / 2;
        
        // Divide the array into two halves and recursively sort them
        int[] left = new int[mid];
        int[] right = new int[arr.length - mid];
        System.arraycopy(arr, 0, left, 0, mid);
        System.arraycopy(arr, mid, right, 0, arr.length - mid);
        
        // Recursively sort both halves
        left = mergeSort(left);
        right = mergeSort(right);
        
        // Merge the sorted halves back together using the merge function
        return merge(left, right);
    }

    public static void main(String[] args) {
        int[] array = {5, 2, 8, 3, 1, 4, 6};
        int[] sortedArray = mergeSort(array);
        
        System.out.println("Sorted array:");
        for (int num : sortedArray) {
            System.out.print(num + " ");
        }
    }
}