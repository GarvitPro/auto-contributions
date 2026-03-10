import java.util.Arrays;

public class SegmentTree {
    // Define the function to initialize segment tree
    private static int[] initializeSegmentTree(int[] arr, int n) {
        // Calculate the size of segment tree
        int size = 4 * n;

        // Create the segment tree array
        int[] segmentTree = new int[size];

        // Initialize the segment tree with array values
        buildSegmentTree(arr, segmentTree, 0, 0, n - 1);

        return segmentTree;
    }

    // Define the function to build the segment tree
    private static void buildSegmentTree(int[] arr, int[] segmentTree, int n, int start, int end) {
        // Base case: when start equals end
        if (start == end) {
            segmentTree[n] = arr[start];
            return;
        }

        // Calculate the mid index
        int mid = (start + end) / 2;

        // Recursively build the left and right halves
        buildSegmentTree(arr, segmentTree, n * 2, start, mid);
        buildSegmentTree(arr, segmentTree, n * 2 + 1, mid + 1, end);

        // Update the segment tree with the maximum of left and right halves
        segmentTree[n] = Math.max(segmentTree[n * 2], segmentTree[n * 2 + 1]);
    }

    // Define the function to query the segment tree
    private static int querySegmentTree(int[] segmentTree, int n, int left, int right) {
        // Base case: when left equals right
        if (left > right) {
            return Integer.MIN_VALUE;
        }

        // If left equals start and right equals end, return the segment tree value
        if (left == 0 && right == segmentTree.length / 4 - 1) {
            return segmentTree[n];
        }

        // Calculate the mid index
        int mid = (left + right) / 2;

        // Recursively query the left and right halves
        int leftMax = querySegmentTree(segmentTree, n * 2, left, mid);
        int rightMax = querySegmentTree(segmentTree, n * 2 + 1, mid + 1, right);

        // Return the maximum of left and right halves
        return Math.max(leftMax, rightMax);
    }

    public static void main(String[] args) {
        // Define the input array
        int[] arr = {1, 3, 5, 7, 9};

        // Initialize the segment tree
        int[] segmentTree = initializeSegmentTree(arr, arr.length);

        // Print the segment tree values
        System.out.println("Segment Tree Values:");
        for (int i = 0; i < segmentTree.length; i++) {
            System.out.print(segmentTree[i] + " ");
        }
        System.out.println();

        // Query the segment tree for a range
        System.out.println("Query Result for Range 1-3:");
        System.out.println(querySegmentTree(segmentTree, 1, 0, 2));

        System.out.println("Query Result for Range 3-4:");
        System.out.println(querySegmentTree(segmentTree, 1, 2, 3));
    }
}