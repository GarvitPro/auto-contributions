// Heap Operations in Java
// This class demonstrates the basic heap operations including insert, delete and display.

import java.util.Arrays;

public class HeapOperations {
    // Define a Node class to represent individual elements in the heap
    static class Node {
        int data;
        Node left;
        Node right;

        public Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    // Function to insert a new node into the heap
    static void insert(Node[] heap, int data) {
        // Create a new Node object with given data
        Node newNode = new Node(data);

        // If the heap is empty, add the new node as root
        if (heap.length == 0) {
            heap[0] = newNode;
            return;
        }

        // Add the new node to the end of the array
        int i = heap.length - 1;
        while (i > 0 && heap[i].data < newNode.data) {
            Node temp = heap[i];
            heap[i] = heap[(i - 1) / 2];
            heap[(i - 1) / 2] = temp;
            i = (i - 1) / 2;
        }
        heap[i] = newNode;
    }

    // Function to delete a node from the heap
    static void delete(Node[] heap, int data) {
        // If the heap is empty, return
        if (heap.length == 0) {
            return;
        }

        // Find the leaf node with given data
        for (int i = 0; i < heap.length / 2; i++) {
            if (heap[i].data == data) {
                break;
            }
        }

        // If the node to be deleted is not found, return
        if (i >= heap.length || heap[i].data != data) {
            return;
        }

        // Set the node to be deleted as leaf
        Node temp = heap[0];
        heap[0] = heap[i];

        // Perform heapify on the root node
        heapify(heap, 0);

        // Delete the leaf node from the array
        for (int i = 0; i < heap.length / 2; i++) {
            if (heap[i].data == data) {
                return;
            }
            temp = heap[i];
            heap[i] = null;
            i++;
        }
    }

    // Function to perform heapify on a subtree
    static void heapify(Node[] heap, int i) {
        Node leftChild = heap[2 * i + 1];
        Node rightChild = heap[2 * i + 2];

        // Find the largest child
        int maxIndex = i;
        if (leftChild != null && leftChild.data > heap[maxIndex].data) {
            maxIndex = 2 * i + 1;
        }
        if (rightChild != null && rightChild.data > heap[maxIndex].data) {
            maxIndex = 2 * i + 2;
        }

        // If the largest child exists and is greater than the current node
        if (maxIndex < heap.length && heap[maxIndex] != null && heap[maxIndex].data > heap[i].data) {
            Node temp = heap