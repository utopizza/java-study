package dataStructure;

public class UnionFind {

    private int[] id;
    private int[] size;
    private int count;

    public UnionFind(int N) {
        this.count = N;
        this.id = new int[N];
        for (int i = 0; i < N; i++) id[i] = i;
        for (int i = 0; i < N; i++) size[i] = 1;
    }

    public int getCount() {
        return count;
    }

    public boolean connected(int p, int q) {
        return find_WeightedQuickUnion(p) == find_WeightedQuickUnion(q);
    }

    public void union(int p, int q) {
        union_WeightedQuickUnion(p, q);
    }


    // Quick-Find : O(n^2)
    private int find_QuickFind(int p) {
        return id[p];
    }

    private void union_QuickFind(int p, int q) {
        int pId = find_QuickFind(p);
        int qId = find_QuickFind(q);
        if (pId == qId) return;
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pId) id[i] = qId;
        }
        count--;
    }


    // Quick-Union : best O(n) worst O(n^2)
    private int find_QuickUnion(int p) {
        while (p != id[p]) p = id[p];
        return p;
    }

    private void union_QuickUnion(int p, int q) {
        int pRoot = find_QuickUnion(p);
        int qRoot = find_QuickUnion(q);
        if (pRoot == qRoot) return;
        id[pRoot] = qRoot;
        count--;
    }


    // Weighted-Quick-Union : O(lgn)
    private int find_WeightedQuickUnion(int p) {
        while (p != id[p]) p = id[p];
        return p;
    }

    private void union_WeightedQuickUnion(int p, int q) {
        int pRoot = find_WeightedQuickUnion(p);
        int qRoot = find_WeightedQuickUnion(q);
        if (pRoot == qRoot) return;
        else if (size[pRoot] < size[qRoot]) { // small tree union to big tree
            id[pRoot] = qRoot;
            size[qRoot] += size[pRoot];
        } else {
            id[qRoot] = pRoot;
            size[pRoot] += size[qRoot];
        }
        count--;
    }

}
