import java.util.*;

public class MSTChecker {
    public static class UnionFind {
        public int[] parent;
        public int[] size;

        public UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int find(int p) {
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];
                p = parent[p];
            }
            return p;
        }

        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) {
                return;
            }
            if (size[rootP] < size[rootQ]) {
                parent[rootP] = rootQ;
                size[rootQ] += size[rootP];
            } else {
                parent[rootQ] = rootP;
                size[rootP] += size[rootQ];
            }
        }
    }

    public static boolean isMST(graph G) {
        int V = G.V();
        List<graph.Edge> edges = new ArrayList<>();
        for (int v = 0; v < V; v++) {
            for (graph.Edge e : G.adj(v)) {
                if (e.from() < e.to()) { // To avoid adding duplicate edges
                    edges.add(e);
                }
            }
        }
        Collections.sort(edges, Comparator.comparingDouble(e -> e.weight()));
        UnionFind uf = new UnionFind(V);
        double weight = 0;
        for (graph.Edge e : edges) {
            int v = e.from();
            int w = e.to();
            if (uf.find(v) != uf.find(w)) {
                uf.union(v, w);
                weight += e.weight();
            }
        }
        return uf.find(0) == uf.find(V - 1) && weight == getMSTWeight(G);
    }

    public static double getMSTWeight(graph G) {
        double weight = 0;
        boolean[] visited = new boolean[G.V()];
        PriorityQueue<graph.Edge> pq = new PriorityQueue<>(Comparator.comparingDouble(e -> e.weight()));
        pq.offer(new graph.Edge(0, 0, 0));
        while (!pq.isEmpty()) {
            graph.Edge e = pq.poll();
            int v = e.to();
            if (visited[v]) {
                continue;
            }
            visited[v] = true;
            weight += e.weight();
            for (graph.Edge f : G.adj(v)) {
                if (!visited[f.to()]) {
                    pq.offer(f);
                }
            }
        }
        return weight;
    }
}
