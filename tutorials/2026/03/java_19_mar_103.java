// Segment Tree Implementation in Java

public class SegmentTree {
    // Define the type of data stored in the segment tree
    private int[] array;
    private int[] segmentTree;

    public SegmentTree(int size) {
        array = new int[size];
        segmentTree = new int[4 * size]; // 4 times the size for efficiency
    }

    // Function to build the segment tree
    void build(int node, int low, int high) {
        if (low == high) { // Base case: single element
            segmentTree[node] = array[low];
            return;
        }
        int mid = (low + high) / 2; // Calculate midpoint
        build(2 * node + 1, low, mid); // Recursively build left subtree
        build(2 * node + 2, mid + 1, high); // Recursively build right subtree
        segmentTree[node] = segmentTree[2 * node + 1] + segmentTree[2 * node + 2]; // Calculate total for this node
    }

    // Function to query the sum of elements in a range
    int query(int node, int low, int high, int start, int end) {
        if (low > end || high < start) { // Range is out of bounds
            return 0;
        } else if (low >= start && high <= end) { // Range matches
            return segmentTree[node];
        }
        int mid = (low + high) / 2; // Calculate midpoint
        return query(2 * node + 1, low, mid, start, end) + query(2 * node + 2, mid + 1, high, start, end); // Recursively sum ranges
    }

    // Function to update a single element in the array
    void update(int index, int value) {
        updateUtil(0, 0, array.length - 1, index, value);
    }

    private void updateUtil(int node, int low, int high, int index, int value) {
        if (low == high && index == low) { // Update single element
            segmentTree[node] = value;
            return;
        }
        int mid = (low + high) / 2; // Calculate midpoint
        if (index <= mid) {
            updateUtil(2 * node + 1, low, mid, index, value);
        } else {
            updateUtil(2 * node + 2, mid + 1, high, index, value);
        }
        segmentTree[node] = segmentTree[2 * node + 1] + segmentTree[2 * node + 2]; // Update total for this node
    }

    public static void main(String[] args) {
        SegmentTree segmentTree = new SegmentTree(5); // Create a segment tree with 5 elements
        segmentTree.array = new int[]{10, 20, 30, 40, 50};
        segmentTree.build(0, 0, 4); // Build the segment tree

        System.out.println("Sum of range (2-3): " + segmentTree.query(0, 0, 4, 2, 3)); // Query sum of elements in range
        System.out.println("Sum of range (