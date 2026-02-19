import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class StackAndQueueOperations {
    // This program teaches stack and queue operations in Java.
    // It provides a basic implementation of both data structures along with their respective methods.

    public static void main(String[] args) {

        // Create an empty stack
        Stack<Integer> stack = new Stack<>();

        // Push elements onto the stack
        stack.push(1);
        stack.push(2);
        stack.push(3);

        System.out.println("Stack after pushing 1, 2, and 3: " + stack);

        // Pop elements from the stack
        int popElement1 = (int)stack.pop();
        int popElement2 = (int)stack.pop();

        System.out.println("Popped element from the top of the stack: " + popElement1);
        System.out.println("Popped element from the top of the stack: " + popElement2);

        // Create an empty queue
        Queue<Integer> queue = new LinkedList<>();

        // Enqueue elements into the queue
        queue.add(4);
        queue.add(5);
        queue.add(6);

        System.out.println("Queue after adding 4, 5, and 6: " + queue);

        // Dequeue elements from the queue
        int dequeueElement1 = (int)queue.poll();
        int dequeueElement2 = (int)queue.poll();

        System.out.println("Dequeued element from the front of the queue: " + dequeueElement1);
        System.out.println("Dequeued element from the front of the queue: " + dequeueElement2);

    }

    //Example usage and test
    public static void exampleUsage() {
        Stack<Integer> stack = new Stack<>();
        Queue<Integer> queue = new LinkedList<>();

        // Pushing elements onto the stack
        stack.push(10);
        stack.push(20);
        System.out.println("Stack after pushing 10, 20: " + stack);

        // Popping elements from the stack
        int popElement1 = (int)stack.pop();
        int popElement2 = (int)stack.pop();

        System.out.println("Popped element from the top of the stack: " + popElement1);
        System.out.println("Popped element from the top of the stack: " + popElement2);

        // Adding elements to the queue
        queue.add(30);
        queue.add(40);
        System.out.println("Queue after adding 30, 40: " + queue);

        // Dequeueing elements from the queue
        int dequeueElement1 = (int)queue.poll();
        int dequeueElement2 = (int)queue.poll();

        System.out.println("Dequeued element from the front of the queue: " + dequeueElement1);
        System.out.println("Dequeued element from the front of the queue: " + dequeueElement2);

    }
}