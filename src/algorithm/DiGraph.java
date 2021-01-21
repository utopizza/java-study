package algorithm;

import dataStructure.Bag;
import dataStructure.Queue;
import dataStructure.Stack;

public class DiGraph {
    private int V;
    private int E;
    private Bag<Integer>[] adj;

    public DiGraph(int V) {
        this.V = V;
        this.E = 0;
        adj = new Bag[V];
        for (int v = 0; v < V; v++) adj[v] = new Bag<Integer>();
    }

    public DiGraph(int[][] matrix) {
        this.V = matrix.length;
        this.E = 0;
        adj = new Bag[V];
        for (int v = 0; v < V; v++) adj[v] = new Bag<Integer>();
        for (int v = 0; v < V; v++) {
            for (int w = 0; w < V; w++) {
                if (matrix[v][w] != 0) {
                    addEdge(v, w);
                }
            }
        }
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
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

    public DiGraph reverse() {
        DiGraph R = new DiGraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                R.addEdge(w, v);
            }
        }
        return R;
    }
}

class FindDiPathDFS {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;

    public FindDiPathDFS(DiGraph G, int s) {
        marked = new boolean[G.getV()];
        this.s = s;
        dfs(G, s);
    }

    private void dfs(DiGraph G, int v) {
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
        Stack<Integer> stack = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x]) stack.push(x);
        stack.push(s);
        return stack;
    }
}

class FindDiPathBFS {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;

    public FindDiPathBFS(DiGraph G, int s) {
        marked = new boolean[G.getV()];
        this.s = s;
        bfs(G, s);
    }

    private void bfs(DiGraph G, int s) {
        Queue<Integer> queue = new Queue<Integer>();
        queue.enqueue(s);
        marked[s] = true;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    queue.enqueue(w);
                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> stack = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x]) stack.push(x);
        stack.push(s);
        return stack;
    }
}

class FindDiCycle {
    private boolean[] marked;
    private boolean[] onStack;
    private int[] edgeTo;
    private Stack<Integer> cycle;

    public FindDiCycle(DiGraph G) {
        marked = new boolean[G.getV()];
        onStack = new boolean[G.getV()];
        edgeTo = new int[G.getV()];
        for (int v = 0; v < G.getV(); v++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    private void dfs(DiGraph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (this.hasCycle()) return;
            else if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            } else if (onStack[w]) { // find a cycle
                cycle = new Stack<Integer>();
                for (int x = v; x != w; x = edgeTo[x]) cycle.push(x);
                cycle.push(w);
                cycle.push(v);
            }
        }
        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> getOneCycle() {
        return cycle;
    }
}

class TopologySortDFS {
    private boolean[] marked;
    private Stack<Integer> stack;
    private Queue<Integer> topologyOrder;

    public TopologySortDFS(DiGraph G) {
        FindDiCycle cycleFinder = new FindDiCycle(G);
        if (cycleFinder.hasCycle())
            throw new RuntimeException("Topology sort can not contain any cycle.");
        else {
            marked = new boolean[G.getV()];
            stack = new Stack<Integer>();
            topologyOrder = new Queue<Integer>();
            for (int v = 0; v < G.getV(); v++) {
                if (!marked[v]) {
                    dfs(G, v);
                }
            }
        }
    }

    private void dfs(DiGraph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
        // all nodes from v have been visited
        // use stack here because v will be in front of them in topologyOrder
        stack.push(v);
    }

    public Iterable<Integer> getTopologyOrder() {
        while (!stack.isEmpty()) topologyOrder.enqueue(stack.pop());
        return topologyOrder;
    }
}

class TopologySortBFS {
    //private boolean[] marked;
    private int[] inDegree;
    private Queue<Integer> topologyOrder;

    public TopologySortBFS(DiGraph G) {
        FindDiCycle cycleFinder = new FindDiCycle(G);
        if (cycleFinder.hasCycle())
            throw new RuntimeException("Topology sort can not contain any cycle.");
        else {
            //marked = new boolean[G.getV()];
            inDegree = new int[G.getV()];
            topologyOrder = new Queue<Integer>();
            statisticInDegree(G);
            bfs(G);
        }
    }

    private void statisticInDegree(DiGraph G) {
        for (int v = 0; v < G.getV(); v++) {
            for (int w : G.adj(v)) {
                inDegree[w]++;
            }
        }
    }

    private void bfs(DiGraph G) {
        Queue<Integer> queue = new Queue<Integer>();
        for (int v = 0; v < G.getV(); v++) {
            if (inDegree[v] == 0) {
                queue.enqueue(v);
            }
        }
        while (queue.getSize() > 0) {
            int v = queue.dequeue(); // delete v from G as well as all edges start from v
            topologyOrder.enqueue(v);
            for (int w : G.adj(v)) {
                inDegree[w]--;
                if (inDegree[w] == 0) {
                    queue.enqueue(w);
                }
            }
        }
    }

    public Iterable<Integer> getTopologyOrder() {
        return topologyOrder;
    }
}

class FindSCC {
    // tarjan
    // kosaraju
}