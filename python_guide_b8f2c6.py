"""
Learning Objective:
This tutorial will teach you how to create evolving visual patterns in Python
using algorithmic generation and random numbers. We will focus on a simple
but powerful concept: "cellular automata" where the state of a "cell"
(a pixel or point in our pattern) changes based on its neighbors and a set of rules.
This is a fundamental technique used in computer graphics, simulations, and art.

We'll be using the `pygame` library for drawing, which is excellent for
interactive graphics and games. Make sure you have it installed:
`pip install pygame`
"""

import pygame
import random

# --- Configuration ---
# Define the dimensions of our display window.
SCREEN_WIDTH = 800
SCREEN_HEIGHT = 600
# Define the size of each "cell" in our simulation.
CELL_SIZE = 5
# Define colors using RGB tuples (Red, Green, Blue).
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
GREEN = (0, 255, 0)
BLUE = (0, 0, 255)
RED = (255, 0, 0)

# --- Cellular Automata Logic ---

class CellularAutomaton:
    """
    Represents a 2D cellular automaton grid.
    Each cell has a state (e.g., 0 for dead, 1 for alive).
    The state of cells evolves over time based on predefined rules.
    """
    def __init__(self, width, height, rule_set, initial_density=0.5):
        """
        Initializes the cellular automaton grid.

        Args:
            width (int): The width of the grid in cells.
            height (int): The height of the grid in cells.
            rule_set (dict): A dictionary defining the rules for cell evolution.
                             Keys are tuples representing the state of a cell and its neighbors.
                             Values are the resulting state of the cell.
            initial_density (float): The probability (0.0 to 1.0) that a cell starts as alive.
        """
        self.width = width
        self.height = height
        self.rule_set = rule_set
        # Initialize the grid with random states.
        self.grid = self._initialize_grid(initial_density)
        # Store the next state of the grid temporarily during updates.
        self.next_grid = [[0 for _ in range(width)] for _ in range(height)]

    def _initialize_grid(self, density):
        """
        Fills the grid with initial states, randomly setting cells to alive based on density.
        """
        grid = [[0 for _ in range(self.width)] for _ in range(self.height)]
        for r in range(self.height):
            for c in range(self.width):
                if random.random() < density:
                    grid[r][c] = 1  # 1 represents an "alive" cell.
        return grid

    def _get_neighbor_states(self, r, c):
        """
        Gets the states of the 8 neighbors surrounding a cell at (r, c).
        Handles boundary conditions by wrapping around the grid (toroidal).
        """
        neighbor_states = []
        # Iterate through relative positions of neighbors (-1, 0, 1 for row and column).
        for dr in [-1, 0, 1]:
            for dc in [-1, 0, 1]:
                # Skip the cell itself.
                if dr == 0 and dc == 0:
                    continue

                # Calculate the absolute row and column of the neighbor, wrapping around.
                # The modulo operator (%) ensures we stay within grid bounds.
                neighbor_r = (r + dr) % self.height
                neighbor_c = (c + dc) % self.width
                neighbor_states.append(self.grid[neighbor_r][neighbor_c])
        return tuple(neighbor_states) # Return as a tuple for easy lookup in the rule_set.

    def update(self):
        """
        Updates the state of every cell in the grid for the next generation.
        This is the core of the cellular automata simulation.
        """
        for r in range(self.height):
            for c in range(self.width):
                # Get the current state of the cell.
                current_state = self.grid[r][c]
                # Get the states of its neighbors.
                neighbor_states = self._get_neighbor_states(r, c)
                # Combine the current state with neighbor states to form the lookup key.
                # The order of states in the tuple is crucial and depends on how rule_set is defined.
                # Here, we assume the rule_set expects (current_cell_state, neighbor1_state, ..., neighbor8_state).
                lookup_key = (current_state,) + neighbor_states

                # Apply the rule from the rule_set.
                # If the rule for this combination exists, use its result; otherwise, the cell stays the same.
                self.next_grid[r][c] = self.rule_set.get(lookup_key, current_state)

        # After calculating all next states, replace the current grid with the next grid.
        self.grid = [row[:] for row in self.next_grid] # Create a deep copy to avoid aliasing issues.

    def get_grid(self):
        """
        Returns the current state of the grid.
        """
        return self.grid

