// String Hashing in Java using Chaining and Collisions Resolution

import java.util.HashSet;
import java.util.Set;

public class StringHashing {

    // Define a class to store the hash value and the string associated with it
    static class HashNode {
        int hashValue; 
        String str;
        HashNode next;

        public HashNode(int hashValue, String str) {
            this.hashValue = hashValue;
            this.str = str;
            this.next = null;
        }
    }

    // Function to calculate the hash value for a string
    static int calculateHash(String str) {
        int hashValue = 0;

        // Use the ASCII values of characters in the string
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // Multiply the current hash value with a prime number and add the ASCII value of character
            hashValue = (hashValue * 31) + c;
        }

        return Math.abs(hashValue); // Return absolute value to ensure hashing is consistent
    }

    // Function to handle collisions
    static HashNode resolveCollision(HashNode node, int hashValue) {
        if (node == null) {
            return new HashNode(hashValue, "");
        } else if (node.hashValue == hashValue) {
            return new HashNode(hashValue, node.str);
        } else {
            return resolveCollision(node.next, hashValue);
        }
    }

    // Function to insert a string into the hash table
    static void insertIntoHashTable(HashTable hashTable, String str) {
        int hashValue = calculateHash(str);

        if (hashTable.get(hashValue) == null) {
            hashTable.set(hashValue, new HashNode(hashValue, str));
        } else {
            // Handle collision using resolveCollision function
            HashNode node = hashTable.get(hashValue);
            while (node.next != null) {
                int nextHashValue = calculateHash(str) + 1;
                if (node.next.hashValue == nextHashValue) {
                    break;
                }
                node = node.next;
            }

            // Insert the new string
            HashNode newNode = new HashNode(hashValue, str);
            while (node.next != null && node.next.hashValue == nextHashValue) {
                node = node.next;
            }
            newNode.next = node.next;
            node.next = newNode;

        }
    }

    // Function to display the contents of the hash table
    static void printHashTable(HashSet<HashNode> hashTable) {
        for (HashNode node : hashTable.values()) {
            while (node != null) {
                System.out.print("Hash Value: " + node.hashValue);
                if (node.str != null && !node.str.isEmpty())
                    System.out.println(" - String: " + node.str);

                // Traverse the linked list
                node = node.next;
            }
        }
    }

    public static void main(String[] args) {
        HashTable hashTable = new HashTable();

        // Insert some strings into the hash table
        insertIntoHashTable(hashTable, "Apple");
        insertIntoHashTable(hashTable, "Banana");
        insertIntoHashTable(hashTable, "Cherry");

        // Display the contents of the hash table
        printHashTable(hashTable);
    }
}

// Hash Table