package algorithm;

import dataStructure.Queue;

public class Search {
    public static void main(String[] args) {
        WordDictionary w = new WordDictionary();
        w.addWord("abc");
        w.search("a.c");
    }
}

class BinarySearchArray {
    public int search(int[] arr, int target) {
        int lo = 0, hi = arr.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (target == arr[mid]) return mid;
            else if (target < arr[mid]) hi = mid - 1;
            else lo = mid + 1;
        }
        return -1;
    }

    public int BinarySearchMinForRotateArray(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (nums[mid] > nums[nums.length - 1]) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    public int BinarySearchMaxForRotateArray(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = (lo + hi + 1) / 2;
            if (nums[mid] < nums[0]) hi = mid - 1;
            else lo = mid;
        }
        return lo;
    }
}

class BinarySearchTree<Key extends Comparable<Key>, Value> {

    private class Node {
        Key key;
        Value val;
        Node left, right;
        int size;

        Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    private Node root;

    public int getSize() {
        return getSize(root);
    }

    private int getSize(Node node) {
        if (node == null) return 0;
        else return node.size;
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node node, Key key, Value val) {
        if (node == null) return new Node(key, val, 1);
        if (key.compareTo(node.key) == 0) node.val = val;
        else if (key.compareTo(node.key) < 0) node.left = put(node.left, key, val);
        else node.right = put(node.right, key, val);
        node.size = getSize(node.left) + getSize(node.right) + 1;
        return node;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node node, Key key) {
        if (node == null) return null;
        else if (key.compareTo(node.key) == 0) return node.val;
        else if (key.compareTo(node.key) < 0) return get(node.left, key);
        else return get(node.right, key);
    }

    public Key getMin() {
        return getMin(root).key;
    }

    private Node getMin(Node node) {
        if (node == null) return null;
        while (node.left != null) node = node.left;
        return node;
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node node) {
        if (node == null) return null;
        if (node.left == null) return node.right;
        else node.left = deleteMin(node.left);
        node.size = getSize(node.left) + getSize(node.right) + 1;
        return node;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node node, Key key) {
        if (node == null) return null;
        else if (key.compareTo(node.key) < 0) node.left = delete(node.left, key);
        else if (key.compareTo(node.key) > 0) node.right = delete(node.right, key);
        else {
            if (node.left == null || node.right == null) node = node.left == null ? node.right : node.left;
            else {
                Node temp = getMin(node.right);
                node.right = deleteMin(node.right);
                temp.left = node.left;
                temp.right = node.right;
                node = temp;
            }
        }
        node.size = getSize(node.left) + getSize(node.right) + 1;
        return node;
    }

    public Key floor(Key key) {
        Node node = floor(root, key);
        if (node == null) return null;
        return node.key;
    }

    private Node floor(Node node, Key key) { // floor(key)<=key
        if (node == null) return null;
        if (key.compareTo(node.key) == 0) return node;
        else if (key.compareTo(node.key) < 0) return floor(node.left, key);
        // key>node.key
        Node temp = floor(node.right, key);
        if (temp != null) return temp;
        else return node;
    }

    public Key select(int k) {
        return select(root, k).key;
    }

    private Node select(Node node, int k) {
        if (node == null) return null;
        int t = getSize(node.left);
        if (t < k) return select(node.right, k - t - 1);
        else if (t > k) return select(node.left, k);
        else return node;
    }

    public int rank(Key key) {
        return rank(root, key);
    }

    private int rank(Node node, Key key) {
        if (node == null) return 0;
        if (key.compareTo(node.key) < 0) return rank(node.left, key);
        else if (key.compareTo(node.key) > 0) return rank(node.right, key) + getSize(node.left) + 1;
        else return getSize(node.left);
    }

    public Iterable<Key> getKeys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<Key>();
        getKeys(root, queue, lo, hi);
        return queue;
    }

    private void getKeys(Node node, Queue<Key> queue, Key lo, Key hi) {
        if (node == null) return;
        if (lo.compareTo(node.key) < 0) getKeys(node.left, queue, lo, hi);
        if (lo.compareTo(node.key) <= 0 && hi.compareTo(node.key) >= 0) queue.enqueue(node.key);
        if (hi.compareTo(node.key) > 0) getKeys(node.right, queue, lo, hi);
    }

}

class AVLTree<Key extends Comparable<Key>, Value> {