# --- Drawing Functions ---

def draw_grid(screen, grid, cell_size):
    """
    Draws the cellular automaton grid onto the Pygame screen.
    Each cell is drawn as a rectangle.
    """
    # Iterate through each row and column of the grid.
    for r, row in enumerate(grid):
        for c, cell_state in enumerate(row):
            # Determine the color based on the cell's state.
            color = BLACK if cell_state == 0 else GREEN # 0 is dead (black), 1 is alive (green).

            # Calculate the pixel coordinates for the top-left corner of the cell rectangle.
            x_pos = c * cell_size
            y_pos = r * cell_size

            # Create a rectangle representing the cell.
            cell_rect = pygame.Rect(x_pos, y_pos, cell_size, cell_size)

            # Draw the rectangle onto the screen.
            pygame.draw.rect(screen, color, cell_rect)

# --- Example Usage ---

def main():
    """
    The main function to set up Pygame, create the cellular automaton,
    and run the simulation loop.
    """
    # Initialize Pygame.
    pygame.init()
    # Set up the display window.
    screen = pygame.display.set_mode((SCREEN_WIDTH, SCREEN_HEIGHT))
    # Set the window title.
    pygame.display.set_caption("Evolving Visual Patterns")
    # Create a clock object to control frame rate.
    clock = pygame.time.Clock()

    # --- Define a rule set for Conway's Game of Life ---
    # This is a famous example of a cellular automaton.
    # Rules are based on the current state of a cell and its 8 neighbors.
    # A cell is alive (1) if it has exactly 3 live neighbors.
    # A live cell with fewer than 2 live neighbors dies (underpopulation).
    # A live cell with more than 3 live neighbors dies (overpopulation).
    # A dead cell with exactly 3 live neighbors becomes alive (reproduction).
    # The structure: (current_state, n1, n2, n3, n4, n5, n6, n7, n8) -> next_state

    # Define the default rule for a dead cell (state 0)
    # If a dead cell has 3 live neighbors, it becomes alive.
    RULE_DEAD_TO_ALIVE = {
        (0, 0, 0, 0, 0, 0, 0, 0, 0): 0, # 0 neighbors -> stays dead
        (0, 1, 0, 0, 0, 0, 0, 0, 0): 0, # 1 neighbor -> stays dead
        (0, 1, 1, 0, 0, 0, 0, 0, 0): 1, # 2 neighbors -> becomes alive (reproduction rule)
        (0, 1, 1, 1, 0, 0, 0, 0, 0): 1, # 3 neighbors -> becomes alive (reproduction rule)
        (0, 1, 1, 1, 1, 0, 0, 0, 0): 0, # 4 neighbors -> dies
        (0, 1, 1, 1, 1, 1, 0, 0, 0): 0, # 5 neighbors -> dies
        (0, 1, 1, 1, 1, 1, 1, 0, 0): 0, # 6 neighbors -> dies
        (0, 1, 1, 1, 1, 1, 1, 1, 0): 0, # 7 neighbors -> dies
        (0, 1, 1, 1, 1, 1, 1, 1, 1): 0, # 8 neighbors -> dies
    }

    # Define the default rule for a live cell (state 1)
    # If a live cell has 2 or 3 live neighbors, it stays alive.
    # Otherwise, it dies.
    RULE_ALIVE_TO_ALIVE = {
        (1, 0, 0, 0, 0, 0, 0, 0, 0): 0, # 0 neighbors -> dies (underpopulation)
        (1, 1, 0, 0, 0, 0, 0, 0, 0): 0, # 1 neighbor -> dies (underpopulation)
        (1, 1, 1, 0, 0, 0, 0, 0, 0): 1, # 2 neighbors -> stays alive
        (1, 1, 1, 1, 0, 0, 0, 0, 0): 1, # 3 neighbors -> stays alive (survival rule)
        (1, 1, 1, 1, 1, 0, 0, 0, 0): 0, # 4 neighbors -> dies (overpopulation)
        (1, 1, 1, 1, 1, 1, 0, 0, 0): 0, # 5 neighbors -> dies (overpopulation)
        (1, 1, 1, 1, 1, 1, 1, 0, 0): 0, # 6 neighbors -> dies (overpopulation)
        (1, 1, 1, 1, 1, 1, 1, 1, 0): 0, # 7 neighbors -> dies (overpopulation)
        (1, 1, 1, 1, 1, 1, 1, 1, 1): 0, # 8 neighbors -> dies (overpopulation)
    }

    # Combine the rules. This dictionary will be passed to the CellularAutomaton.
    # Note: The order of neighbor states matters. The keys are:
    # (current_cell_state, top_left, top, top_right, middle_left, middle_right, bottom_left, bottom, bottom_right)
    # However, in _get_neighbor_states, we collect them in a fixed order.
    # Let's ensure consistency. A common order for neighbors is clockwise starting from top-left.
    # Our _get_neighbor_states produces: top-left, top, top-right, middle-left, middle-right, bottom-left, bottom, bottom-right.
    # So the keys are (current_state, tl, t, tr, ml, mr, bl, b, br)

    # We need to map the generated neighbor tuple from _get_neighbor_states to the rule_set keys.
    # Let's redefine the rule_set structure to match our _get_neighbor_states output directly.
    # A rule is defined by (current_state, neighbor_states_tuple) -> next_state

    # Example: A dead cell (0) with 3 live neighbors becomes alive (1).
    # The tuple for neighbors is generated by _get_neighbor_states.
    # We need to ensure the neighbor_states tuple from _get_neighbor_states
    # matches the structure expected by the rule_set.
    # _get_neighbor_states provides: (tl, t, tr, ml, mr, bl, b, br)

    # Let's adjust the rules to be more direct and cover all combinations.
    # This is where the "randomness" and "evolving" aspect comes in.
    # We can create different rule sets to get different patterns.

    # For simplicity, let's define rules for specific neighbor counts.
    # This is a simplified representation. A true Game of Life rule set would be exhaustive.

    # A more compact rule representation for Game of Life:
    # For a cell to be alive in the next generation:
    # 1. It must be currently alive and have 2 or 3 live neighbors.
    # 2. It must be currently dead and have exactly 3 live neighbors.

    # Let's build the rule_set based on neighbor counts.
    game_of_life_rules = {}
    # Live cell rules:
    # Current state is 1.
    for num_live_neighbors in range(9): # 0 to 8 neighbors
        # The rule depends on the number of live neighbors.
        # This part is tricky to map directly from a simple count.
        # The actual rule_set dictionary requires specific neighbor states.

        # Let's simplify and focus on a different type of evolving pattern
        # that is easier to define rules for initially.
        # Instead of Game of Life, let's create a custom rule.

        # Custom Rule Idea:
        # A cell becomes alive if it has at least one alive neighbor.
        # A live cell dies if it has more than 3 alive neighbors.
        # Otherwise, states persist.

        # Let's define a simpler rule set for a more abstract pattern.
        # Rule: A cell's next state is the XOR of its current state and a random
        #       neighbor's state. This will lead to complex, chaotic patterns.

        # Re-thinking the rule_set structure for clarity and learning.
        # The key will be (current_cell_state,) + neighbor_states_tuple
        # The neighbor_states_tuple from _get_neighbor_states is (tl, t, tr, ml, mr, bl, b, br)

    # Let's define a rule where a cell becomes alive if it has an ODD number of alive neighbors.
    # This is related to Rule 90 in Wolfram's 1D cellular automata, adapted to 2D.
    # Let's use a simpler rule:
    # - If a cell is dead (0) and has exactly 2 alive neighbors, it becomes alive (1).
    # - If a cell is alive (1) and has 1, 2, or 3 alive neighbors, it stays alive (1).
    # - All other cases, the cell becomes dead (0).

    # Constructing a rule_set for this custom logic:
    custom_rules = {}
    for r in range(self.height if 'self' in locals() else 10): # Mocking for rule generation
        for c in range(self.width if 'self' in locals() else 10): # Mocking
            for num_neighbors_alive in range(9):
                # Generate all possible neighbor state combinations for a given number of live neighbors
                # This is computationally intensive to do explicitly.
                # Let's focus on the rule logic itself.

                # A cell's next state depends on its current state and its neighbors.
                # We can define rules by iterating through all 9 possibilities (current state + 8 neighbors).
                # Total states = 2 (current) * 2^8 (neighbors) = 2 * 256 = 512 combinations.

                # Simplified custom rule:
                # A cell becomes alive (1) if its state XORed with the sum of its neighbors' states is 1.
                # This will be quite chaotic.

                # Let's simplify rule definition for educational purposes.
                # We'll focus on a rule where the next state is determined by the majority state of neighbors.
                # If a dead cell has more than 4 live neighbors, it becomes alive.
                # If a live cell has less than 3 live neighbors, it dies.
                # Otherwise, it keeps its state.

                # This requires counting live neighbors, not just matching a specific tuple.
                # Let's stick to the tuple matching for direct learning of rule_set.

                # Rule Set for Learning:
                # Rule 1: A dead cell (0) becomes alive (1) if it has exactly one alive neighbor.
                # Rule 2: A live cell (1) becomes dead (0) if it has more than three alive neighbors.
                # Rule 3: All other cells remain in their current state.

                # Implementing Rule 1:
                # We need to iterate through all possible neighbor states that sum to 1.
                # Example: (0, 1, 0, 0, 0, 0, 0, 0, 0) -> 1
                #           (0, 0, 1, 0, 0, 0, 0, 0, 0) -> 1
                # ... and so on for all 8 neighbors.

                # This suggests we need to generate neighbor combinations.
                # A simpler approach for learning is to specify a few key rules.

    # Let's define a rule set that produces interesting, non-trivial patterns.
    # Focus on the concept of state transitions based on neighbors.
    # Consider a simplified rule:
    # - A dead cell becomes alive if exactly two of its neighbors are alive.
    # - A live cell stays alive if two or three of its neighbors are alive.
    # - Otherwise, cells become dead.

    # This is very close to Conway's Game of Life.
    # Let's define the rule_set structure and populate it.

    # The structure of the keys in rule_set:
    # (current_cell_state, neighbor1, neighbor2, ..., neighbor8)
    # The order of neighbors is consistent with self._get_neighbor_states() output:
    # (top_left, top, top_right, middle_left, middle_right, bottom_left, bottom, bottom_right)

    # Let's define rules for Game of Life (a common and illustrative CA)
    # Birth: A dead cell with exactly three live neighbours becomes a live cell.
    # Survival: A live cell with two or three live neighbours survives.
    # Death:
    #   - Underpopulation: A live cell with fewer than two live neighbours dies.
    #   - Overpopulation: A live cell with more than three live neighbours dies.

    rules_for_game_of_life = {}

    # Generate all 2^8 = 256 possible neighbor states
    for i in range(256):
        binary_repr = bin(i)[2:].zfill(8) # Get binary string, pad to 8 digits
        neighbors = tuple(int(bit) for bit in binary_repr) # Convert bits to integer tuple

        # --- Rules for a DEAD cell (current_state = 0) ---
        # Birth: A dead cell with exactly three live neighbours becomes a live cell.
        live_neighbor_count = sum(neighbors)
        if live_neighbor_count == 3:
            rules_for_game_of_life[(0,) + neighbors] = 1 # Becomes alive

        # --- Rules for a LIVE cell (current_state = 1) ---
        # Survival: A live cell with two or three live neighbours survives.
        if live_neighbor_count == 2 or live_neighbor_count == 3:
            rules_for_game_of_life[(1,) + neighbors] = 1 # Stays alive
        else:
            # Death: Underpopulation ( < 2 neighbors) or Overpopulation ( > 3 neighbors)
            rules_for_game_of_life[(1,) + neighbors] = 0 # Dies

    # Now, `rules_for_game_of_life` contains the complete set of rules for Conway's Game of Life.
    # This dictionary can have up to 512 entries, but many will map to staying the same state.
    # For cells where the outcome isn't explicitly defined by the rules above, they remain in their current state.
    # The `.get(key, current_state)` in `CellularAutomaton.update` handles this.

    # --- Instantiate the Cellular Automaton ---
    # Calculate grid dimensions based on screen size and cell size.
    grid_width = SCREEN_WIDTH // CELL_SIZE
    grid_height = SCREEN_HEIGHT // CELL_SIZE

    # Create an instance of our CellularAutomaton.
    # We'll use a low initial density so patterns can emerge more clearly.
    # If density is too high, the grid might just become solid quickly.
    automaton = CellularAutomaton(grid_width, grid_height, rules_for_game_of_life, initial_density=0.3)

    # --- Main Game Loop ---
    running = True
    while running:
        # --- Event Handling ---
        for event in pygame.event.get():
            # If the user clicks the close button, exit the loop.
            if event.type == pygame.QUIT:
                running = False
            # Example: Pressing SPACE key to reset the pattern
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_SPACE:
                    print("Resetting pattern...")
                    # Re-initialize with a new random seed (or same, to see it evolve differently)
                    automaton = CellularAutomaton(grid_width, grid_height, rules_for_game_of_life, initial_density=random.uniform(0.2, 0.4))


        # --- Update ---
        # Advance the simulation by one step.
        automaton.update()

        # --- Drawing ---
        # Fill the screen with black to clear the previous frame.
        screen.fill(BLACK)
        # Draw the current state of the grid onto the screen.
        draw_grid(screen, automaton.get_grid(), CELL_SIZE)

        # --- Display Update ---
        # Update the full display Surface to the screen.
        pygame.display.flip()

        # --- Frame Rate Control ---
        # Limit the frame rate to 30 frames per second.
        # This controls the speed of the pattern evolution.
        clock.tick(30)

    # Quit Pygame when the loop ends.
    pygame.quit()

