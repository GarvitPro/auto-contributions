// PrefixSum.java

public class PrefixSum {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5};
        int n = nums.length;

        // Create a prefix sum array of the same size as nums
        int[] prefixSum = new int[n];

        // Initialize the first element of prefixSum to be equal to the first element of nums
        prefixSum[0] = nums[0];
        System.out.println("Prefix Sum: [ " + prefixSum[0] + "]");

        // Calculate each subsequent element in prefixSum by adding the current element in nums to the previous prefix sum
        for (int i = 1; i < n; i++) {
            int nextNum = nums[i];
            int prevSum = prefixSum[i - 1];

            prefixSum[i] = prevSum + nextNum;
            System.out.println("Prefix Sum: [ " + prefixSum[i] + "]");

        }

    }
}