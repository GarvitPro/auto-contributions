// String Hashing in Java
// This program demonstrates how to calculate the hash value of a string using different methods.

public class StringHashing {

    // Method to calculate the ASCII sum of a string
    public static int asciiSum(String str) {
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            sum += str.charAt(i);
        }
        return sum;
    }

    // Method to calculate the hash value using the ASCII sum method
    public static int asciiHash(String str) {
        return asciiSum(str);
    }

    // Method to calculate the hash value using the XOR operation
    public static int xorHash(String str) {
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash ^= str.charAt(i);
        }
        return hash;
    }

    // Method to calculate the hash value using a prime number
    public static int primeHash(int prime, String str) {
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash * prime + str.charAt(i)) % (prime * str.length());
        }
        return hash;
    }

    // Method to calculate the hash value using a rolling hash
    public static int rollingHash(int p, String str) {
        int pow = 1;
        for (int i = 0; i < str.length(); i++) {
            pow = (pow * p) % (p * str.length());
        }
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash += str.charAt(i) * pow;
            pow = (pow * p) % (p * str.length());
        }
        return hash;
    }

    public static void main(String[] args) {
        String str = "Hello, World!";
        System.out.println("ASCII Sum: " + asciiSum(str));
        System.out.println("ASCII Hash: " + asciiHash(str));
        System.out.println("XOR Hash: " + xorHash(str));
        int prime = 31;
        System.out.println("Prime Hash: " + primeHash(prime, str));
        int p = 37;
        System.out.println("Rolling Hash: " + rollingHash(p, str));
    }
}