import java.util.Stack;

public class MonotonicStack {
    // Function to check if the given array is monotonic
    public static boolean isMonotonic(int[] arr) {
        int up = 0;
        int down = 1;
        
        while (up < arr.length && down < arr.length) {
            // If current element is greater than next element, swap them
            if (arr[up] > arr[down]) {
                swap(arr, up, down);
                up++;
                down++;
            }
            // If current element is less than next element, swap them
            else if (arr[up] < arr[down]) {
                swap(arr, down, up);
                down++;
            }
            // Current element is equal to the next element
            else {
                up++;
                down++;
            }
        }
        
        // If we have reached the end of the array without failing a condition,
        // then the array is monotonic
        return true;
    }

    // Function to swap two elements in an array
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        // Test case 1: Monotonic array
        int[] arr1 = {1, 2, 3, 4, 5};
        System.out.println("Is monotonic? " + isMonotonic(arr1)); 

        // Test case 2: Decreasing array
        int[] arr2 = {5, 4, 3, 2, 1};
        System.out.println("Is monotonic? " + isMonotonic(arr2)); 

        // Test case 3: Increasing array
        int[] arr3 = {1, 2, 3, 4, 5};
        System.out.println("Is monotonic? " + isMonotonic(arr3)); 

        // Test case 4: Duplicate elements
        int[] arr4 = {1, 2, 2, 1};
        System.out.println("Is monotonic? " + isMonotonic(arr4));
    }
}