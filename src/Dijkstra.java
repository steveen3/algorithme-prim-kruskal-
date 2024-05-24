import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.Scanner;
import java.util.Stack;

public class Dijkstra {
    static int minDistance(int dist[], Boolean sptSet[]) {
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < dist.length; v++)
            if (!sptSet[v] && dist[v] < min) {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }

    public static Pane dijkstra(int graph[][]) {
        int V = graph.length;
        int dist[] = new int[V];
        int pred[] = new int[V]; // Tableau des précédents
        Boolean sptSet[] = new Boolean[V];

        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }

        // Demander le sommet de départ et d'arrivée à l'utilisateur
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez le sommet de départ : ");
        int src = scanner.nextInt();
        System.out.print("Entrez le sommet d'arrivée : ");
        int dest = scanner.nextInt();
        scanner.close();

        dist[src] = 0;

        Pane pane = new Pane();

        for (int count = 0; count < V - 1; count++) {
            int u = minDistance(dist, sptSet);

            sptSet[u] = true;

            for (int v = 0; v < V; v++) {
                if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                    pred[v] = u; // Mise à jour du prédécesseur
                }
            }
        }

        // Affichage du chemin le plus court entre src et dest
        printShortestPath(pred, src, dest);

        // Affichage des arêtes sur le pane
        for (int i = 1; i < V; i++) {
            Line line = new Line();
            line.setStartX((i - 1) * 100);
            line.setStartY(dist[i - 1]);
            line.setEndX(i * 100);
            line.setEndY(dist[i]);
            pane.getChildren().add(line);
        }

        return pane;
    }

    public static void printShortestPath(int pred[], int src, int dest) {
        Stack<Integer> path = new Stack<>();

        int crawl = dest;
        path.push(crawl);
        while (pred[crawl] != src) {
            path.push(pred[crawl]);
            crawl = pred[crawl];
        }
        path.push(src);

        System.out.println("Chemin le plus court de " + src + " à " + dest + ":");
        while (!path.isEmpty()) {
            System.out.print(path.pop() + " ");
        }
        System.out.println();
    }
}
