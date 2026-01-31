// Learning Objective:
// This tutorial demonstrates how to build a very basic Object-Relational Mapper (ORM)
// in Java. You will learn to use JDBC for database interaction and Java Reflection to
// dynamically inspect Java objects and map their fields to database table columns,
// focusing on the 'persist' (save) operation for new objects.

// This example will use an in-memory H2 database for convenience, but the core
// ORM logic is applicable to any relational database.

// --- Step 1: Define Custom Annotations ---
// We need annotations to mark our Java objects and their fields so our ORM knows
// how to map them to database entities and columns. These are simpler versions
// of annotations you'd find in frameworks like JPA.

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Marks a class as an entity that should be persisted to the database.
 * The table name will default to the class name (converted to snake_case or lowercase for simplicity).
 */
@Retention(RetentionPolicy.RUNTIME) // This annotation needs to be available at runtime for reflection.
@Target(ElementType.TYPE)        // This annotation can only be applied to classes/interfaces.
@interface MyEntity {
    // We could add 'tableName()' here, but for simplicity, we'll derive it from the class name.
}

/**
 * Marks a field as the primary key of an entity.
 * Assumes auto-incrementing primary keys for this basic example.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) // This annotation can only be applied to fields.
@interface MyId {
}

/**
 * Marks a field as a column in the database table.
 * For simplicity, the column name will default to the field name.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) // This annotation can only be applied to fields.
@interface MyColumn {
    // We could add 'name()' here for custom column names, but we'll use field names for simplicity.
}


// --- Step 2: Create the Simple ORM (EntityManager) ---
// This class will contain the core logic for mapping Java objects to database operations.
// For this tutorial, we will focus solely on the 'persist' (INSERT) operation.

class SimpleEntityManager {
    private final Connection connection; // The JDBC connection to the database.

    /**
     * Constructor for our SimpleEntityManager.
     *
     * @param connection An active JDBC database connection.
     */
    public SimpleEntityManager(Connection connection) {
        this.connection = connection;
    }

    /**
     * Persists a new Java object (entity) to the database.
     * This method inspects the object using reflection, builds an INSERT statement,
     * executes it, and then sets the auto-generated primary key back into the object.
     *
     * @param entity The Java object to persist. It must be annotated with @MyEntity.
     * @throws SQLException If a database access error occurs.
     * @throws ReflectiveOperationException If reflection fails (e.g., field not accessible).
     * @throws IllegalArgumentException If the entity is not correctly annotated.
     */
    public void persist(Object entity) throws SQLException, ReflectiveOperationException {
        // 1. Validate the entity class using reflection.
        // Get the runtime Class object of the entity.
        Class<?> entityClass = entity.getClass();

        // Check if the class is annotated with @MyEntity. If not, it's not a valid entity for our ORM.
        if (!entityClass.isAnnotationPresent(MyEntity.class)) {
            throw new IllegalArgumentException("Class " + entityClass.getName() + " is not an @MyEntity.");
        }

        // Determine the table name from the class name (simple conversion to lowercase).
        var tableName = entityClass.getSimpleName().toLowerCase();

        // 2. Collect field information using reflection.
        var columns = new StringJoiner(", "); // Used to build the column names part of the SQL.
        var placeholders = new StringJoiner(", "); // Used to build the '?' placeholders part of the SQL.
        var fieldValues = new ArrayList<Object>(); // Stores the values of the fields to be set in the PreparedStatement.
        Field idField = null; // To store the primary key field, so we can set its generated value later.

        // Iterate over all declared fields in the entity class.
        for (Field field : entityClass.getDeclaredFields()) {
            // Make the field accessible, even if it's private. This is crucial for reflection.
            field.setAccessible(true);

            // Check if the field is the primary key.
            if (field.isAnnotationPresent(MyId.class)) {
                idField = field; // Store this field.
            }

            // Check if the field is a regular column.
            if (field.isAnnotationPresent(MyColumn.class) || field.isAnnotationPresent(MyId.class)) {
                // For simplicity, column name is the field name (could be customized via annotation).
                columns.add(field.getName().toLowerCase());
                placeholders.add("?");
                // Get the value of the field from the entity object.
                fieldValues.add(field.get(entity));
            }
        }

        // Basic validation: ensure an ID field was found.
        if (idField == null) {
            throw new IllegalArgumentException("Entity " + entityClass.getName() + " must have an @MyId field.");
        }

        // 3. Construct the SQL INSERT statement.
        // Example: INSERT INTO product (id, name, price) VALUES (?, ?, ?)
        var sql = String.format("INSERT INTO %s (%s) VALUES (%s)",
                tableName, columns.toString(), placeholders.toString());
        System.out.println("Executing SQL: " + sql); // For debugging/learning purposes.

        // 4. Execute the INSERT statement using JDBC PreparedStatement.
        // Use try-with-resources to ensure PreparedStatement and ResultSet are closed automatically.
        // Statement.RETURN_GENERATED_KEYS is vital for retrieving auto-incremented IDs.
        try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Set the parameters for the prepared statement.
            for (int i = 0; i < fieldValues.size(); i++) {
                statement.setObject(i + 1, fieldValues.get(i)); // JDBC parameters are 1-indexed.
            }

            // Execute the update (INSERT operation).
            var affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating entity failed, no rows affected.");
            }

            // 5. Retrieve and set the auto-generated primary key.
            // Get the keys that the database generated.
            try (var generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // Assuming the primary key is the first (and only) generated key.
                    // Get the generated ID as an Integer (adjust type if your PK is long, UUID etc.).
                    var generatedId = generatedKeys.getInt(1);
                    // Use reflection to set the generated ID back into the entity object.
                    idField.setAccessible(true); // Ensure it's accessible again if needed.
                    idField.set(entity, generatedId);
                } else {
                    throw new SQLException("Creating entity failed, no ID obtained.");
                }
            }
        }
    }
}


