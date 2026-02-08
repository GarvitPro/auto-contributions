// Learning Objective:
// This tutorial teaches you how to process and analyze a collection of data in Java
// using the powerful Stream API. You will learn to filter data based on specific criteria
// and perform basic aggregations like calculating an average, making your code
// more concise, readable, and efficient for common data manipulation tasks.
// We will focus on understanding WHAT each step does and WHY it's used.

// Modern Java feature: A 'record' is a concise way to declare immutable data classes.
// It automatically provides a constructor, accessor methods (getters), equals(), hashCode(),
// and toString() methods, reducing boilerplate code.
// We use 'Sale' to represent an item sold, with its name and price.
record Sale(String itemName, double price) {
    // No custom code needed here for this example, the record does it all for us!
}

public class DataAnalyzerTutorial {

    public static void main(String[] args) {

        // --- 1. Setting Up Our Sample Data ---
        // For learning purposes, we'll create a list of 'Sale' objects directly.
        // In a real application, this data might come from a database, file, or network.
        System.out.println("--- Sample Data ---");
        var salesData = java.util.List.of(
                new Sale("Laptop", 1200.00),
                new Sale("Mouse", 25.50),
                new Sale("Keyboard", 75.00),
                new Sale("Monitor", 300.00),
                new Sale("Webcam", 50.00),
                new Sale("Laptop Bag", 40.00),
                new Sale("External SSD", 150.00),
                new Sale("Headphones", 90.00),
                new Sale("Gaming Mouse", 60.00),
                new Sale("Desk Lamp", 35.00)
        );

        // Print all sales to see what we're starting with.
        // We use a forEach loop which is a simple way to iterate over collections.
        salesData.forEach(sale -> System.out.println("  " + sale));
        System.out.println("\n"); // Add a newline for better readability between sections.


        // --- 2. Filtering Data: Finding Specific Information ---
        // Objective: Find all sales that are considered 'high value' (e.g., price > $100).
        // This is a common data analysis task: isolating subsets of data that meet criteria.

        double highValueThreshold = 100.00;
        System.out.println("--- Filtering: High-Value Sales (price > $" + highValueThreshold + ") ---");

        // WHAT: `salesData.stream()` converts our List into a Stream.
        // WHY: Streams provide a sequence of elements that can be processed in a declarative way,
        //      allowing for powerful operations like filtering, mapping, and reducing.
        var highValueSales = salesData.stream()
                // WHAT: `filter(sale -> sale.price() > highValueThreshold)` is an intermediate operation.
                // WHY: It keeps only the elements (Sale objects) from the stream that satisfy the given
                //      predicate (the condition `sale.price() > highValueThreshold`).
                .filter(sale -> sale.price() > highValueThreshold)
                // WHAT: `collect(java.util.stream.Collectors.toList())` is a terminal operation.
                // WHY: It gathers the elements from the stream into a new List.
                //      Without a terminal operation, the intermediate operations (like filter) wouldn't execute.
                .collect(java.util.stream.Collectors.toList());

        // Print the filtered high-value sales.
        System.out.println("  Found " + highValueSales.size() + " high-value sales:");
        highValueSales.forEach(sale -> System.out.println("    " + sale));
        System.out.println("\n");


        // --- 3. Aggregating Data: Calculating an Overall Average ---
        // Objective: Calculate the average price of ALL sales.
        // Aggregation involves summarizing data (sum, count, average, min, max, etc.).

        System.out.println("--- Aggregation: Overall Average Sale Price ---");

        // WHAT: `salesData.stream()` again creates a stream.
        var overallAveragePrice = salesData.stream()
                // WHAT: `mapToDouble(Sale::price)` is an intermediate operation.
                // WHY: It transforms each 'Sale' object in the stream into a 'double'
                //      (specifically, its price). `Sale::price` is a method reference,
                //      a shorthand for `sale -> sale.price()`.
                //      `mapToDouble` is used because we're interested in numerical calculations
                //      and it provides specific operations like `average()`.
                .mapToDouble(Sale::price)
                // WHAT: `average()` is a terminal operation on a DoubleStream.
                // WHY: It calculates the arithmetic mean of the 'double' values in the stream.
                //      It returns an `OptionalDouble` because the stream might be empty,
                //      in which case an average cannot be calculated.
                .average();

        // WHAT: `overallAveragePrice.ifPresentOrElse(...)`
        // WHY: `OptionalDouble` is a container that may or may not contain a `double` value.
        //      `ifPresentOrElse` is a safe and modern way to handle this:
        //      - If a value is present (the stream was not empty), it executes the first lambda.
        //      - If no value is present (the stream was empty), it executes the second lambda.
        overallAveragePrice.ifPresentOrElse(
                avg -> System.out.printf("  Overall average price: $%.2f%n", avg),
                () -> System.out.println("  Could not calculate overall average (no sales data).")
        );
        System.out.println("\n");


        // --- 4. Combining Filtering and Aggregation ---
        // Objective: Calculate the average price of ONLY the high-value sales.
        // This is a very common scenario in data analysis: compute metrics on filtered subsets.

        System.out.println("--- Combined: Average Price of High-Value Sales ---");

        var averageHighValuePrice = salesData.stream()
                // First, filter for high-value sales (price > $100).
                .filter(sale -> sale.price() > highValueThreshold)
                // Then, map these filtered sales to their prices (doubles).
                .mapToDouble(Sale::price)
                // Finally, calculate the average of these specific prices.
                .average();

        averageHighValuePrice.ifPresentOrElse(
                avg -> System.out.printf("  Average price of high-value sales: $%.2f%n", avg),
                () -> System.out.println("  Could not calculate average of high-value sales (no such sales found).")
        );
        System.out.println("\n");


        // --- 5. Robustness: Handling Empty Data Sets ---
        // It's crucial to write code that gracefully handles situations where no data exists.
        // The `Optional` type (like `OptionalDouble` here) helps us do this without `null` checks.

        System.out.println("--- Robustness: Handling Empty Data ---");
        var emptySalesData = java.util.List.<Sale>of(); // An empty list of sales.

        // Attempt to calculate the average price from an empty list.
        var averageFromEmptyList = emptySalesData.stream()
                .mapToDouble(Sale::price)
                .average();

        System.out.println("  Attempting to calculate average from an empty list:");
        averageFromEmptyList.ifPresentOrElse(
                avg -> System.out.printf("  Average: $%.2f%n", avg),
                () -> System.out.println("  Correctly handled: No average could be calculated from an empty list.")
        );
        System.out.println("\n");

        System.out.println("--- Tutorial Complete! ---");
        System.out.println("You've learned to filter and aggregate data using Java Streams. " +
                           "This foundation is key for more complex data analysis tasks.");
    }
}