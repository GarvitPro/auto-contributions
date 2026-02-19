import java.util.Stack;

public class MonotonicStack {
    // This program teaches how to use a stack data structure to find monotonic sequences in an array.
    // A monotonic sequence is one that is either monotonically increasing or decreasing.
    // The monotonic stack algorithm works by iterating through the array and using a stack to keep track of the elements we've seen so far.

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        findMonotonicSequences(arr);
    }

    // This function finds all monotonic sequences in the given array.
    // It uses a stack to keep track of the elements we've seen so far.
    public static void findMonotonicSequences(int[] arr) {
        int n = arr.length;
        Stack<Integer> stack = new Stack<>();
        boolean increasing = true;  // Flag to check if the sequence is increasing or decreasing
        for (int i = 0; i < n; i++) {
            // If the stack is not empty and the current element is less than the top of the stack,
            // we know that the sequence was increasing until this point, so we print it out.
            if (!stack.isEmpty() && arr[i] < stack.peek()) {
                System.out.println("Increasing sequence: ");
                while (!stack.isEmpty()) {
                    System.out.print(stack.pop() + " ");
                }
                System.out.println();
            }

            // If the stack is not empty and the current element is greater than the top of the stack,
            // we know that the sequence was decreasing until this point, so we print it out.
            if (!stack.isEmpty() && arr[i] > stack.peek()) {
                System.out.println("Decreasing sequence: ");
                while (!stack.isEmpty()) {
                    System.out.print(stack.pop() + " ");
                }
                System.out.println();
            }

            // If the stack is empty, or the current element is greater than or less than the top of the stack,
            // we push the current element onto the stack.
            if (stack.isEmpty() || arr[i] > stack.peek()) {
                stack.push(arr[i]);
            } else {
                while (!stack.isEmpty() && arr[i] >= stack.peek()) {
                    stack.pop();
                }
                stack.push(arr[i]);
            }

            // We flip the increasing flag whenever we encounter a new element that is greater than
            // or less than the top of the stack.
            if (arr[i] < stack.peek()) {
                increasing = false;
            } else if (arr[i] > stack.peek()) {
                increasing = true;
            }
        }

        // If the sequence was neither increasing nor decreasing, we print out the final element.
        if (!increasing) {
            System.out.println("Final element: " + arr[n - 1]);
        }
    }
}