// This class teaches stack and queue operations in Java.
// It explains how to implement a stack using an array and a queue using LinkedList.
// These data structures are fundamental in computer science and have numerous applications.

import java.util.LinkedList;
import java.util.Queue;

public class StackQueueOperations {

    // Define a class for the stack
    static class Stack {
        private int[] arr;
        private int top;

        public Stack(int size) {
            arr = new int[size];
            top = -1;
        }

        // Push an element onto the stack
        public void push(int data) {
            if (top < arr.length - 1) {
                arr[++top] = data;
            } else {
                System.out.println("Stack is full!");
            }
        }

        // Pop an element from the stack
        public int pop() {
            if (top >= 0) {
                return arr[top--];
            } else {
                return -1; // Return -1 to indicate no more elements in the stack
            }
        }

        // Peek at the top element of the stack without removing it
        public int peek() {
            if (top >= 0) {
                return arr[top];
            } else {
                return -1; // Return -1 to indicate no more elements in the stack
            }
        }

        // Check if the stack is empty
        public boolean isEmpty() {
            return top == -1;
        }
    }

    // Define a class for the queue
    static class Queue {
        private LinkedList<Integer> queue;

        public Queue() {
            queue = new LinkedList<>();
        }

        // Enqueue an element into the queue
        public void enqueue(int data) {
            queue.add(data);
        }

        // Dequeue an element from the queue
        public int dequeue() {
            if (!queue.isEmpty()) {
                return queue.remove();
            } else {
                throw new RuntimeException("Queue is empty!");
            }
        }

        // Peek at the front element of the queue without removing it
        public int peek() {
            if (!queue.isEmpty()) {
                return queue.peek();
            } else {
                throw new RuntimeException("Queue is empty!");
            }
        }

        // Check if the queue is empty
        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    public static void main(String[] args) {
        Stack stack = new Stack(5);
        Queue queue = new Queue();

        // Push elements onto the stack and print their values
        System.out.println("Stack values:");
        for (int i = 0; i < 10; i++) {
            stack.push(i);
            System.out.print(stack.peek() + " ");
        }
        System.out.println("\n");

        // Dequeue elements from the queue and print their values
        System.out.println("Queue values:");
        while (!queue.isEmpty()) {
            System.out.println(queue.dequeue());
        }

        // Check if stacks are empty
        System.out.println("Is stack empty? " + stack.isEmpty());

        // Check if queues are empty
        System.out.println("Is queue empty? " + queue.isEmpty());
    }
}