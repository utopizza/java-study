package cache;

import java.util.HashMap;
import java.util.Map;

public class MyLRUCache<K, V> {

    private class Node {
        Node pre, next;
        K key;
        V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node head, tail; // maintain a linkedList, oldest at the head, newest at the end
    private int cap; // total capacity of the cache
    private int size; // current number of the elements
    Map<K, Node> map = new HashMap<>(); // help to find a Node in O(1)

    MyLRUCache(int cap) {
        if (cap <= 0) throw new RuntimeException("Not correct capacity.");
        this.cap = cap;
    }

    public void put(K key, V value) {
        Node newNode = new Node(key, value);

        // add the new object at the end of the list
        map.put(key, newNode);
        if (head == null) head = tail = newNode;
        else {
            tail.next = newNode;
            newNode.pre = tail;
            tail = newNode;
        }
        size++;

        // remove the oldest object from the head
        if (size > cap) {
            Node toDelete = head;
            head = head.next;
            map.remove(toDelete.key);
            size--;
        }

        printList();
    }

    public V get(K key) {
        if (!map.containsKey(key)) return null;
        Node visitedNode = map.get(key);

        // move the visited node to the end of the list
        if (visitedNode != tail) {

            // remove
            if (visitedNode == head) {
                head = head.next;
            } else {
                visitedNode.pre.next = visitedNode.next;
                visitedNode.next.pre = visitedNode.pre;
            }

            // add
            tail.next = visitedNode;
            visitedNode.pre = tail;
            visitedNode.next = null;
            tail = visitedNode;
        }

        printList();
        return visitedNode.value;
    }

    public void printList() {
        if (head == null) System.out.println("empty list.");
        Node p = head;
        String toPrint = "Size=" + size + ":";
        while (p != null) {
            toPrint += "(" + p.key + "," + p.value + ") ";
            p = p.next;
        }
        System.out.println(toPrint);
    }

    public static void main(String[] args) {
        MyLRUCache<String, String> myLRUCache = new MyLRUCache<>(3);
        myLRUCache.put("a", "1");
        myLRUCache.put("b", "2");
        myLRUCache.put("c", "3");
        myLRUCache.put("d", "4");
        myLRUCache.get("b");
        myLRUCache.put("e", "5");
        myLRUCache.get("c");
        myLRUCache.get("b");
    }

}
