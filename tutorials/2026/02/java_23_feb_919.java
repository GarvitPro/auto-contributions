// String Hashing in Java
//=================================
public class StringHashing {
    // Step 1: Define a class to calculate the hash value of a string
    public static class HashCalculator {
        // Step 2: Calculate the hash value using the formula: hash = prime * (a + b + c + ... + n)
        // where prime is a large prime number, and a, b, c, ... , n are characters in the string
        public int calculateHash(String str) {
            int hashValue = 0; // Initialize hash value
            int prime = 31; // Choose a large prime number

            // Convert the string to lowercase for simplicity
            str = str.toLowerCase();

            // Iterate over each character in the string
            for (int i = 0; i < str.length(); i++) {
                // Convert the character to its ASCII value
                int asciiValue = str.charAt(i);
                // Calculate the weighted sum of the ASCII value with the prime number
                int weightedSum = asciiValue * prime;
                // Add the weighted sum to the hash value
                hashValue += weightedSum;
            }

            // Return the calculated hash value
            return hashValue;
        }
    }

    public static void main(String[] args) {
        // Create an instance of the HashCalculator class
        HashCalculator calculator = new HashCalculator();

        // Test the calculateHash method with a sample string
        String str = "HelloWorld";
        int hashValue = calculator.calculateHash(str);

        // Print the calculated hash value
        System.out.println("Hash Value for \"" + str + "\": " + hashValue);
    }
}