import java.util.Arrays;

public class SlidingWindow {

    /**
     * This class demonstrates the concept of a sliding window.
     * A sliding window is a technique used to solve problems that require examining a subset of a larger sequence.
     * The idea is to start with a "window" of a certain size and then move the window one element at a time,
     * examining the new subset at each position.
     */

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        int k = 3;
        int[] res = maxSlidingWindow(arr, k);
        System.out.println("Maximum element in each window: " + Arrays.toString(res));
    }

    /**
     * This function returns the maximum element in each window of size k.
     * 
     * @param arr    the input array
     * @param k      the size of the window
     * @return the maximum element in each window
     */
    public static int[] maxSlidingWindow(int[] arr, int k) {
        int n = arr.length;
        int[] res = new int[n - k + 1];

        // Initialize the deque to store indices of elements in the window
        java.util.Deque<Integer> dq = new java.util.ArrayDeque<>();

        // Process each element in the array
        for (int i = 0; i < n; i++) {
            // Remove elements from the back of the deque that are out of the current window
            while (!dq.isEmpty() && dq.peekFirst() <= i - k) {
                dq.pollFirst();
            }

            // Remove elements from the front of the deque that are smaller than the current element
            while (!dq.isEmpty() && arr[dq.peekLast()] < arr[i]) {
                dq.pollLast();
            }

            // Add the current element to the deque
            dq.offerLast(i);

            // If the window is full, add the maximum element to the result array
            if (i >= k - 1) {
                res[i - k + 1] = arr[dq.peekFirst()];
            }
        }

        return res;
    }
}