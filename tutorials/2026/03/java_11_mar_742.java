import java.util.*;

public class DijkstraShortestPath {
    public static void main(String[] args) {
        DijkstraShortestPath dijkstra = new DijkstraShortestPath();
        dijkstra.run();
    }

    public void run() {
        // Define the graph as a weighted adjacency list
        Map<String, List<Edge>> graph = new HashMap<>();
        graph.put("A", Arrays.asList(new Edge("B", 2), new Edge("C", 3)));
        graph.put("B", Arrays.asList(new Edge("A", 2), new Edge("C", 1), new Edge("D", 4)));
        graph.put("C", Arrays.asList(new Edge("A", 3), new Edge("B", 1), new Edge("D", 5)));
        graph.put("D", Arrays.asList(new Edge("B", 4), new Edge("C", 5)));

        // Create a priority queue to hold nodes to be processed
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getDistance));

        // Create a dictionary to hold the shortest distance to each node
        Map<String, Integer> distances = new HashMap<>();
        for (String node : graph.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put("A", 0); // The distance to the starting node is 0

        // Create a dictionary to hold the previous node in the shortest path
        Map<String, String> previous = new HashMap<>();

        // Add the starting node to the queue
        queue.offer(new Node("A", 0));

        // While there are nodes in the queue
        while (!queue.isEmpty()) {
            // Get the node with the smallest distance
            Node node = queue.poll();
            int distance = node.getDistance();

            // If the distance is greater than the known distance, skip this node
            if (distance > distances.get(node.getName())) {
                continue;
            }

            // For each neighbor of the current node
            for (Edge edge : graph.get(node.getName())) {
                String neighbor = edge.getNeighbor();
                int weight = edge.getWeight();

                // Calculate the distance to the neighbor
                int newDistance = distance + weight;

                // If the new distance is smaller than the known distance, update it
                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, node.getName());

                    // Add the neighbor to the queue
                    queue.offer(new Node(neighbor, newDistance));
                }
            }
        }

        // Print the shortest distances
        for (String node : distances.keySet()) {
            System.out.println("Shortest distance from A to " + node + ": " + distances.get(node));
        }

        // Print the shortest path
        System.out.println("Shortest path from A to D:");
        String currentNode = "D";
        while (!currentNode.equals("A")) {
            System.out.print(currentNode + " ");
            currentNode = previous.get(currentNode);
        }
        System.out.println("A");
    }
}

class Edge {
    private String neighbor;
    private int weight;

    public Edge(String neighbor, int weight) {
        this.neighbor = neighbor;
        this.weight = weight;
    }

    public String getNeighbor() {
        return neighbor;
    }

    public int getWeight() {