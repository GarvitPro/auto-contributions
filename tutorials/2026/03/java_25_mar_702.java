// Topological Sort in Java
// Author: [Your Name]
// Date: 12/02/2023

import java.util.*;

public class TopologicalSort {

    // Define a class to represent a graph edge
    static class Edge {
        int src, dest;

        public Edge(int source, int destination) {
            this.src = source;
            this.dest = destination;
        }
    }

    // Function to perform topological sort using DFS
    private static boolean dfs(boolean[] visited, List<Integer> stack, int v, List<List<Edge>> graph) {
        // Mark the current node as visited and add it to the recursion stack
        visited[v] = true;

        // Recur for all adjacent vertices of current vertex
        for (Edge edge : graph.get(v)) {
            if (!visited[edge.dest]) {
                if (!dfs(visited, stack, edge.dest, graph)) {
                    return false;
                }
            } else if (stack.contains(edge.dest)) {
                // If the adjacent node is in the recursion stack, there's a cycle
                return false;
            }
        }

        // Remove current vertex from the recursion stack
        stack.add(v);

        return true;
    }

    // Function to perform topological sort using Kahn's algorithm
    private static List<Integer> kahnAlgorithm(List<List<Edge>> graph) {
        int numVertices = graph.size();

        // Create a list to store the in-degree of each vertex
        int[] inDegree = new int[numVertices];

        // Calculate the in-degree of each vertex
        for (List<Edge> edge : graph) {
            inDegree[edge.dest]++;
        }

        // Create a queue to store vertices with an in-degree of 0
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numVertices; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        // Perform topological sort using Kahn's algorithm
        List<Integer> stack = new ArrayList<>();
        while (!queue.isEmpty()) {
            int temp = queue.poll();
            stack.add(temp);

            for (Edge edge : graph.get(temp)) {
                inDegree[edge.dest]--;
                if (inDegree[edge.dest] == 0) {
                    queue.add(edge.dest);
                }
            }
        }

        return stack;
    }

    // Main function to test the topological sort implementation
    public static void main(String[] args) {
        List<List<Edge>> graph = new ArrayList<>();
        List<Edge> edge1 = new ArrayList<>(Arrays.asList(new Edge(5, 2), new Edge(5, 0)));
        List<Edge> edge2 = new ArrayList<>(Arrays.asList(new Edge(4, 0), new Edge(4, 1)));
        List<Edge> edge3 = new ArrayList<>(Arrays.asList(new Edge(4, 1), new Edge(3, 1)));
        List<Edge> edge4 = new ArrayList<>(Arrays.asList(new Edge(2, 3), new Edge(2, 1)));
        List<Edge> edge5 = new ArrayList<>(Arrays.asList(new Edge(2, 1), new Edge(0, 1)));
        List<Edge> edge6 = new ArrayList<>(Arrays.asList(new Edge(2, 1)));

        graph.add(edge1);
        graph.add(edge2);
        graph