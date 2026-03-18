public class SlidingWindow {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        System.out.println("Original array: ");
        printArray(arr);

        // Initialize window size
        int windowSize = 3;

        // Call the function to find max sum of subarray with given size
        int maxSum = findMaxSum(arr, windowSize);
        System.out.println("Maximum sum of subarray of size " + windowSize + ": " + maxSum);

    }

    public static int findMaxSum(int[] arr, int windowSize) {
        if (arr.length < windowSize)
            return -1;

        // Initialize variables to keep track of maximum sum and current sum
        int maxSum = Integer.MIN_VALUE;
        int currSum = 0;

        // Iterate over the array with a sliding window
        for (int i = 0; i <= arr.length - windowSize; i++) {
            // Calculate the sum of elements within the window
            for (int j = i; j < i + windowSize; j++)
                currSum += arr[j];

            // Update maxSum if currentSum is greater
            if (currSum > maxSum)
                maxSum = currSum;

            // Subtract the first element of the window from currSum and add the next one to maintain a sliding window
            currSum -= arr[i];
        }

        return maxSum;
    }

    public static void printArray(int[] arr) {
        for (int i : arr)
            System.out.print(i + " ");
        System.out.println();
    }
}