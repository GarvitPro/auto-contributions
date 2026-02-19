// This Java file teaches dynamic programming memoization.
// Dynamic programming is a method for solving complex problems by breaking them down into smaller subproblems,
// solving each subproblem only once, and storing their solutions to avoid redundant computation.

public class Memoization {

    // Function to calculate the nth Fibonacci number using memoization
    public static int fibonacci(int n) {
        // Create an array to store the Fibonacci numbers for memoization
        int[] memo = new int[n + 1];
        
        // Base cases: F(0) = 0, F(1) = 1
        memo[0] = 0;
        memo[1] = 1;

        // Calculate Fibonacci numbers from F(2) to F(n)
        for (int i = 2; i <= n; i++) {
            // Use previous two Fibonacci numbers to calculate the next one
            memo[i] = memo[i - 1] + memo[i - 2];
        }

        // Return the nth Fibonacci number
        return memo[n];
    }

    public static void main(String[] args) {
        // Test the fibonacci function with different inputs
        System.out.println("Fibonacci numbers:");
        for (int i = 0; i <= 10; i++) {
            System.out.print(fibonacci(i) + " ");
        }
    }
}