package dataStructure;

import java.util.Iterator;

public class Stack<T> implements Iterable<T> {

    private Node first;
    private int size;

    private class Node {
        T item;
        Node next;

        Node(T item) {
            this.item = item;
        }
    }

    public void push(T item) {
        if (first == null) first = new Node(item);
        else {
            Node node = new Node(item);
            node.next = first;
            first = node;
        }
        size++;
    }

    public T pop() {
        if (first == null) {
            throw new RuntimeException("Stack is empty!");
        } else {
            Node top = first;
            first = first.next;
            top.next = null;
            size--;
            return top.item;
        }
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public Iterator<T> iterator() {
        return new myIterator();
    }

    private class myIterator implements Iterator<T> {

        Node cur = first;

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public T next() {
            T item = cur.item;
            cur = cur.next;
            return item;
        }

        @Override
        public void remove() {

        }
    }

}
