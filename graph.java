    //-----------------------------------------------------
    // Title: Graph
    // Author: Feyzi Eren Gündoğdu
    // ID: 52417418978
    // Section: 1
    // Assignment: 1
    // Description: This is the graph class which inculdes graph data strucutre abd bag data structure.
    //-----------------------------------------------


import java.util.*;

@SuppressWarnings("unchecked")
public class graph {
    public int V; // V is the amount of vertices
    public List<Edge>[] adj; // adj is the adjacency list
    public double[] weight; // weight is the weight of each edge
    
    public graph(int V) { // constructor
        this.V = V;
        adj = (List<Edge>[]) new List[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<Edge>();
        }
        weight = new double[V];
    }
    
    public void addE(int v, int w, double weight) { // method that adds edges to the graph
        adj[v].add(new Edge(v, w, weight));
    }

    public Iterable<Edge> adj(int v) { // returns the adjacency list of a vertex
        return adj[v];
    }

    public int V() {// returns the amount of vertices
        return V;
    }

    public boolean isCyclicUtil(int v, boolean[] visited, boolean[] recStack) {
        visited[v] = true;
        recStack[v] = true;

        for (Edge e : adj(v)) {
            int w = e.to();
            if (!visited[w] && isCyclicUtil(w, visited, recStack)) {
                return true;
            } else if (recStack[w]) {
                return true;
            }
        }

        recStack[v] = false;
        return false;
    }

    public boolean isCyclic() {
        boolean[] visited = new boolean[V];
        boolean[] recStack = new boolean[V];
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                if (isCyclicUtil(i, visited, recStack)) {
                    return true;
                }
            }
        }
        return false;
    }

    public class Edge {
        public final int v, w;
        public final double weight;

        public Edge(int v, int w, double weight) {
            this.v = v;
            this.w = w;
            this.weight = weight;
        }

        public int from() {
            return v;
        }

        public int to() {
            return w;
        }

        public double weight() {
            return weight;
        }

        public String toString() {
            return v + "->" + w + " (" + weight + ")";
        }
    }
}
