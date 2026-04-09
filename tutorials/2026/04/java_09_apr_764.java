import java.util.Arrays;

public class MonotonicStack {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        System.out.println("Original Array: " + Arrays.toString(arr));
        System.out.println("Monotonic Stack: " + printMonotonicStack(arr));
    }

    public static String printMonotonicStack(int[] arr) {
        int n = arr.length;
        // Create a monotonic stack
        int[] stack = new int[n];
        int top = -1;
        for (int i = 0; i < n; i++) {
            while (top >= 0 && arr[stack[top]] > arr[i]) {
                top--;
            }
            if (top < 0 || arr[stack[top]] != arr[i]) {
                stack[++top] = i;
            } else {
                // If we find a duplicate, remove the old one
                stack[top + 1] = i; // We increment top by 2 to maintain correct index
            }
        }

        int[] resultArray = new int[n];
        // Build the monotonic stack array
        for (int i = n - 1; i >= 0; i--) {
            resultArray[i] = arr[stack[i]];
        }

        return Arrays.toString(resultArray);
    }
}