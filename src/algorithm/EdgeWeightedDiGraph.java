package algorithm;

import dataStructure.Bag;
import dataStructure.IndexMinPQ;
import dataStructure.Stack;

public class EdgeWeightedDiGraph {
    private int V;
    private int E;
    private Bag<DirectedEdge>[] adj;

    public EdgeWeightedDiGraph(int V) {
        this.V = V;
        this.E = 0;
        adj = new Bag[V];
        for (int v = 0; v < V; v++) adj[v] = new Bag<DirectedEdge>();
    }

    public int getV() {
        return V;
    }

    public int getE() {
        return E;
    }

    public void addEdge(DirectedEdge e) {
        adj[e.from()].add(e);
        E++;
    }

    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> bag = new Bag<DirectedEdge>();
        for (int v = 0; v < V; v++) {
            for (DirectedEdge e : adj[v]) {
                bag.add(e);
            }
        }
        return bag;
    }
}

class DirectedEdge {
    private final int v;
    private final int w;
    private final double weight;

    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }
}

class ShortestPath {
    private DirectedEdge[] edgeTo;
    private double[] distTo;

    // relax by edge
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[v] + e.getWeight() < distTo[w]) {
            distTo[w] = distTo[v] + e.getWeight();
            edgeTo[w] = e;
        }
    }

    // relax by vertex
    private void relax(EdgeWeightedDiGraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[v] + e.getWeight() < distTo[w]) {
                distTo[w] = distTo[v] + e.getWeight();
                edgeTo[w] = e;
            }
        }
    }
}

class Dijkstra {
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;

    public Dijkstra(EdgeWeightedDiGraph G, int s) {
        edgeTo = new DirectedEdge[G.getV()];
        distTo = new double[G.getV()];
        for (int v = 0; v < G.getV(); v++) distTo[v] = Double.POSITIVE_INFINITY;
        pq = new IndexMinPQ<Double>(G.getV());

        distTo[s] = 0.0;
        pq.insert(s, 0.0);
        while (!pq.isEmpty()) relax(G, pq.delMin());
    }

    private void relax(EdgeWeightedDiGraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[v] + e.getWeight() < distTo[w]) {
                distTo[w] = distTo[v] + e.getWeight();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.change(w, distTo[w]);
                else pq.insert(w, distTo[w]);
            }
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) path.push(e);
        return path;
    }
}

class DijkstraAllPairs {
    private Dijkstra[] all;

    public DijkstraAllPairs(EdgeWeightedDiGraph G) {
        all = new Dijkstra[G.getV()];
        for (int v = 0; v < G.getV(); v++) all[v] = new Dijkstra(G, v);
    }

    public Iterable<DirectedEdge> path(int s, int t) {
        return all[s].pathTo(t);
    }

    public double dist(int s, int t) {
        return all[s].distTo(t);
    }
}

class BellmanFord {
    private double[] distTo;
    private DirectedEdge[] edgeTo;

    public BellmanFord(EdgeWeightedDiGraph G, int s) {
        edgeTo = new DirectedEdge[G.getV()];
        distTo = new double[G.getV()];
        for (int v = 0; v < G.getV(); v++) distTo[v] = Double.POSITIVE_INFINITY;
        for (int pass = 0; pass < G.getV(); pass++) {
            for (int v = 0; v < G.getV(); v++) {
                relax(G, v);
            }
        }
    }

    private void relax(EdgeWeightedDiGraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[v] + e.getWeight() < distTo[w]) {
                distTo[w] = distTo[v] + e.getWeight();
                edgeTo[w] = e;
            }
        }
    }
}