# --- Entry Point ---
# This ensures that the `main()` function is called only when the script is executed directly.
if __name__ == "__main__":
    main()

# --- Example Usage Explanation ---
# To run this program:
# 1. Save the code as a Python file (e.g., `evolving_patterns.py`).
# 2. Make sure you have `pygame` installed (`pip install pygame`).
# 3. Run the file from your terminal: `python evolving_patterns.py`
#
# You will see a window with a black background. Initially, it will be filled
# with random green "alive" cells. These cells will then evolve based on the
# rules of Conway's Game of Life.
#
# You can observe different patterns emerge:
# - Still lifes: Patterns that do not change.
# - Oscillators: Patterns that repeat in a cycle.
# - Spaceships: Patterns that move across the grid.
#
# Try changing the `initial_density` parameter when creating the `CellularAutomaton`
# instance in the `main()` function to see how it affects the initial state and
# subsequent evolution.
#
# Try experimenting with different `rules_for_game_of_life` definitions.
# You can create entirely new behaviors by defining different rules for
# how cells change based on their neighbors! For instance, change the birth
# and survival conditions.
#
# Pressing the SPACE bar will reset the pattern with a new random configuration.
# This is a great way to explore the vast possibilities of cellular automata.
#
# The concept of cellular automata is fundamental to many areas of science and
# art, including:
# - Modeling biological systems (e.g., spread of diseases)
# - Simulating physical phenomena (e.g., fire spread)
# - Generating procedural content in games and art
# - Studying complex systems and emergence
#
# This simple example provides a foundation for understanding these powerful concepts.
# You can expand upon this by:
# - Implementing different neighbor counting mechanisms.
# - Using more colors to represent different cell states or types.
# - Adding interaction (e.g., mouse clicks to change cell states).
# - Exploring 3D cellular automata.
# - Implementing more complex rule sets.
#
# Enjoy exploring the emergent beauty of algorithms and randomness!
"""