    private Node root;
    private static final int ALLOWED_IMBALANCE = 1;

    private class Node {
        Key key;
        Value val;
        Node left, right;
        int height;

        Node(Key key, Value val) {
            this.key = key;
            this.val = val;
            this.height = 0;
        }
    }

    private int getHeight(Node node) {
        return node == null ? -1 : node.height;
    }

    private Node balance(Node node) {
        if (node == null) return node;

        if (getHeight(node.left) - getHeight(node.right) > ALLOWED_IMBALANCE)
            if (getHeight(node.left.left) >= getHeight(node.left.right)) node = rotateLL(node);
            else node = rotateLR(node);
        else if (getHeight(node.right) - getHeight(node.left) > ALLOWED_IMBALANCE)
            if (getHeight(node.right.right) >= getHeight(node.right.left)) node = rotateRR(node);
            else node = rotateRL(node);

        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        return node;
    }

    private Node rotateLL(Node oldRoot) {
        Node newRoot = oldRoot.left;
        oldRoot.left = newRoot.right;
        newRoot.right = oldRoot;
        oldRoot.height = Math.max(getHeight(oldRoot.left), getHeight(oldRoot.right)) + 1;
        newRoot.height = Math.max(getHeight(newRoot.left), getHeight(oldRoot.right)) + 1;
        return newRoot;
    }

    private Node rotateRR(Node oldRoot) {
        Node newRoot = oldRoot.right;
        oldRoot.right = newRoot.left;
        newRoot.left = oldRoot;
        oldRoot.height = Math.max(getHeight(oldRoot.left), getHeight(oldRoot.right)) + 1;
        newRoot.height = Math.max(getHeight(newRoot.left), getHeight(oldRoot.right)) + 1;
        return newRoot;
    }

    private Node rotateLR(Node oldRoot) {
        oldRoot.left = rotateRR(oldRoot.left);
        return rotateLL(oldRoot);
    }

    private Node rotateRL(Node oldRoot) {
        oldRoot.right = rotateLL(oldRoot.right);
        return rotateRR(oldRoot);
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node node, Key key, Value val) {
        if (node == null) return new Node(key, val);
        if (key.compareTo(node.key) < 0) node.left = put(node.left, key, val);
        else if (key.compareTo(node.key) > 0) node.right = put(node.right, key, val);
        else node.val = val;
        return balance(node);
    }
}

class RedBlackTree<Key extends Comparable<Key>, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;

    private class Node {
        Key key;
        Value val;
        Node left, right;
        int size;
        boolean color;

        Node(Key key, Value val, int size, boolean color) {
            this.key = key;
            this.val = val;
            this.size = size;
            this.color = color;
        }
    }

    private boolean isRed(Node node) {
        return node == null ? false : node.color == RED;
    }

    private int getSize(Node node) {
        if (node == null) return 0;
        else return getSize(node.left) + getSize(node.right) + 1;
    }

    private void flipColors(Node node) {
        if (node == null) return;
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    private Node rotateLeft(Node root) {
        Node newRoot = root.right;
        root.right = newRoot.left;
        newRoot.left = root;
        newRoot.color = root.color;
        root.color = RED;
        newRoot.size = root.size;
        root.size = getSize(root.left) + getSize(root.right) + 1;
        return newRoot;
    }

    private Node rotateRight(Node root) {
        Node newRoot = root.left;
        root.left = newRoot.right;
        newRoot.right = root;
        newRoot.color = root.color;
        root.color = RED;
        newRoot.size = root.size;
        root.size = getSize(root.left) + getSize(root.right) + 1;
        return newRoot;
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
        root.color = BLACK;
    }

    private Node put(Node node, Key key, Value val) {
        if (node == null) return new Node(key, val, 1, RED);

        if (key.compareTo(node.key) < 0) node.left = put(node.left, key, val);
        else if (key.compareTo(node.key) > 0) node.right = put(node.right, key, val);
        else node.val = val;

        if (isRed(node.right) && !isRed(node.left)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColors(node);

        node.size = getSize(node.left) + getSize(node.right) + 1;
        return node;
    }

}

class Trie<Value> {
    private static int R = 256;
    private Node root;

    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }

    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c], key, d + 1);
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d + 1);
        return x;
    }

    // find all keys
    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    // find keys that matched the given prefix
    private Iterable<String> keysWithPrefix(String pre) {
        Queue<String> q = new Queue<String>();
        collect(get(root, pre, 0), pre, q);
        return q;
    }

    private void collect(Node x, String pre, Queue<String> q) {
        if (x == null) return;
        if (x.val != null) q.enqueue(pre);
        for (char c = 0; c < R; c++) {
            collect(x.next[c], pre + c, q);
        }
    }

    // find keys that matched the given patten(containing wildcard, such as '.'), not prefix
    public Iterable<String> keysThatMatch(String pat) {
        Queue<String> q = new Queue<String>();
        collect(root, "", pat, q);
        return q;
    }

    private void collect(Node x, String pre, String pat, Queue<String> q) {
        int d = pre.length();
        if (x == null) return;
        if (d == pat.length() && x.val != null) q.enqueue(pre);
        if (d == pat.length()) return;

        char next = pat.charAt(d);
        for (char c = 0; c < R; c++) {
            if (next == '.' || next == c) { // wildcard
                collect(x.next[c], pre + c, pat, q);
            }
        }
    }

    // search longest prefix
    public String longestPrefixOf(String s) {
        int length = search(root, s, 0, 0);
        return s.substring(0, length);
    }

    private int search(Node x, String s, int d, int length) {
        if (x == null) return length;
        if (x.val != null) length = d;
        if (d == s.length()) return length;
        char c = s.charAt(d);
        return search(x.next[c], s, d + 1, length);
    }

    // delete a key
    public void delete(String key) {
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) x.val = null;
        else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d + 1);
        }
        if (x.val != null) return x;
        for (char c = 0; c < R; c++) {
            if (x.next[c] != null) {
                return x;
            }
        }
        return null;
    }
}

