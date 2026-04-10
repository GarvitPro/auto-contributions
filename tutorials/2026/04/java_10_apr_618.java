/**
 * Trie Data Structure Implementation in Java
 */

// Node class representing a single node in the trie
class Node {
    // Children nodes of the current node
    private final Node[] children = new Node[26];
    // Whether the current node is the end of a word
    private boolean isEndOfWord;

    public Node() {
        for (int i = 0; i < 26; i++) {
            children[i] = null;
        }
        isEndOfWord = false;
    }

    // Method to add a word to the trie
    public void addWord(String word) {
        Node current = this;
        for (char c : word.toCharArray()) {
            int index = c - 'a'; // Map char to index in children array
            if (current.children[index] == null) {
                current.children[index] = new Node();
            }
            current = current.children[index];
        }
        current.isEndOfWord = true;
    }

    // Method to check if a word is present in the trie
    public boolean containsWord(String word) {
        return contains(word, 0);
    }

    private boolean contains(String word, int index) {
        Node current = this;
        for (int i = index; i < word.length(); i++) {
            char c = word.charAt(i);
            int idx = c - 'a';
            if (current.children[idx] == null) {
                return false;
            }
            current = current.children[idx];
        }
        // Check if the remaining part of the word is a suffix
        return current.isEndOfWord && contains(word, index + 1);
    }
}

// Trie class representing the entire trie data structure
public class Trie {
    private Node root;

    public Trie() {
        root = new Node();
    }

    // Method to add a word to the trie
    public void addWord(String word) {
        root.addWord(word);
    }

    // Method to check if a word is present in the trie
    public boolean containsWord(String word) {
        return root.containsWord(word);
    }
}

// Main class for testing
public class TrieTest {
    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.addWord("apple");
        trie.addWord("app");
        trie.addWord("banana");

        System.out.println(trie.containsWord("apple")); // true
        System.out.println(trie.containsWord("app"));   // true
        System.out.println(trie.containsWord("banan")); // false
        System.out.println(trie.containsWord("aple"));  // false
    }
}