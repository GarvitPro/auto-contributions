public class Trie {
    private static final int MAX_SIZE = 26;

    // Trie node
    private static class Node {
        boolean endOfWord;
        Node[] children;

        public Node() {
            this.endOfWord = false;
            this.children = new Node[MAX_SIZE];
        }
    }

    private Node root;

    // Constructor to initialize the trie
    public Trie() {
        this.root = new Node();
    }

    // Method to insert a word into the trie
    public void insert(String word) {
        Node current = root;
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';
            if (current.children[index] == null) {
                current.children[index] = new Node();
            }
            current = current.children[index];
        }
        current.endOfWord = true;
    }

    // Method to search a word in the trie
    public boolean search(String word) {
        Node current = root;
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';
            if (current.children[index] == null) {
                return false;  // Word not found
            }
            current = current.children[index];
        }
        return current.endOfWord;  // Word found or is end of a word
    }

    // Method to check if a prefix exists in the trie
    public boolean startsWith(String prefix) {
        Node current = root;
        for (char ch : prefix.toCharArray()) {
            int index = ch - 'a';
            if (current.children[index] == null) {
                return false;  // Prefix not found
            }
            current = current.children[index];
        }
        return true;  // Prefix exists
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        trie.insert("banana");
        trie.insert("orange");

        System.out.println(trie.search("apple"));   // true
        System.out.println(trie.search("banana"));  // true
        System.out.println(trie.search("orange"));  // true
        System.out.println(trie.search("grape"));   // false

        System.out.println(trie.startsWith("app"));  // true
        System.out.println(trie.startsWith("ban"));  // true
        System.out.println(trie.startsWith("ora"));  // true
        System.out.println(trie.startsWith("gra"));  // false
    }
}