import java.util.Stack;

public class MonotonicStack {
    // Define a method to check if the given array is monotonically increasing or decreasing
    public static boolean isMonotonic(int[] arr) {
        int i = 0;
        int j = arr.length - 1;
        while (i < j) {
            if ((arr[i] > arr[i + 1]) != (arr[j] > arr[j - 1])) {
                return false; // If the condition is not met, the array is not monotonic
            }
            i++; // Move to the next element in the left half of the array
            j--; // Move to the next element in the right half of the array
        }
        return true; // If no counterexample is found, the array is monotononic
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        System.out.println("Is the array monotonic? " + isMonotonic(arr)); // Output: true

        int[] arr2 = {5, 4, 3, 2, 1};
        System.out.println("Is the array monotonic? " + isMonotonic(arr2)); // Output: false
    }
}