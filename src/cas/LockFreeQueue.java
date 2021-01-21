package cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Random;

public class LockFreeQueue<T> implements Iterable<T> {

    private final static class Node<T> {
        volatile T item;
        volatile Node<T> next;

        Node(T item) {
            this.item = item;
        }

        private static final Unsafe unsafe;
        private static final long itemOffset;
        private static final long nextOffset;

        static {
            unsafe = getUnsafe();
            try {
                itemOffset = unsafe.objectFieldOffset(Node.class.getDeclaredField("item"));
                nextOffset = unsafe.objectFieldOffset(Node.class.getDeclaredField("next"));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                throw new Error(e);
            }
        }

        public boolean casNext(Node<T> oldNext, Node<T> newNext) {
            return unsafe.compareAndSwapObject(this, nextOffset, oldNext, newNext);
        }
    }

    private volatile Node<T> head, tail;
    private static final Unsafe unsafe;
    private static final long headOffset;
    private static final long tailOffset;

    static {
        try {
            unsafe = getUnsafe();
            headOffset = unsafe.objectFieldOffset(LockFreeQueue.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset(LockFreeQueue.class.getDeclaredField("tail"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    private static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Can not get unsafe.");
        }
    }

    public LockFreeQueue() {
        head = new Node(null);
        tail = head;
    }

    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        Node<T> p = tail;
        do {
            while (p.next != null) p = p.next; // make sure p.next==null(p is the last node)
        } while (!p.casNext(null, newNode));
        casTail(tail, newNode);
    }

    public T dequeue() {
        Node<T> p = head;
        do {
            p = head;
            if (p.next == null) throw new RuntimeException("Can not dequeue an empty queue.");
        } while (casHead(p, p.next));
        return p.item;
    }

    private boolean casHead(Node oldHead, Node newHead) {
        return unsafe.compareAndSwapObject(this, headOffset, oldHead, newHead);
    }

    private boolean casTail(Node oldTail, Node newTail) {
        return unsafe.compareAndSwapObject(this, tailOffset, oldTail, newTail);
    }

    // iterator

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private class MyIterator<T> implements Iterator<T> {

        Node cur = head.next;

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public T next() {
            T item = (T) cur.item;
            cur = cur.next;
            return item;
        }

        @Override
        public void remove() {
        }
    }
}

class Test {

    private static LockFreeQueue<Integer> queue = new LockFreeQueue<>();
    private static Random random = new Random();

    private static class MyRunner implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                queue.enqueue(i);
                try {
                    Thread.sleep(random.nextInt(5));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new MyRunner());
            thread.start();
        }

        Thread.sleep(5000);
        for (int x : queue) {
            System.out.print(x + " ");
        }
    }
}