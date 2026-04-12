public class UnionFind {

    // Number of vertices in the graph
    private int numVertices;

    // Array to keep track of parent of each vertex
    private int[] parent;
    // Array to keep track of rank of each vertex
    private int[] rank;

    public UnionFind(int numVertices) {
        this.numVertices = numVertices;
        this.parent = new int[numVertices];
        this.rank = new int[numVertices];

        // Initialize all vertices as separate sets
        for (int i = 0; i < numVertices; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    // Function to find the parent of a vertex
    private int find(int vertex) {
        if (parent[vertex] != vertex)
            parent[vertex] = find(parent[vertex]);
        return parent[vertex];
    }

    // Function to union two sets
    public void union(int set1, int set2) {
        int root1 = find(set1);
        int root2 = find(set2);

        // If roots are same, then already in same set
        if (root1 == root2)
            return;

        // Attach smaller rank tree under root of high rank tree
        if (rank[root1] < rank[root2])
            parent[root1] = root2;
        else {
            if (rank[root1] == rank[root2])
                rank[root1]++;
            parent[root2] = root1;
        }
    }

    // Function to check if two vertices are in same set
    public boolean isSameSet(int vertex1, int vertex2) {
        return find(vertex1) == find(vertex2);
    }

    // Driver Code
    public static void main(String[] args) {
        UnionFind uf = new UnionFind(6);

        System.out.println("Initially, vertices are in following sets:");
        for (int i = 0; i < 6; i++) {
            System.out.print(i + " ");
            if (i == 5)
                System.out.println();
            else
                System.out.print("-");
        }

        // Union of 1 and 3
        uf.union(1, 3);
        System.out.println("\nAfter union of 1 and 3:");
        for (int i = 0; i < 6; i++) {
            System.out.print(i + " ");
            if (i == 5)
                System.out.println();
            else
                System.out.print("-");
        }

        // Union of 2 and 4
        uf.union(2, 4);
        System.out.println("\nAfter union of 2 and 4:");
        for (int i = 0; i < 6; i++) {
            System.out.print(i + " ");
            if (i == 5)
                System.out.println();
            else
                System.out.print("-");
        }

        // Union of 1 and 5
        uf.union(1, 5);
        System.out.println("\nAfter union of 1 and 5:");
        for (int i = 0; i < 6; i++) {
            System.out.print(i + " ");
            if (i == 5)
                System.out.println();
            else
                System.out.print("-");
        }

        // Check if 2, 4 are in same