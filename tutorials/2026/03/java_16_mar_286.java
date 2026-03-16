// Segment Tree Implementation in Java

public class SegmentTree {
    // Node class representing individual elements in the segment tree
    public static class Node {
        int left, right;
        long sum;

        public Node(int left, int right) {
            this.left = left;
            this.right = right;
            if (left != right) {
                int mid = (left + right) / 2;
                this.sum = new Node(left, mid).sum + new Node(mid + 1, right).sum;
            } else {
                this.sum = 0; // leaf node
            }
        }

        public long query(int left, int right) {
            if (this.left == left && this.right == right)
                return sum;
            int mid = (left + right) / 2;
            if (right <= mid)
                return new Node(left, mid).query(left, right);
            else if (left >= mid + 1)
                return new Node(mid + 1, right).query(left, right);
            else {
                return new Node(left, mid).query(left, mid) + new Node(mid + 1, right).query(mid + 1, right);
            }
        }

        public void update(int idx, long val) {
            if (left == right)
                sum = val;
            else {
                int mid = (left + right) / 2;
                if (idx <= mid)
                    new Node(left, mid).update(idx, val);
                else
                    new Node(mid + 1, right).update(idx, val);
                sum = new Node(left, mid).sum + new Node(mid + 1, right).sum;
            }
        }
    }

    // Segment tree constructor
    public static Node constructSegmentTree(int[] arr) {
        return constructSegmentTree(arr, 0, arr.length - 1);
    }

    private static Node constructSegmentTree(int[] arr, int start, int end) {
        if (start > end)
            return null;

        int mid = (start + end) / 2;
        Node node = new Node(start, end);

        // Recursively construct left and right subtrees
        node.left = constructSegmentTree(arr, start, mid);
        node.right = constructSegmentTree(arr, mid + 1, end);

        return node;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        Node segmentTree = constructSegmentTree(arr);

        // Query the sum of elements in range [0, 3]
        System.out.println("Sum of elements from index 0 to 3: " + segmentTree.query(0, 3));

        // Update the value at index 2
        segmentTree.update(2, 10);
        System.out.println("Updated Sum of elements from index 0 to 3: " + segmentTree.query(0, 3));
    }
}