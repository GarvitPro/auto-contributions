import java.util.*;

public class Dijkstra {
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        // Create a graph with the following edges and weights:
        // 0 -> 1: 4, 0 -> 2: 2, 0 -> 3: 9,
        // 1 -> 2: 8, 1 -> 3: 7, 1 -> 4: 11,
        // 2 -> 4: 2, 2 -> 5: 6, 2 -> 6: 14,
        // 3 -> 5: 1, 3 -> 6: 10
        Map<Integer, List<Edge>> graph = new HashMap<>();
        graph.put(0, Arrays.asList(
                new Edge(1, 4),
                new Edge(2, 2),
                new Edge(3, 9)
        ));
        graph.get(0).add(new Edge(1, 8));
        graph.get(0).add(new Edge(2, 7));
        graph.get(0).add(new Edge(3, 11));

        graph.put(1, Arrays.asList(
                new Edge(2, 11),
                new Edge(3, 7),
                new Edge(4, 8)
        ));
        graph.get(1).add(new Edge(0, 4));
        graph.get(1).add(new Edge(2, 6));

        graph.put(2, Arrays.asList(
                new Edge(4, 2),
                new Edge(5, 6),
                new Edge(6, 14)
        ));
        graph.get(2).add(new Edge(0, 2));
        graph.get(2).add(new Edge(1, 11));

        graph.put(3, Arrays.asList(
                new Edge(5, 1),
                new Edge(6, 10)
        ));
        graph.get(3).add(new Edge(0, 9));
        graph.get(3).add(new Edge(1, 7));

        graph.put(4, Collections.singletonList(new Edge(2, 11)));
        graph.get(4).add(new Edge(1, 8));

        graph.put(5, Arrays.asList(
                new Edge(6, 10)
        ));
        graph.get(5).add(new Edge(3, 1));
        graph.get(5).add(new Edge(2, 6));

        graph.put(6, Collections.singletonList(new Edge(5, 10)));
        graph.get(6).add(new Edge(3, 10));
        graph.get(6).add(new Edge(2, 14));

        // Use Dijkstra's algorithm to find the shortest path
        Map<Integer, Integer> shortestDistances = dijkstra(graph, 0);
        System.out.println("Shortest Distances:");
        for (Map.Entry<Integer, Integer> entry : shortestDistances.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Find the shortest path
        List<Integer> shortestPath = shortestPath(graph, 0);
        System.out.println("\nShortest Path:");
        System.out.print(shortestPath.get(0));
        for (int i = 1; i < shortestPath.size();