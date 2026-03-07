public class SegmentTree {
    public static void main(String[] args) {
        SegmentTree st = new SegmentTree();
        st.buildSegmentTree(new int[]{1, 2, 3, 4, 5}, 0, 4);
        st.updateRange(1, 3, 10);
        st.updateRange(2, 3, 20);
        System.out.println("Max: " + st.queryMax(0, 4));
        System.out.println("Sum: " + st.querySum(0, 4));
    }
}

class SegmentTree {
    int[] tree;
    int size;

    public SegmentTree() {
        this.tree = new int[4 * 4]; // 4 * n is the typical power of 2 for trees
        this.size = 4;
    }

    public void buildSegmentTree(int[] arr, int node, int low, int high) {
        // base case: if the segment is a leaf node
        if (low == high) {
            tree[node] = arr[low];
            return;
        }

        int mid = (low + high) / 2;
        buildSegmentTree(arr, 2 * node + 1, low, mid);
        buildSegmentTree(arr, 2 * node + 2, mid + 1, high);

        // combine the values
        tree[node] = combine(tree[2 * node + 1], tree[2 * node + 2]);
    }

    public void updateRange(int left, int right, int value) {
        updateRange(0, 0, size - 1, left, right, value);
    }

    private void updateRange(int index, int node, int low, int high, int left, int right) {
        // if the segment is empty, do nothing
        if (low > right || high < left) return;

        // if the entire segment is affected, update it
        if (low >= left && high <= right) {
            tree[node] = value;
            return;
        }

        // split the segment in half
        int mid = (low + high) / 2;
        updateRange(2 * index + 1, 2 * node + 1, low, mid, left, right);
        updateRange(2 * index + 2, 2 * node + 2, mid + 1, high, left, right);

        // combine the values
        tree[node] = combine(tree[2 * index + 1], tree[2 * index + 2]);
    }

    public int queryMax(int left, int right) {
        return queryMax(0, 0, size - 1, left, right);
    }

    private int queryMax(int index, int node, int low, int high, int left, int right) {
        // if the segment is empty, return negative infinity
        if (low > right || high < left) return Integer.MIN_VALUE;

        // if the entire segment is being queried
        if (low >= left && high <= right) {
            return tree[node];
        }

        // split the segment in half
        int mid = (low + high) / 2;
        int leftMax = queryMax(2 * index + 1, 2 * node +