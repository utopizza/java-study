package dataStructure;

import java.util.Iterator;

public class Bag<T> implements Iterable<T> {

    private Node first;
    private int size;

    private class Node {
        T item;
        Node next;

        Node(T item) {
            this.item = item;
        }
    }

    public void add(T item) {
        if (first == null) first = new Node(item);
        else {
            Node newFirst = new Node(item);
            newFirst.next = first;
            first = newFirst;
        }
        size++;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int getSize() {
        return size;
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
