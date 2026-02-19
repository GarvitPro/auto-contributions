import java.util.Arrays;
import import java.util.PriorityQueue;

public class HeapOperations {
    // This program teaches heap operations including inserting elements into a heap, 
    // deleting elements from a heap, and maintaining heap properties like heapify.

    public static void main(String[] args) {
        // Create an empty min heap
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // Insert elements into the heap
        insertIntoMinHeap(minHeap, 10);
        insertIntoMinHeap(minHeap, 20);
        insertIntoMinHeap(minHeap, 30);

        System.out.println("Min heap after insertion: " + printMinHeap(minHeap));

        // Delete the top element from the heap
        Integer deletedElement = deleteFromMinHeap(minHeap);
        System.out.println("Deleted element: " + deletedElement);
        System.out.println("Min heap after deletion: " + printMinHeap(minHeap));
    }

    // Function to insert an element into a min heap
    public static void insertIntoMinHeap(PriorityQueue<Integer> minHeap, int element) {
        // Add the element to the end of the priority queue
        minHeap.add(element);

        // Heapify the last element (at index -1)
        heapifyLastElement(minHeap);
    }

    // Function to delete an element from a min heap
    public static Integer deleteFromMinHeap(PriorityQueue<Integer> minHeap) {
        // Check if the heap is empty
        if (minHeap.isEmpty()) {
            System.out.println("Heap is empty");
            return null;
        }

        // Get the top element (minimum element)
        int topElement = minHeap.poll();

        // If the heap has only one element, remove it
        if (minHeap.size() == 1) {
            System.out.println("Only one element left in the heap");
            return topElement;
        }

        // Heapify the last element (at index -1)
        heapifyLastElement(minHeap);

        // Return the deleted element
        return topElement;
    }

    // Function to maintain heap property for the last element
    public static void heapifyLastElement(PriorityQueue<Integer> minHeap) {
        int parentIndex = (minHeap.size() / 2) - 1;

        // Compare the last element with its parent
        if (parentIndex >= 0 && minHeap.peek() > minHeap.get(parentIndex)) {
            // Swap the elements to maintain heap property
            int temp = minHeap.poll();
            minHeap.add(minHeap.remove(parentIndex));
            minHeap.add(temp);
        }
    }

    // Function to print the min heap
    public static String printMinHeap(PriorityQueue<Integer> minHeap) {
        StringBuilder sb = new StringBuilder();

        while (!minHeap.isEmpty()) {
            sb.append(minHeap.poll()).append(" ");
        }

        return sb.toString();
    }
}