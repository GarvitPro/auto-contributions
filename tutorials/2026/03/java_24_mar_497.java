public class StringHashing {

    public static void main(String[] args) {
        System.out.println("String Hashing Example");
        System.out.println("---------------------");

        // Create a string to be hashed
        String str = "Hello World";

        // Calculate the hash code using the built-in hashCode() method
        int hashCode = str.hashCode();
        System.out.println("Built-in Hash Code: " + hashCode);

        // Create a custom hash function (Ripple Hash Function)
        System.out.println("\nCustom Ripple Hash Function:");
        String customHashedStr = rippledHash(str);
        System.out.println("Custom Hashed String: " + customHashedStr);
    }

    /**
     * Custom ripple hash function.
     *
     * @param str the input string
     * @return the hashed string
     */
    public static String rippledHash(String str) {
        int length = str.length();
        StringBuilder sb = new StringBuilder();

        // First pass: extract characters and update prefix sum
        for (int i = 0; i < length / 2; i++) {
            char c = str.charAt(i);
            char d = str.charAt(length - i - 1); // Reverse the string for second pass

            // Calculate ASCII values and add to prefix sum
            sb.append((char) ((c * 31 + 'a') % 256)); // Add a constant value for non-alphabet characters
        }

        // Second pass: combine prefix sums of both strings
        StringBuilder combinedHash = new StringBuilder();
        int prefixSum = 0;
        for (int i = 0; i < length / 2; i++) {
            char c = str.charAt(i);
            char d = str.charAt(length - i - 1); // Reverse the string

            // Calculate ASCII values and add to combined hash
            prefixSum += ((c * 31 + 'a') % 256) + ((d * 31 + 'a') % 256);
        }

        // Combine both strings into final hashed result
        return sb.toString() + Integer.toString(prefixSum);
    }
}