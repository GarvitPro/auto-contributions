public class DynamicProgrammingMemoization {
    // Function to calculate the minimum number of operations required to transform "word1" into "word2"
    public static int minOperations(String word1, String word2) {
        int[][] memo = new int[word1.length() + 1][word2.length() + 1];
        
        // Initialize base case: transforming an empty string requires no operations
        for (int i = 0; i <= word1.length(); i++) {
            memo[i][0] = i;
        }
        for (int j = 0; j <= word2.length(); j++) {
            memo[0][j] = j;
        }

        // Fill in the rest of the memoization table
        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // If the current characters match, no operation is needed
                    memo[i][j] = memo[i - 1][j - 1];
                } else {
                    // Otherwise, consider three operations: insertion, deletion, and substitution
                    int insert = memo[i][j - 1] + 1;
                    int delete = memo[i - 1][j] + 1;
                    int substitute = memo[i - 1][j - 1] + 1;
                    memo[i][j] = Math.min(insert, Math.min(delete, substitute));
                }
            }
        }

        // The minimum number of operations is stored in the bottom-right corner of the memoization table
        return memo[word1.length()][word2.length()];
    }

    public static void main(String[] args) {
        System.out.println(minOperations("horse", "ros"));  // Output: 3
    }
}