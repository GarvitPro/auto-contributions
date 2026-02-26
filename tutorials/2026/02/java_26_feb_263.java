// HeapOperations.java

// Importing necessary packages
import java.util.*;

// Heap Operations class
public class HeapOperations {

    // Constructor to initialize the heap
    public HeapOperations() {
        this.heap = new ArrayList<>();
    }

    // Method to insert a new element into the heap
    public void insert(int value) {
        // Add the new element to the end of the heap
        heap.add(value);
        // Bubble up the new element to its correct position
        bubbleUp(heap.size() - 1);
    }

    // Method to remove and return the largest element from the heap
    public int removeMax() {
        // Check if the heap is empty
        if (heap.isEmpty()) {
            throw new RuntimeException("Heap is empty");
        }
        // Store the maximum value
        int max = heap.get(0);
        // Swap the maximum value with the last element in the heap
        heap.set(0, heap.remove(heap.size() - 1));
        // Bubble down the new maximum value to its correct position
        bubbleDown(0);
        return max;
    }

    // Method to print the heap
    public void printHeap() {
        System.out.println("Heap: " + heap);
    }

    // Helper method to bubble up the new element to its correct position
    private void bubbleUp(int index) {
        // Initialize the current index
        int currentIndex = index;
        // While the current index is greater than 0
        while (currentIndex > 0) {
            // Calculate the parent index
            int parentIndex = (currentIndex - 1) / 2;
            // If the parent index is greater than the current index, swap the elements
            if (parentIndex >= 0 && heap.get(parentIndex) > heap.get(currentIndex)) {
                int temp = heap.get(parentIndex);
                heap.set(parentIndex, heap.get(currentIndex));
                heap.set(currentIndex, temp);
                // Update the current index
                currentIndex = parentIndex;
            } else {
                break;
            }
        }
    }

    // Helper method to bubble down the new maximum value to its correct position
    private void bubbleDown(int index) {
        // Initialize the current index
        int currentIndex = index;
        // Initialize the left child index
        int leftChildIndex = 2 * currentIndex + 1;
        // Initialize the right child index
        int rightChildIndex = 2 * currentIndex + 2;
        // While the left child index is within the heap bounds
        while (leftChildIndex < heap.size()) {
            // Initialize the maximum index
            int maxIndex = currentIndex;
            // If the right child index is within the heap bounds and the right child is greater than the left child, update the maximum index
            if (rightChildIndex < heap.size() && heap.get(rightChildIndex) > heap.get(leftChildIndex)) {
                maxIndex = rightChildIndex;
            }
            // If the maximum index is not the current index, swap the elements
            if (maxIndex != currentIndex) {
                int temp = heap.get(maxIndex);
                heap.set(maxIndex, heap.get(currentIndex));
                heap.set(currentIndex, temp);
                // Update the current index
                currentIndex = maxIndex;
            } else {
                break;
            }
            // Update the left child index
            leftChildIndex++;
            // Update the right child