// --- Step 3: Define an Example Entity (POJO) ---
// This is a plain old Java object (POJO) that our ORM will interact with.
// It uses our custom annotations to define its mapping to a database table.

class Product {
    @MyId         // Marks this field as the primary key.
    @MyColumn     // Also a column.
    private Integer id; // Integer for nullable primary key, will be auto-generated.

    @MyColumn     // Marks this field as a regular column.
    private String name;

    @MyColumn     // Marks this field as a regular column.
    private Double price;

    // Constructor for creating new products without an ID (ID will be generated by the DB).
    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    // Getters and Setters (essential for ORMs, as they often use them, or direct field access as we do here).
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; } // ORM will use this (or reflection) to set ID.
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    @Override
    public String toString() {
        return "Product{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", price=" + price +
               '}';
    }
}


// --- Step 4: Example Usage ---
// Demonstrates how to set up the database and use our SimpleEntityManager.

public class OrmTutorial {

    // Helper method to initialize the H2 database and create the product table.
    private static Connection initializeDatabase() throws SQLException {
        // Use an in-memory H2 database for easy setup and teardown.
        // "jdbc:h2:mem:testdb" creates a database in memory named 'testdb'.
        // "DB_CLOSE_DELAY=-1" keeps the database open as long as the JVM is running.
        var connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
        System.out.println("Database connection established.");

        // Create the 'product' table. This would typically be part of a schema migration tool.
        // For this simple tutorial, we hardcode the DDL.
        // Note: The column names 'id', 'name', 'price' match our field names (case-insensitive in H2).
        var createTableSql = "CREATE TABLE product (" +
                             "id INT AUTO_INCREMENT PRIMARY KEY," +
                             "name VARCHAR(255) NOT NULL," +
                             "price DECIMAL(10, 2) NOT NULL" +
                             ")";
        try (var statement = connection.createStatement()) {
            statement.execute(createTableSql);
            System.out.println("Table 'product' created.");
        }
        return connection;
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Initialize the database and get a connection.
            connection = initializeDatabase();

            // Instantiate our simple ORM.
            var entityManager = new SimpleEntityManager(connection);

            // Create a new Product object.
            var product1 = new Product("Laptop", 1200.50);
            System.out.println("Before persist: " + product1);

            // Persist the product using our ORM.
            entityManager.persist(product1);
            System.out.println("After persist (ID should be set): " + product1);

            System.out.println("\n--- Persisting another product ---");
            var product2 = new Product("Mouse", 25.99);
            System.out.println("Before persist: " + product2);
            entityManager.persist(product2);
            System.out.println("After persist (ID should be set): " + product2);

        } catch (SQLException | ReflectiveOperationException e) {
            // Catch and print any exceptions that occur during DB operations or reflection.
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Always close the database connection to release resources.
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Database connection closed.");
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }
}