import java.util.*;

public class TopologicalSort {

    // Represents a directed graph using adjacency list
    static class Graph {
        // Adjacency list representation of the graph
        private Map<Integer, List<Integer>> adjacencyList;

        public Graph() {
            this.adjacencyList = new HashMap<>();
        }

        // Adds an edge between two nodes in the graph
        public void addEdge(int source, int destination) {
            adjacencyList.computeIfAbsent(source, k -> new ArrayList<>()).add(destination);
        }

        // Performs a topological sort on the graph
        public List<Integer> topologicalSort() {
            // Create a map to store the in-degree of each node
            Map<Integer, Integer> inDegree = new HashMap<>();
            for (Integer node : adjacencyList.keySet()) {
                inDegree.put(node, 0);
            }

            // Calculate the in-degree of each node
            for (List<Integer> neighbors : adjacencyList.values()) {
                for (Integer neighbor : neighbors) {
                    inDegree.put(neighbor, inDegree.get(neighbor) + 1);
                }
            }

            // Create a queue to store nodes with in-degree 0
            Queue<Integer> queue = new LinkedList<>();
            for (Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
                if (entry.getValue() == 0) {
                    queue.add(entry.getKey());
                }
            }

            // Create a list to store the sorted nodes
            List<Integer> sorted = new ArrayList<>();

            // Perform the topological sort
            while (!queue.isEmpty()) {
                int node = queue.poll();
                sorted.add(node);

                // Decrease the in-degree of neighboring nodes
                for (Integer neighbor : adjacencyList.get(node)) {
                    inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                    if (inDegree.get(neighbor) == 0) {
                        queue.add(neighbor);
                    }
                }
            }

            // Check if the graph has a cycle
            if (sorted.size() != adjacencyList.size()) {
                throw new RuntimeException("Graph contains a cycle");
            }

            return sorted;
        }
    }

    public static void main(String[] args) {
        // Create a sample graph
        Graph graph = new Graph();
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        // Perform the topological sort
        List<Integer> sorted = graph.topologicalSort();
        System.out.println("Topological Sort: " + sorted);
    }
}