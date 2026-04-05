import java.util.*;

public class DijkstraShortestPath {

    public static void main(String[] args) {
        int[][] graph = {
            {0, 4, 0, 0, 0, 0, 0, 8, 0},
            {4, 0, 8, 0, 0, 0, 0, 11, 0},
            {0, 8, 0, 7, 0, 4, 0, 0, 2},
            {0, 0, 7, 0, 9, 14, 0, 0, 0},
            {0, 0, 0, 9, 0, 10, 0, 0, 0},
            {0, 0, 4, 14, 10, 0, 2, 0, 0},
            {0, 0, 0, 0, 0, 2, 0, 1, 6},
            {8, 11, 0, 0, 0, 0, 1, 0, 7},
            {0, 0, 2, 0, 0, 0, 6, 7, 0}
        };

        int[][] shortestDistances = dijkstraShortestPath(graph);
        printShortestDistances(shortestDistances);
    }

    /**
     * Dijkstra's algorithm to find the shortest path in a weighted graph.
     *
     * @param graph adjacency matrix representation of the graph
     * @return shortest distances from source node to all other nodes
     */
    public static int[][] dijkstraShortestPath(int[][] graph) {
        // Create a list of edges, initially with infinite distance and no previous node
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                if (graph[i][j] != 0) {
                    // Create an edge with distance and previous node
                    edges.add(new Edge(graph[i][j], i, j));
                }
            }
        }

        // Sort the edges by their distances
        edges.sort(Comparator.comparingInt(Edge::getDistance));

        // Create a map to store shortest distances
        Map<Integer, Integer> distances = new HashMap<>();
        for (int i = 0; i < graph.length; i++) {
            distances.put(i, Integer.MAX_VALUE);
        }
        distances.put(0, 0); // Distance from source node is always 0

        // Create a map to store previous nodes
        Map<Integer, Integer> previousNodes = new HashMap<>();
        for (int i = 0; i < graph.length; i++) {
            previousNodes.put(i, -1);
        }

        while (!edges.isEmpty()) {
            int currentEdgeIndex = edges.indexOf(edges.get(0));
            Edge currentEdge = edges.remove(0);

            // Update distances and previous nodes if necessary
            int currentNode = currentEdge.getNode();
            int distance = currentEdge.getDistance();
            for (int i = 0; i < graph[currentNode].length; i++)