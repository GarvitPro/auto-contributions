// TrieDataStructure.java

/**
 * This class represents a Trie data structure in Java.
 * A Trie (also known as a prefix tree) is a tree-like data structure that is used to store a dynamic set or associative array where the keys are usually strings.
 *
 * @author [Your Name]
 * @version 1.0
 */

public class TrieDataStructure {

    // The root of the Trie
    private Node root;

    // Constructor to initialize the Trie
    public TrieDataStructure() {
        this.root = new Node();
    }

    /**
     * Method to insert a word into the Trie
     *
     * @param word The word to be inserted into the Trie
     */
    public void insert(String word) {
        Node current = root;
        for (char ch : word.toCharArray()) {
            // If the character is not present in the Trie, add it
            if (current.children[ch - 'a'] == null) {
                current.children[ch - 'a'] = new Node();
            }
            // Move to the next node
            current = current.children[ch - 'a'];
        }
        // Mark the end of the word
        current.isEndOfWord = true;
    }

    /**
     * Method to search for a word in the Trie
     *
     * @param word The word to be searched in the Trie
     * @return True if the word is present in the Trie, false otherwise
     */
    public boolean search(String word) {
        Node current = root;
        for (char ch : word.toCharArray()) {
            // If the character is not present in the Trie, return false
            if (current.children[ch - 'a'] == null) {
                return false;
            }
            // Move to the next node
            current = current.children[ch - 'a'];
        }
        // If the word is found, return true
        return current.isEndOfWord;
    }

    /**
     * Method to delete a word from the Trie
     *
     * @param word The word to be deleted from the Trie
     */
    public void delete(String word) {
        delete(word, 0, root);
    }

    /**
     * Helper method to delete a word from the Trie
     *
     * @param word The word to be deleted from the Trie
     * @param index The current index in the word
     * @param node The current node in the Trie
     */
    private void delete(String word, int index, Node node) {
        if (index == word.length()) {
            // If the word is empty, remove the last character
            if (node.children[0] == null) {
                node.children[0] = null;
            }
            // If the node is empty, return
            if (node.children[0] == null) {
                return;
            }
            // If the node is not empty, remove the last character
            if (node.children[0].isEndOfWord) {
                node.children[0].isEndOfWord = false;
                if (node.children[0].children[0] == null) {
                    node.children[0] = null;
                }
            } else {
                delete(word, index, node.children[0]);
            }
            return;
        }
        // If the character is not present in the Trie, return