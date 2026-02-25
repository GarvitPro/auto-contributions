// String Hashing in Java
// This program demonstrates the basic principles of string hashing.
// It covers hashing algorithms, collisions, and resolving them.

public class StringHashing {
    // Hash function to calculate the hash value of a string
    public static int hashFunction(String str) {
        int hashValue = 0;
        for (int i = 0; i < str.length(); i++) {
            // Convert each character to its ASCII value
            char c = str.charAt(i);
            // Calculate the hash value by multiplying the current hash value by 31 and adding the ASCII value of the character
            hashValue = hashValue * 31 + c;
        }
        return hashValue;
    }

    // Method to check if two strings have the same hash value
    public static boolean sameHash(String str1, String str2) {
        // Calculate the hash values of both strings
        int hash1 = hashFunction(str1);
        int hash2 = hashFunction(str2);
        // Check if both hash values are equal
        return hash1 == hash2;
    }

    // Method to demonstrate collisions
    public static void demonstrateCollisions() {
        String str1 = "hello";
        String str2 = "world";
        String str3 = "hello"; // Colliding string
        // Calculate the hash values of all strings
        int hash1 = hashFunction(str1);
        int hash2 = hashFunction(str2);
        int hash3 = hashFunction(str3);
        // Print the results
        System.out.println("Hash of " + str1 + ": " + hash1);
        System.out.println("Hash of " + str2 + ": " + hash2);
        System.out.println("Hash of " + str3 + ": " + hash3);
        // Check if all strings have the same hash value
        System.out.println("Are " + str1 + ", " + str2 + ", and " + str3 + " colliding? " + sameHash(str1, str2) && sameHash(str1, str3) && sameHash(str2, str3));
    }

    // Main method to test the program
    public static void main(String[] args) {
        // Test the program
        System.out.println("Testing the hash function...");
        String str1 = "hello";
        String str2 = "world";
        String str3 = "hello";
        int hash1 = hashFunction(str1);
        int hash2 = hashFunction(str2);
        int hash3 = hashFunction(str3);
        System.out.println("Hash of " + str1 + ": " + hash1);
        System.out.println("Hash of " + str2 + ": " + hash2);
        System.out.println("Hash of " + str3 + ": " + hash3);
        System.out.println("Are " + str1 + ", " + str2 + ", and " + str3 + " colliding? " + sameHash(str1, str2) && sameHash(str1, str3) && sameHash(str2, str3));
        // Demonstrate collisions
        System.out.println("\nDemonstrating collisions...");
        demonstrateCollisions();
    }
}