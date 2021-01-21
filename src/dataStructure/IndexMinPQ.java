package dataStructure;

public class IndexMinPQ<Key extends Comparable<Key>> {
    private int N;  // count of pq
    private int[] pq; // heap;
    private int[] qp; // qp[pq[i]]=pq[qp[i]]=i, as a hashMap: <pq.key(qp.index), pq.index(qp.key)>
    private Key[] keys;

    public IndexMinPQ(int maxN) {
        keys = (Key[]) new Comparable[maxN + 1];
        pq = new int[maxN + 1];
        qp = new int[maxN + 1];
        for (int i = 0; i <= maxN; i++) qp[i] = -1;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public boolean contains(int k) {
        return qp[k] != -1;
    }

    private void exchange(int i, int j) {
        int temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    private boolean less(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) <= 0;
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

    public void insert(int k, Key key) {
        N++;
        qp[k] = N;
        pq[N] = k; // k is the index of key in keys[], this makes the connection
        keys[k] = key;
        swim(N);
    }

    public Key min() {
        return keys[pq[1]];
    }

    public int delMin() {
        int indexOfMin = pq[1];
        exchange(1, N--);
        sink(1);
        keys[pq[N + 1]] = null;
        qp[pq[N + 1]] = -1;
        return indexOfMin;
    }

    public int minIndex() {
        return pq[1];
    }

    public void change(int k, Key key) {
        keys[k] = key;
        swim(qp[k]);
        sink(qp[k]); //???
    }

    public void delete(int k) {
        int index = qp[k];
        exchange(index, N--);
        swim(index);
        sink(index);
        keys[k] = null;
        qp[k] = -1;
    }
}
