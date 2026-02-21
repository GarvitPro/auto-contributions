public class DivideAndConquer {
    // Function to find the sum of two elements in an array
    public static int sum(int[] arr, int low, int high) {
        // Base case: if the array has one element, return the element
        if (low == high) {
            return arr[low];
        }
        
        // Divide the array into two halves
        int mid = (low + high) / 2;
        int midValue = sum(arr, low, mid);
        int rightSum = sum(arr, mid + 1, high);
        
        // Conquer the problem by finding the sum of the two halves
        // and returning the result
        return midValue + rightSum;
    }

    // Function to find the maximum element in an array
    public static int max(int[] arr, int low, int high) {
        // Base case: if the array has one element, return the element
        if (low == high) {
            return arr[low];
        }
        
        // Divide the array into two halves
        int mid = (low + high) / 2;
        int maxLeft = max(arr, low, mid);
        int maxRight = max(arr, mid + 1, high);
        
        // Conquer the problem by finding the maximum of the two halves
        // and returning the result
        return Math.max(maxLeft, maxRight);
    }

    // Function to find the minimum element in an array
    public static int min(int[] arr, int low, int high) {
        // Base case: if the array has one element, return the element
        if (low == high) {
            return arr[low];
        }
        
        // Divide the array into two halves
        int mid = (low + high) / 2;
        int minLeft = min(arr, low, mid);
        int minRight = min(arr, mid + 1, high);
        
        // Conquer the problem by finding the minimum of the two halves
        // and returning the result
        return Math.min(minLeft, minRight);
    }

    // Function to find the maximum element in an array using binary search
    public static int binaryMax(int[] arr, int low, int high) {
        // Base case: if the array has one element, return the element
        if (low == high) {
            return arr[low];
        }
        
        // If the middle element is greater than the rightmost element, 
        // the maximum element must be in the right half
        if (arr[mid] < arr[high]) {
            return binaryMax(arr, mid + 1, high);
        }
        
        // If the middle element is less than the rightmost element, 
        // the maximum element must be in the left half
        if (arr[mid] > arr[high]) {
            return binaryMax(arr, low, mid);
        }
        
        // If the middle element is equal to the rightmost element, 
        // we can return the middle element
        return arr[mid];
    }

    // Function to find the minimum element in an array using binary search
    public static int binaryMin(int[] arr, int low, int high) {
        // Base case: if the array has one element, return the element
        if (low