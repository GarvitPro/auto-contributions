import java.util.Arrays;

public class RecursiveBacktracking {
    // Function to find all permutations of an array using recursive backtracking
    public static void findPermutations(int[] arr, int start, int end) {
        // Base case: if start is greater than end, no more permutations
        if (start > end) {
            System.out.println(Arrays.toString(arr));
            return;
        }

        // Iterate through each element in the array
        for (int i = start; i <= end; i++) {
            // Swap the current element with the start element
            swap(arr, start, i);

            // Recursive call with start and end incremented
            findPermutations(arr, start + 1, end);

            // Swap back to original array
            swap(arr, start, i);
        }
    }

    // Function to swap two elements in an array
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        System.out.println("Permutations of array [1, 2, 3] are:");
        findPermutations(arr, 0, arr.length - 1);
    }
}