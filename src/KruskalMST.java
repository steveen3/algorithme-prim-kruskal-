import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import java.util.Arrays;

public class KruskalMST {
    static class Edge implements Comparable<Edge> {
        int src, dest, weight;

        public Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        public int compareTo(Edge compareEdge) {
            return this.weight - compareEdge.weight;
        }
    }

    static class Subset {
        int parent, rank;
    }

    static int minKey(int key[], Boolean mstSet[]) {
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < key.length; v++)
            if (!mstSet[v] && key[v] < min) {
                min = key[v];
                min_index = v;
            }

        return min_index;
    }

    static void Union(Subset subsets[], int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    static int find(Subset subsets[], int i) {
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);
        return subsets[i].parent;
    }

    public static Pane kruskal(int graph[][]) {
        int V = graph.length;
        int parent[] = new int[V];
        int key[] = new int[V];
        Boolean mstSet[] = new Boolean[V];

        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        key[0] = 0;
        parent[0] = -1;

        Pane pane = new Pane();

        for (int count = 0; count < V - 1; count++) {
            int u = minKey(key, mstSet);

            mstSet[u] = true;

            for (int v = 0; v < V; v++)
                if (graph[u][v] != 0 && !mstSet[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
        }

        int totalWeight = 0;

        for (int i = 1; i < V; i++) {
            Line line = new Line();
            line.setStartX(parent[i] * 100);
            line.setStartY(0);
            line.setEndX(i * 100);
            line.setEndY(0);
            pane.getChildren().add(line);
            totalWeight += graph[i][parent[i]];
        }

        // Affichage du résultat sur la console
        System.out.println("Arêtes de l'arbre couvrant de poids minimum (Kruskal) :");
        for (int i = 1; i < V; i++) {
            System.out.println(parent[i] + " - " + i + " : " + graph[i][parent[i]]);
        }
        System.out.println("Somme des poids des arêtes minimales : " + totalWeight);

        return pane;
    }
}
