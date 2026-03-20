import java.util.Arrays;

public class SegmentTree {
    // Function to build the segment tree
    private static int[] build(int[] arr, int n) {
        int size = Integer.highestOneBit(n);
        int[] tree = new int[2 * size];
        for (int i = 0; i < n; i++) {
            tree[i + size] = arr[i];
        }
        // Fill the rest of the array with the minimum value
        for (int i = size - 1; i > 0; i--) {
            tree[i] = Math.min(tree[2 * i], tree[2 * i + 1]);
        }
        return tree;
    }

    // Function to update a segment in the segment tree
    private static void update(int[] tree, int index, int value, int n) {
        for (int i = index + Integer.highestOneBit(n); i > 0; i /= 2) {
            tree[i] += value;
        }
    }

    // Function to query a segment in the segment tree
    private static int query(int[] tree, int left, int right, int n) {
        int sum = 0;
        for (left += Integer.highestOneBit(n), right += Integer.highestOneBit(n); left < right; left /= 2, right /= 2) {
            if ((left & 1) == 1) {
                sum += tree[left++];
            }
            if ((right & 1) == 0) {
                sum += tree[right--];
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        int[] arr = {3, 2, 15, 9};
        int n = arr.length;
        int[] tree = build(arr, n);

        // Update a segment in the segment tree
        update(tree, 1, -7, n);
        System.out.println("Minimum sum of values in segments [0,5] and [4,n-1]: " + query(tree, 0, 5, n) + " and " + query(tree, 4, n, n));
        
        // Update a segment in the segment tree
        update(tree, 2, -7, n);
        System.out.println("Minimum sum of values in segments [0,5] and [3,n-1]: " + query(tree, 0, 5, n) + " and " + query(tree, 3, n, n));

        // Query a segment in the segment tree
        System.out.println("Minimum sum of values in segments [1,4]: " + query(tree, 1, 4, n));
    }
}