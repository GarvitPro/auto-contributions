// Learning Objective: This tutorial demonstrates Conway's Game of Life simulation
// to teach fundamental concepts of 2D arrays in Java, including accessing elements,
// iterating through rows and columns, and applying conditional logic.
// It also introduces basic object-oriented principles by representing the game grid
// as an object.

import java.util.Arrays; // Importing Arrays utility for easier printing of the grid.
import java.util.Random; // For generating random initial states for the grid.

public class GameOfLife {

    // This constant defines the size of our square game grid.
    // We're using a constant so it's easy to change the grid size later.
    private static final int GRID_SIZE = 10;

    // A 2D array to represent the game grid.
    // 'true' represents a living cell, 'false' represents a dead cell.
    // Think of this as a grid of squares, where each square can be either alive or dead.
    private boolean[][] grid;

    // Constructor: Initializes the Game of Life simulation.
    public GameOfLife() {
        // We create a new grid of the specified size.
        // The grid is initialized with all 'false' (dead) cells by default.
        grid = new boolean[GRID_SIZE][GRID_SIZE];
    }

    // Method to randomly initialize the grid.
    // This gives us a starting point for the simulation.
    public void randomizeGrid() {
        Random random = new Random(); // An object to generate random numbers.
        for (int row = 0; row < GRID_SIZE; row++) { // Loop through each row of the grid.
            for (int col = 0; col < GRID_SIZE; col++) { // Loop through each column in the current row.
                // For each cell, randomly decide if it should be alive or dead.
                // random.nextBoolean() returns true or false with roughly 50% probability.
                grid[row][col] = random.nextBoolean();
            }
        }
    }

    // Method to count the number of living neighbors for a given cell.
    // This is the core logic of Conway's Game of Life rules.
    private int countLiveNeighbors(int row, int col) {
        int liveNeighbors = 0; // Initialize the count for this cell.

        // We check all 8 surrounding cells (including diagonals).
        // The 'dr' and 'dc' arrays represent the relative changes in row and column
        // to get to each neighbor.
        // Example: dr[0] = -1, dc[0] = -1 means move one row up and one column left.
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) { // Iterate through all 8 possible neighbor directions.
            int neighborRow = row + dr[i]; // Calculate the row of the potential neighbor.
            int neighborCol = col + dc[i]; // Calculate the column of the potential neighbor.

            // IMPORTANT: Boundary Check! We must ensure the neighbor is within the grid.
            // If neighborRow or neighborCol are outside the valid range (0 to GRID_SIZE-1),
            // we skip this neighbor to avoid errors.
            if (neighborRow >= 0 && neighborRow < GRID_SIZE && neighborCol >= 0 && neighborCol < GRID_SIZE) {
                // If the neighbor is within bounds AND it's alive (true), increment the count.
                if (grid[neighborRow][neighborCol]) {
                    liveNeighbors++;
                }
            }
        }
        return liveNeighbors; // Return the total count of living neighbors.
    }

    // Method to update the grid to the next generation based on Game of Life rules.
    public void updateGrid() {
        // We need a new grid to store the next state because the rules depend on the CURRENT state.
        // If we updated the 'grid' directly, later calculations for other cells would use the new,
        // partially updated state, which is incorrect.
        boolean[][] nextGrid = new boolean[GRID_SIZE][GRID_SIZE];

        for (int row = 0; row < GRID_SIZE; row++) { // Iterate through each cell in the current grid.
            for (int col = 0; col < GRID_SIZE; col++) {

                int liveNeighbors = countLiveNeighbors(row, col); // Get the neighbor count for this cell.
                boolean isAlive = grid[row][col]; // Check if the current cell is alive.

                // Apply Conway's Game of Life rules:
                // 1. Any live cell with fewer than two live neighbours dies (underpopulation).
                // 2. Any live cell with two or three live neighbours lives on to the next generation.
                // 3. Any live cell with more than three live neighbours dies (overpopulation).
                // 4. Any dead cell with exactly three live neighbours becomes a live cell (reproduction).

                if (isAlive) { // If the current cell is alive...
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        nextGrid[row][col] = false; // ...it dies.
                    } else {
                        nextGrid[row][col] = true; // ...it lives on.
                    }
                } else { // If the current cell is dead...
                    if (liveNeighbors == 3) {
                        nextGrid[row][col] = true; // ...it becomes alive.
                    } else {
                        nextGrid[row][col] = false; // ...it remains dead.
                    }
                }
            }
        }
        // After calculating the next state for all cells, we replace the old grid with the new one.
        this.grid = nextGrid;
    }

    // Method to print the current state of the grid to the console.
    // 'X' for alive, '.' for dead.
    public void printGrid() {
        System.out.println("--------------------"); // A visual separator for each generation.
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                // Use a ternary operator (a shorthand if-else) for conciseness.
                // If grid[row][col] is true, print 'X', otherwise print '.'.
                System.out.print(grid[row][col] ? "X " : ". ");
            }
            System.out.println(); // Move to the next line after printing a row.
        }
        System.out.println("--------------------");
    }

    // Main method: The entry point of our program.
    public static void main(String[] args) {
        // Create an instance (an object) of our GameOfLife simulation.
        GameOfLife game = new GameOfLife();

        // Initialize the grid with random live and dead cells.
        game.randomizeGrid();
        System.out.println("Initial Grid:");
        game.printGrid(); // Display the initial state.

        // Simulate for a few generations to see how the pattern evolves.
        int numberOfGenerations = 5;
        for (int i = 0; i < numberOfGenerations; i++) {
            game.updateGrid(); // Calculate the next state of the grid.
            System.out.println("Generation " + (i + 1) + ":");
            game.printGrid(); // Display the grid after the update.
        }
    }
}