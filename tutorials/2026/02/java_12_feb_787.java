import java.util.HashMap;
import java.util.Map;

public class HashMapTutorial {
    public static void main(String[] args) {
        // Create an empty HashMap
        Map<String, Integer> hashMap = new HashMap<>();

        // Add elements to the HashMap
        // In Java, HashMap is a key-value store. The key is unique and cannot be null.
        // The value can be any type of object.

        // Inserting data into the HashMap
        // We are storing names as keys and their corresponding ages as values.
        hashMap.put("John", 25);
        hashMap.put("Alice", 30);
        hashMap.put("Bob", 35);

        // Displaying elements in the HashMap
        System.out.println("HashMap Contents:");
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Update a value in the HashMap
        // You can update an existing key-value pair by putting a new value for that key.
        hashMap.put("John", 26);

        // Remove an element from the HashMap
        // You can remove an existing key-value pair using the remove() method.
        hashMap.remove("Alice");

        // Check if a key exists in the HashMap
        // Before removing or updating, it's good practice to check if the key exists.
        System.out.println("Does 'Bob' exist? " + hashMap.containsKey("Bob"));

        // Get the value for a specific key
        // You can get the value for a specific key using the get() method.
        System.out.println("John's age: " + hashMap.get("John"));

        // Put multiple elements into the HashMap at once
        // You can add multiple key-value pairs in one go using the putAll() method.
        Map<String, Integer> anotherMap = new HashMap<>();
        anotherMap.put("John", 26);
        anotherMap.put("Alice", 31);
        hashMap.putAll(anotherMap);

        System.out.println("Updated HashMap Contents:");
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Check if the HashMap is empty
        // You can check if the map is empty using the isEmpty() method.
        System.out.println("Is the HashMap empty? " + hashMap.isEmpty());

        // Clear all elements from the HashMap
        // You can clear all entries in the map by calling the clear() method.
        hashMap.clear();

        // Check if the HashMap is empty after clearing
        System.out.println("Is the HashMap empty now? " + hashMap.isEmpty());
    }
}