import java.util.Arrays;

public class RecursiveBacktracking {

    // Function to generate all permutations of an array using recursive backtracking
    public static void permute(int[] arr, int start, int end) {
        // Base case: if the start index is equal to the end index, print the permutation
        if (start == end) {
            System.out.println(Arrays.toString(arr));
        } else {
            // Iterate over each element from start index to end index
            for (int i = start; i <= end; i++) {
                // Swap the current element with the start index
                swap(arr, start, i);
                
                // Recursively call permute on the subarray starting from next index
                permute(arr, start + 1, end);
                
                // Backtrack: swap back the elements to restore the original array
                swap(arr, start, i);
            }
        }
    }

    // Helper function to swap two elements in an array
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Driver code: generate permutations of an array and print them
    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        permute(arr, 0, arr.length - 1);
    }
}