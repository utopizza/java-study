package algorithm;

import dataStructure.Bag;
import dataStructure.Queue;
import dataStructure.Stack;

public class Graph {
    private int E;
    private int V;
    private Bag<Integer>[] adj;

    public Graph(int[][] matrix) {
        this.V = matrix.length;
        this.E = 0;
        adj = new Bag[V];
        for (int v = 0; v < V; v++) adj[v] = new Bag<Integer>();
        for (int v = 0; v < V; v++) {
            for (int w = 0; w < v; w++) {
                if (matrix[v][w] == 1) {
                    addEdge(v, w);
                }
            }
        }
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public int getE() {
        return E;
    }

    public int getV() {
        return V;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }
}

class FindPathDFS {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;

    public FindPathDFS(Graph G, int s) {
        marked = new boolean[G.getV()];
        edgeTo = new int[G.getV()];
        this.s = s;
        dfs(G, s);
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x]) path.push(x);
        path.push(s);
        return path;
    }
}

class FindPathBFS {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;

    public FindPathBFS(Graph G, int s) {
        marked = new boolean[G.getV()];
        edgeTo = new int[G.getV()];
        this.s = s;
        bfs(G, s);
    }

    private void bfs(Graph G, int s) {
        marked[s] = true;
        Queue<Integer> queue = new Queue<Integer>();
        queue.enqueue(s);
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : G.adj(v)) {
                if (marked[w]) continue;
                edgeTo[w] = v;
                marked[w] = true;
                queue.enqueue(w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (hasPathTo(v)) {
            Stack<Integer> path = new Stack<Integer>();
            for (int x = v; x != s; x = edgeTo[x]) path.push(x);
            path.push(s);
            return path;
        }
        return null;
    }
}

class FindCC {
    private boolean[] marked;
    private int[] componentId;
    private int componentCount;

    public FindCC(Graph G) {
        marked = new boolean[G.getV()];
        componentId = new int[G.getV()];
        componentCount = 0;
        for (int v = 0; v < G.getV(); v++) {
            dfs(G, v);
            componentCount++; // different id for nodes in different dfs
        }
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        componentId[v] = componentCount;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    public int getComponentCount() {
        return componentCount;
    }

    public int getComponentId(int v) {
        return componentId[v];
    }

    public boolean isConnected(int v, int w) {
        return componentId[v] == componentId[w];
    }
}