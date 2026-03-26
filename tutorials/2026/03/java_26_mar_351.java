// Importing the HashMap class from the java.util package
import java.util.HashMap;
import // Importing the Scanner class for user input
import java.util.Scanner;

public class HashMapExample {
    public static void main(String[] args) {
        // Create a new HashMap object
        HashMap<String, Integer> map = new HashMap<>();

        // Add key-value pairs to the map
        // The put() method is used to add or update an existing entry in the map
        map.put("Apple", 10);
        map.put("Banana", 20);
        map.put("Cherry", 30);

        // Display the contents of the map
        System.out.println("Initial Map Contents:");
        for (String key : map.keySet()) {
            System.out.println(key + ": " + map.get(key));
        }

        // Retrieve a value from the map by its key
        String fruit = "Apple";
        int quantity = map.get(fruit);

        // Update a value in the map
        map.put("Banana", 25);
        System.out.println("\nUpdated Banana Quantity: " + map.get("Banana"));

        // Remove an entry from the map
        map.remove("Cherry");
        System.out.println("\nAfter Removing Cherry: " + map);

        // Check if a key exists in the map
        boolean containsKey = map.containsKey("Orange");
        System.out.println("\nDoes map contain Orange? " + containsKey);

        // Clear the entire map
        map.clear();
        System.out.println("\nMap after clearing: " + map);
    }
}