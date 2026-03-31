import java.util.*;

public class UnionFind {
    private int[] parent;
    private int[] rank;

    public UnionFind(int n) {
        this.parent = new int[n];
        this.rank = new int[n];

        // Initialize all elements as their own parents and with a rank of 0.
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            // If the element is not its own parent, find its root and make it a child of that.
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        // If the two roots are different, merge them. 
        if (rootX != rootY) {
            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else {
                parent[rootY] = rootX;
                // If the ranks are equal, increase one of them.
                rank[rootX]++;
            }
        }
    }

    public static void main(String[] args) {
        UnionFind uf = new UnionFind(10);

        System.out.println("Initially connected components: " + uf.parent.length);
        uf.union(0, 1);
        uf.union(1, 2);
        uf.union(3, 4);
        uf.union(5, 6);
        uf.union(7, 8);
        System.out.println("Connected components after the first union: " + Arrays.toString(uf.parent));
        uf.union(9, 0); // Connects 9 to the group containing 0.
        System.out.println("Connected components after the second union: " + Arrays.toString(uf.parent));
    }
}