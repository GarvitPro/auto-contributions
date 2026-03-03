public class SegmentTree {
    // Node class representing each node in the segment tree
    static class Node {
        int min;
        int max;

        Node(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }

    // Segment tree class with methods for update and query
    static class SegmentTree {
        Node[] tree;
        int size;

        SegmentTree(int[] arr) {
            size = arr.length;
            tree = new Node[4 * size];
            buildTree(arr, 0, 0, size - 1);
        }

        // Build the segment tree from the given array
        void buildTree(int[] arr, int node, int start, int end) {
            if (start == end) {
                tree[node] = new Node(arr[start], arr[start]);
            } else {
                int mid = (start + end) / 2;
                buildTree(arr, 2 * node + 1, start, mid);
                buildTree(arr, 2 * node + 2, mid + 1, end);
                tree[node] = merge(tree[2 * node + 1], tree[2 * node + 2]);
            }
        }

        // Merge two nodes to form a new node
        Node merge(Node left, Node right) {
            return new Node(Math.min(left.min, right.min), Math.max(left.max, right.max));
        }

        // Update the value at a specific index in the array
        void update(int index, int newValue) {
            updateTree(0, 0, size - 1, index, newValue);
        }

        // Recursively update the value at a specific index in the array
        void updateTree(int node, int start, int end, int index, int newValue) {
            if (start == index && end == index) {
                tree[node] = new Node(newValue, newValue);
            } else {
                int mid = (start + end) / 2;
                if (index <= mid) {
                    updateTree(2 * node + 1, start, mid, index, newValue);
                } else {
                    updateTree(2 * node + 2, mid + 1, end, index, newValue);
                }
                tree[node] = merge(tree[2 * node + 1], tree[2 * node + 2]);
            }
        }

        // Query the range [start, end] and return the minimum and maximum values
        int[] query(int start, int end) {
            return queryTree(0, 0, size - 1, start, end);
        }

        // Recursively query the range [start, end] and return the minimum and maximum values
        int[] queryTree(int node, int start, int end, int queryStart, int queryEnd) {
            if (queryStart > end || queryEnd < start) {
                return new int[]{-1000000000, 1000000000};
            } else if (queryStart <= start && queryEnd >= end) {
                return new int[]{tree[node].min, tree[node].max};
            } else {
                int mid = (start + end) / 2;
                int[] leftQuery = queryTree(2 * node + 1, start, mid, queryStart, queryEnd