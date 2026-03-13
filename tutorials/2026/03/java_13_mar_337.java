public class DivideAndConquer {
    // Function to find the minimum element in an array using merge sort
    public static int minElement(int[] arr) {
        // If the array has only one element, return that element
        if (arr.length == 1) {
            return arr[0];
        }

        // Find the middle index of the array
        int mid = arr.length / 2;

        // Divide the array into two halves
        int[] leftHalf = new int[mid];
        System.arraycopy(arr, 0, leftHalf, 0, mid);
        int[] rightHalf = new int[arr.length - mid];

        // Copy elements from original array to right half
        System.arraycopy(arr, mid, rightHalf, 0, arr.length - mid);

        // Recursively find the minimum element in both halves
        int minLeft = minElement(leftHalf);
        int minRight = minElement(rightHalf);

        // Return the minimum of both halves
        return Math.min(minLeft, minRight);
    }

    public static void main(String[] args) {
        int[] arr = {5, 2, 8, 1, 9};
        System.out.println("Minimum element in array is " + minElement(arr));
    }
}