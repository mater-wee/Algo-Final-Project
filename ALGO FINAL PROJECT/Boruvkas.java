import java.util.*;

public class Boruvkas {

    static class Edge {
        int u, v, w;
        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    static int find(int[] p, int i) {
        if (p[i] != i)
            p[i] = find(p, p[i]);
        return p[i];
    }

    static void union(int[] p, int[] r, int a, int b) {
        a = find(p, a);
        b = find(p, b);

        if (r[a] < r[b]) p[a] = b;
        else if (r[a] > r[b]) p[b] = a;
        else {
            p[b] = a;
            r[a]++;
        }
    }

    static char name(int i) {
        return (char) ('A' + i);
    }

    public static void main(String[] args) {

        int V = 7;

        // Edge list
        List<Edge> e = new ArrayList<>();
        e.add(new Edge(3,4,1)); // D-E
        e.add(new Edge(1,3,2)); // B-D
        e.add(new Edge(1,4,2)); // B-E
        e.add(new Edge(0,2,2)); // A-C
        e.add(new Edge(2,4,2)); // C-E
        e.add(new Edge(4,5,2)); // E-F
        e.add(new Edge(1,2,3)); // B-C
        e.add(new Edge(2,6,3)); // C-G
        e.add(new Edge(0,6,4)); // A-G
        e.add(new Edge(2,5,4)); // C-F
        e.add(new Edge(3,5,4)); // D-F
        e.add(new Edge(0,1,5)); // A-B
        e.add(new Edge(5,6,6)); // F-G

        int[] p = new int[V]; // parent
        int[] r = new int[V]; // rank

        for (int i = 0; i < V; i++) {
            p[i] = i;
            r[i] = 0;
        }

        int trees = V, cost = 0;

        while (trees > 1) {

            Edge[] cheap = new Edge[V];

            for (Edge ed : e) {
                int a = find(p, ed.u);
                int b = find(p, ed.v);

                if (a != b) {
                    if (cheap[a] == null || cheap[a].w > ed.w)
                        cheap[a] = ed;

                    if (cheap[b] == null || cheap[b].w > ed.w)
                        cheap[b] = ed;
                }
            }

            for (int i = 0; i < V; i++) {
                Edge ed = cheap[i];

                if (ed != null) {
                    int a = find(p, ed.u);
                    int b = find(p, ed.v);

                    if (a != b) {
                        System.out.println(name(ed.u) + "-" +
                                           name(ed.v) +
                                           " : " + ed.w);

                        cost += ed.w;
                        union(p, r, a, b);
                        trees--;
                    }
                }
            }
        }

        System.out.println("Total = " + cost);
    }
}