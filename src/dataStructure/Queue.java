package dataStructure;

import java.util.Iterator;

public class Queue<T> implements Iterable<T> {

    private Node first, last;
    private int size;

    private class Node {
        T item;
        Node next;

        Node(T item) {
            this.item = item;
        }
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return getSize() == 0;
    }

    public void enqueue(T item) {
        if (first == null) first = last = new Node(item);
        else {
            last.next = new Node(item);
            last = last.next;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) throw new RuntimeException("Queue is empty.");
        else {
            Node temp = first;
            if (getSize() == 1) first = last = null;
            else first = first.next;
            temp.next = null;
            size--;
            return temp.item;
        }
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
