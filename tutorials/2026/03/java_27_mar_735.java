public class PrefixSum {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 7, 2};
        int n = nums.length;
        int[] prefixSum = new int[n + 1];
        
        // Step 1: Initialize the first element of the prefix sum array
        for (int i = 0; i < n; i++) {
            prefixSum[i] = nums[i];
        }
        
        // Step 2: Calculate the prefix sum for each subarray
        for (int i = 1; i < n; i++) {
            prefixSum[i] += prefixSum[i - 1]; 
        }

        System.out.println("Prefix Sum array is:");
        printArray(prefixSum);
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length-1){
                System.out.print(arr[i]);
            } else {
                System.out.print(arr[i] + " ");
            }
        }
        System.out.println();
    }

    public static void queryPrefixSum(int left, int right) {
        return prefixSum[right] - prefixSum[left - 1];
    }
}