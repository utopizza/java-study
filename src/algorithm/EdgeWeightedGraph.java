package algorithm;

import dataStructure.*;

public class EdgeWeightedGraph {
    private int V;
    private int E;
    private Bag<Edge>[] adj;

    public EdgeWeightedGraph(int V) {
        this.V = V;
        this.E = 0;
        adj = new Bag[V];
        for (int v = 0; v < V; v++) adj[v] = new Bag<Edge>();
    }

    public int getV() {
        return V;
    }

    public int getE() {
        return E;
    }

    public void addEdge(Edge e) {
        int v = e.either(), w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public Iterable<Edge> getEdges() {
        Bag<Edge> b = new Bag<Edge>();
        for (int v = 0; v < V; v++) {
            for (Edge e : adj[v]) {
                if (e.other(v) > v) { // skip duplicate edges
                    b.add(e);
                }
            }
        }
        return b;
    }
}

class Edge implements Comparable<Edge> {
    private final int v;
    private final int w;
    private final double weight;

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        if (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new RuntimeException("Inconsistent edge.");
    }

    public int compareTo(Edge that) {
        if (this.getWeight() < that.getWeight()) return -1;
        else if (this.getWeight() == that.getWeight()) return 0;
        else return 1;
    }

    public String toString() {
        return String.format("%d-%d %.2f", v, w, weight);
    }
}

class LazyPrimMST {
    private boolean[] marked; // the vertex is in mst or not
    private Queue<Edge> mst; // store chosen edges (edges of the mst)
    private MinPQ<Edge> pq; // sort candidate edges

    public LazyPrimMST(EdgeWeightedGraph G) {
        marked = new boolean[G.getV()];
        mst = new Queue<Edge>();
        pq = new MinPQ<Edge>();

        addEdgesToMinPQ(G, 0); // can start from any vertex here
        while (!pq.isEmpty()) {
            Edge e = pq.delMin(); // get the minimum edge from MinPQ
            int v = e.either(), w = e.other(v);
            if (marked[v] && marked[w]) continue;
            mst.enqueue(e);
            if (!marked[v]) addEdgesToMinPQ(G, v);
            if (!marked[w]) addEdgesToMinPQ(G, w);
        }
    }

    private void addEdgesToMinPQ(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            if (!marked[e.other(v)]) { // find edges(v->w) which v is in tree but w is not in tree
                pq.insert(e); // add into MinPQ, so the candidate edges are arranged from small to large
            }
        }
    }

    public Iterable<Edge> getEdges() {
        return mst;
    }
}

class PrimMST {
    private Edge[] edgeTo;
    private double[] distTo; // distance from mst to the other vertex
    private boolean[] marked; // the vertex is in mst or not
    private IndexMinPQ<Double> pq; // store distances from mst to the other vertex: <x, distTo[x]>

    public PrimMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.getV()];
        distTo = new double[G.getV()];
        marked = new boolean[G.getV()];
        pq = new IndexMinPQ<Double>(G.getV());
        for (int v = 0; v < G.getV(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;

        distTo[0] = 0.0;
        pq.insert(0, 0.0);
        while (!pq.isEmpty())
            addEdges(G, pq.delMin());
    }

    private void addEdges(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (marked[w]) continue;
            if (e.getWeight() < distTo[w]) { // only add edge that makes mst smaller distance to vertex w
                edgeTo[w] = e;
                distTo[w] = e.getWeight();
                if (pq.contains(w)) pq.change(w, distTo[w]);
                else pq.insert(w, distTo[w]);
            }
        }
    }

    public Iterable<Edge> getEdges() {
        Bag<Edge> mst = new Bag<Edge>();
        for (int v = 1; v < edgeTo.length; v++) {
            mst.add(edgeTo[v]);
        }
        return mst;
    }
}

class KruskalMST {
    private Queue<Edge> mst;
    private MinPQ<Edge> pq;

    public KruskalMST(EdgeWeightedGraph G) {
        mst = new Queue<Edge>();
        pq = new MinPQ<Edge>();

        for (Edge e : G.getEdges())
            pq.insert(e); // sort edges from min to max

        UnionFind uf = new UnionFind(G.getV());
        while (!pq.isEmpty() && mst.getSize() < G.getV() - 1) {
            Edge e = pq.delMin(); // find the next minimum edge
            int v = e.either(), w = e.other(v);
            if (uf.connected(v, w)) continue; // if they are in one tree, then skip this edge
            uf.union(v, w); // otherwise, union these two trees which containing v and w respectively
            mst.enqueue(e);
        }
    }

    public Iterable<Edge> getEdges() {
        return mst;
    }
}