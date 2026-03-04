// BFS Graph Traversal in Java
import java.util.*;

public class BFS {

    // Adjacency list representation of a graph
    private static class Graph {
        private List<List<Integer>> adjacencyList;

        public Graph(int vertices) {
            adjacencyList = new ArrayList<>();
            for (int i = 0; i < vertices; i++) {
                adjacencyList.add(new ArrayList<>());
            }
        }

        // Add an edge between two vertices
        public void addEdge(int src, int dest) {
            adjacencyList.get(src).add(dest);
            adjacencyList.get(dest).add(src); // For undirected graph
        }

        // Perform BFS traversal
        public void bfs(int startVertex) {
            boolean[] visited = new boolean[adjacencyList.size()];
            Queue<Integer> queue = new LinkedList<>();
            queue.add(startVertex);
            visited[startVertex] = true;

            while (!queue.isEmpty()) {
                int currentVertex = queue.poll();
                System.out.print(currentVertex + " ");

                // Visit all adjacent vertices
                for (int adjacentVertex : adjacencyList.get(currentVertex)) {
                    if (!visited[adjacentVertex]) {
                        queue.add(adjacentVertex);
                        visited[adjacentVertex] = true;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        // Create a graph with 5 vertices
        Graph graph = new Graph(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);

        // Perform BFS traversal starting from vertex 0
        System.out.println("BFS Traversal:");
        graph.bfs(0);
    }
}