class TrieSimple {

    private class Node {
        String key;
        Node[] next;

        Node() {
            key = "";
            next = new Node[256];
        }
    }

    private Node root;

    /**
     * Initialize your data structure here.
     */
    public TrieSimple() {
        root = new Node();
    }

    /**
     * Inserts a word into the trie.
     */
    public void insert(String word) {
        Node p = root;
        for (int i = 0; i < word.length(); i++) {
            int c = (int) word.charAt(i);
            if (p.next[c] == null) p.next[c] = new Node();
            if (i == word.length() - 1) p.next[c].key = word;
            p = p.next[c];
        }
    }

    /**
     * Returns if the word is in the trie.
     */
    public boolean search(String word) {
        Node p = root;
        for (int i = 0; i < word.length(); i++) {
            int c = (int) word.charAt(i);
            if (p.next[c] == null) return false;
            if (i == word.length() - 1 && p.next[c].key.equals(word)) return true;
            p = p.next[c];
        }
        return false;
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        return false;
    }
}

class WordDictionary {

    private class TrieNode {
        String key;
        TrieNode[] next;

        TrieNode() {
            key = "";
            next = new TrieNode[26];
        }
    }

    private TrieNode root;

    /**
     * Initialize your data structure here.
     */
    public WordDictionary() {
        root = new TrieNode();
    }

    /**
     * Adds a word into the data structure.
     */
    public void addWord(String word) {
        TrieNode p = root;
        for (int i = 0; i < word.length(); i++) {
            int j = word.charAt(i) - 'a';
            if (p.next[j] == null) p.next[j] = new TrieNode();
            if (i == word.length() - 1) p.next[j].key = word;
            p = p.next[j];
        }
    }

    /**
     * Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter.
     */
    public boolean search(String word) {
        return search(word, 0, root);
    }

    private boolean search(String word, int i, TrieNode node) {
        if (node == null) return false;
        if (i == word.length()) return node.key != "";
        char c = word.charAt(i);
        if (c == '.') {
            boolean ret = false;
            for (TrieNode n : node.next) ret = ret || search(word, i + 1, n);
            return ret;
        } else {
            if (node.next[c - 'a'] != null) {
                if (i == word.length() - 1) return !node.next[c - 'a'].key.equals("");
                else return search(word, i + 1, node.next[c - 'a']);
            } else return false;
        }
    }
}

class KMP {
    private int violentSearch(String pat, String txt) {
        int M = pat.length(), N = txt.length();
        for (int i = 0; i <= N - M; i++) {
            int j;
            for (j = 0; j < M; j++) {
                if (txt.charAt(i + j) != pat.charAt(j)) break;
            }
            if (j == M) return i;
        }
        return N;
    }
}