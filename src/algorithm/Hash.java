package algorithm;

import dataStructure.Bag;

public class Hash {
    public static void main(String[] args) {

    }
}

class SeparateChaining<Key extends Comparable<Key>, Value> {

    private int size;
    private int cap;
    private Bag<Node>[] arr;

    private class Node {
        Key key;
        Value val;

        Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }
    }

    public SeparateChaining(int cap) {
        this.cap = cap;
        arr = new Bag[cap];
        for (int i = 0; i < arr.length; i++) arr[i] = new Bag<Node>();
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % cap;
    }

    public Value get(Key key) {
        for (Node node : arr[hash(key)]) {
            if (key.compareTo(node.key) == 0) return node.val;
        }
        return null;
    }

    public void put(Key key, Value val) {
        arr[hash(key)].add(new Node(key, val));
        size++;
    }

    public int getSize() {
        return size;
    }

}

class LinearProbing<Key extends Comparable<Key>, Value> {
    private int size;
    private int cap;
    private Key[] keys;
    private Value[] values;

    public LinearProbing(int cap) {
        this.cap = cap;
        keys = (Key[]) new Object[cap];
        values = (Value[]) new Object[cap];
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7ffffff) % cap;
    }

    public void put(Key key, Value value) {
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % cap) {
            if (key.compareTo(keys[i]) == 0) {
                values[i] = value;
                return;
            }
        }
        keys[i] = key;
        values[i] = value;
        size++;
    }

    public Value get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % cap) {
            if (key.compareTo(keys[i]) == 0) {
                return values[i];
            }
        }
        return null;
    }

    public void delete(Key key) {
        if (!contains(key)) return;
        int i = hash(key);
        while (key.compareTo(keys[i]) != 0) i = (i + 1) % cap;
        keys[i] = null;
        values[i] = null;
        i = (i + 1) % cap;
        while (keys[i] != null) {
            Key keyToRedo = keys[i];
            Value valToRedo = values[i];
            keys[i] = null;
            values[i] = null;
            size--;
            put(keyToRedo, valToRedo);
            i = (i + 1) % cap;
        }
        size--;
    }

    private boolean contains(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % cap) {
            if (key.compareTo(keys[i]) == 0)
                return true;
        }
        return false;
    }

}