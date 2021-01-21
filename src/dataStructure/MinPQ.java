package dataStructure;

public class MinPQ<Key extends Comparable<Key>> {
    private Key[] pq;
    private int N = 0; // pq[1...N], not using pq[0]

    public MinPQ() {
        pq = (Key[]) new Comparable[100];
    }

    public MinPQ(int cap) {
        pq = (Key[]) new Comparable[cap + 1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int getSize() {
        return N;
    }

    private void exchange(int i, int j) {
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) <= 0;
    }

    private void swim(int k) {
        while (k > 1 && less(k, k / 2)) { // exchange if k is smaller than its parent
            exchange(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (k * 2 <= N) {
            int child = k * 2;
            if (child + 1 <= N && less(child + 1, child)) child++; // choose the smallest child
            if (!less(child, k)) break; // if the smallest child is larger than k, no need to sink
            exchange(k, child);
            k = child;
        }
    }

    public void insert(Key k) {
        pq[++N] = k;
        swim(N); // recover heap
        if (N >= pq.length - 1) resize(pq.length * 2);
    }

    public Key delMin() {
        Key min = pq[1];
        exchange(1, N--);
        pq[N + 1] = null;
        sink(1); // recover heap
        if (N <= pq.length / 4) resize(pq.length / 2);
        return min;
    }

    private void resize(int newLength) {
        Key[] temp = (Key[]) new Object[newLength + 1];
        for (int i = 0; i <= N; i++) temp[i] = pq[i];
        pq = temp;
    }

}
