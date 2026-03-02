// Trie.java

/**
 * This class represents a Trie data structure in Java.
 * It includes methods for inserting a word, searching for a word, and deleting a word.
 */

public class Trie {
    // Define a node in the Trie
    private static class Node {
        boolean isEndOfWord;
        Node[] children;

        public Node() {
            this.isEndOfWord = false;
            this.children = new Node[26]; // 26 letters in the English alphabet
        }
    }

    // Root node of the Trie
    private Node root;

    public Trie() {
        this.root = new Node();
    }

    // Insert a word into the Trie
    public void insert(String word) {
        Node current = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a'; // Convert char to index
            if (current.children[index] == null) {
                current.children[index] = new Node();
            }
            current = current.children[index];
        }
        current.isEndOfWord = true;
    }

    // Search for a word in the Trie
    public boolean search(String word) {
        Node current = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a'; // Convert char to index
            if (current.children[index] == null) {
                return false; // Word not found
            }
            current = current.children[index];
        }
        return current.isEndOfWord; // Word found
    }

    // Delete a word from the Trie
    public void delete(String word) {
        root = deleteHelper(root, word, 0);
    }

    // Helper method for delete
    private Node deleteHelper(Node current, String word, int index) {
        if (index == word.length()) {
            if (!current.isEndOfWord) {
                return null; // Word not found
            }
            current.isEndOfWord = false;
            return current;
        }
        char c = word.charAt(index);
        int indexInChildren = c - 'a';
        if (current.children[indexInChildren] == null) {
            return current;
        }
        current.children[indexInChildren] = deleteHelper(current.children[indexInChildren], word, index + 1);
        if (current.children[indexInChildren] == null && current.isEndOfWord) {
            current.isEndOfWord = false;
        }
        return current;
    }

    // Run the Trie with a sample word
    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        trie.insert("app");
        trie.insert("banana");

        System.out.println(trie.search("apple")); // true
        System.out.println(trie.search("app")); // true
        System.out.println(trie.search("banana")); // true
        System.out.println(trie.search("bana")); // false

        trie.delete("app");
        System.out.println(trie.search("app")); // false
    }
}