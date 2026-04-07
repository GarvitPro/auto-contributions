import java.util.*;

public class TopologicalSort {
    // Define the directed graph using adjacency list representation
    private static class Graph {
        Map<Integer, List<Integer>> adjList;

        public Graph() {
            this.adjList = new HashMap<>();
        }

        // Add an edge to the graph
        public void addEdge(int src, int dest) {
            if (!adjList.containsKey(src)) {
                adjList.put(src, new ArrayList<>());
            }
            adjList.get(src).add(dest);
        }
    }

    // Perform topological sort using Kahn's algorithm
    private static List<Integer> topologicalSort(Graph graph) {
        // Initialize the in-degree of each node to 0
        Map<Integer, Integer> inDegree = new HashMap<>();
        for (int node : graph.adjList.keySet()) {
            inDegree.put(node, 0);
        }

        // Calculate the in-degree of each node
        for (List<Integer> neighbors : graph.adjList.values()) {
            for (Integer neighbor : neighbors) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }

        // Initialize a queue to store nodes with in-degree 0
        Queue<Integer> queue = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        // Perform topological sort using Kahn's algorithm
        List<Integer> sortedNodes = new ArrayList<>();
        while (!queue.isEmpty()) {
            int node = queue.poll();
            sortedNodes.add(node);

            for (Integer neighbor : graph.adjList.get(node)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        // Check for cycles
        if (sortedNodes.size() != graph.adjList.size()) {
            throw new RuntimeException("Cycle detected in the graph");
        }

        return sortedNodes;
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addEdge(5, 2);
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);

        List<Integer> sortedNodes = topologicalSort(graph);
        System.out.println("Topologically Sorted Nodes: " + sortedNodes);
    }
}