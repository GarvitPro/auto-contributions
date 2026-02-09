// String Hashing in Java

import java.util.HashMap;
import java.util.Map;

public class StringHashing {

    // Define the size of the hash table
    private static final int TABLE_SIZE = 1017;

    // Create a simple hash function
    private static int hash(String key) {
        // Use the ASCII values of characters to calculate the hash value
        int hashValue = 0;
        for (int i = 0; i < key.length(); i++) {
            // Multiply the current hash value by 31 and add the ASCII value of the character
            hashValue = (hashValue * 31) + key.charAt(i);
        }
        return Math.abs(hashValue) % TABLE_SIZE;
    }

    public static void main(String[] args) {
        Map<Integer, String> hashTable = new HashMap<>();
        String[] keys = {"apple", "banana", "cherry", "date", "elderberry"};

        for (String key : keys) {
            int index = hash(key);
            while (hashTable.containsKey(index)) {
                // If the slot is occupied, find the next available one
                index = (index + 1) % TABLE_SIZE;
            }
            hashTable.put(index, key);
        }

        System.out.println("Hash Table:");
        for (Map.Entry<Integer, String> entry : hashTable.entrySet()) {
            System.out.println("Index: " + entry.getKey() + ", Key: " + entry.getValue());
        }

        // Test the hash table
        int index = hash("apple");
        while (hashTable.containsKey(index)) {
            // If the slot is occupied, find the next available one
            index = (index + 1) % TABLE_SIZE;
        }
        System.out.println("Hash of 'apple': " + index);
    }
}