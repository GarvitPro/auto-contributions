// TrieDataStructure.java
// This file teaches how to implement a Trie data structure in Java.
// A Trie (also known as a prefix tree) is a tree-like data structure used for storing and retrieving strings.
// It's useful when you need to store a large number of words or substrings, like in autocomplete features.

import java.util.*;

public class TrieDataStructure {

    // TrieNode represents a node in the Trie.
    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEndOfWord;

        public TrieNode() {
            this.isEndOfWord = false;
        }
    }

    // TrieDataStructure represents the Trie data structure itself.
    private TrieNode root;

    public TrieDataStructure() {
        this.root = new TrieNode();
    }

    // Insert a word into the Trie.
    // This method checks if the word already exists in the Trie, and if so, it updates the end-of-word flag for that node.
    public void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            char ch = Character.toLowerCase(c);
            if (!current.children.containsKey(ch)) {
                current.children.put(ch, new TrieNode());
            }
            current = current.children.get(ch);
        }
        current.isEndOfWord = true;
    }

    // Search for a word in the Trie.
    // This method returns true if the word exists in the Trie, and false otherwise.
    public boolean search(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            char ch = Character.toLowerCase(c);
            if (!current.children.containsKey(ch)) {
                return false;
            }
            current = current.children.get(ch);
        }
        return current.isEndOfWord;
    }

    // Check if a prefix exists in the Trie.
    // This method returns true if the prefix exists, and false otherwise.
    public boolean startsWith(String prefix) {
        TrieNode current = root;
        for (char c : prefix.toCharArray()) {
            char ch = Character.toLowerCase(c);
            if (!current.children.containsKey(ch)) {
                return false;
            }
            current = current.children.get(ch);
        }
        return true;
    }

    // Test the Trie data structure.
    public static void main(String[] args) {
        TrieDataStructure trie = new TrieDataStructure();
        String[] words = {"apple", "banana", "app", "band"};
        for (String word : words) {
            trie.insert(word);
        }
        System.out.println(trie.search("apple")); // prints: true
        System.out.println(trie.search("banan")); // prints: false
        System.out.println(trie.startsWith("ap")); // prints: true
        System.out.println(trie.startsWith("ba")); // prints: true
    }
}