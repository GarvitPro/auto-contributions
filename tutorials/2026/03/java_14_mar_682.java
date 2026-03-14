import java.util.*;

public class Dijkstra {

    // Define the graph as an adjacency list
    private static final int[][] graph = {
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

    // Function to implement Dijkstra's algorithm
    public static void dijkstra(int[][] graph, int start) {
        // Initialize distances and previous nodes for all vertices
        int numVertices = graph.length;
        int[] distance = new int[numVertices];
        boolean[] visited = new boolean[numVertices];

        // Set initial distance to infinity and mark as not visited
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(visited, false);

        // Distance of the start vertex is 0
        distance[start] = 0;

        // Create a priority queue with the start vertex
        PriorityQueue<Vertex> pq = new PriorityQueue<>();
        pq.add(new Vertex(start, 0));

        while (!pq.isEmpty()) {
            // Extract the vertex with minimum distance from the priority queue
            Vertex u = pq.poll();

            // Mark as visited to avoid revisiting
            visited[u.vertex] = true;

            // For each neighbor of the extracted vertex
            for (int v = 0; v < numVertices; v++) {
                if (graph[u.vertex][v] > 0 && !visited[v]) {
                    // Calculate tentative distance
                    int newDistance = u.distance + graph[u.vertex][v];

                    // If the calculated distance is less than the current known distance, update it
                    if (newDistance < distance[v]) {
                        distance[v] = newDistance;
                        pq.add(new Vertex(v, newDistance));
                    }
                }
            }
        }

        // Print shortest distances from the start vertex to all other vertices
        for (int i = 0; i < numVertices; i++) {
            if (i == start) continue;
            System.out.println("Shortest distance from " + start + " to " + i + ": " + distance[i]);
        }
    }

    // Define a class for the vertex with its index, distance, and previous node
    static class Vertex implements Comparable<Vertex> {
        int vertex;
        int distance;

        public Vertex(int vertex