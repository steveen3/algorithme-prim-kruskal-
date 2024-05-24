import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import java.util.Scanner;

public class PrimMST {
    private static int startVertex;

    static int minKey(int key[], Boolean mstSet[]) {
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < key.length; v++)
            if (!mstSet[v] && key[v] < min) {
                min = key[v];
                min_index = v;
            }

        return min_index;
    }

    public static Pane primMST(int graph[][]) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez le sommet de départ : ");
        startVertex = scanner.nextInt();

        int V = graph.length;
        int parent[] = new int[V];
        int key[] = new int[V];
        Boolean mstSet[] = new Boolean[V];

        // Initialisation des clés et des ensembles MST
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        // Initialisation de la clé du sommet de départ à 0 pour qu'il soit sélectionné en premier
        key[startVertex] = 0;
        parent[startVertex] = -1;

        Pane pane = new Pane();

        // Construction de l'arbre couvrant minimum
        for (int count = 0; count < V - 1; count++) {
            int u = minKey(key, mstSet);

            // Ajout du sommet u à l'ensemble MST
            mstSet[u] = true;

            // Mise à jour des valeurs de clé et des parents des sommets adjacents
            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && !mstSet[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        int totalWeight = 0;

        // Création des lignes pour représenter les arêtes de l'arbre couvrant minimum
        for (int i = 0; i < V; i++) {
            if (i != startVertex && parent[i] != -1) {
                Line line = new Line();
                line.setStartX(parent[i] * 100);
                line.setStartY(0);
                line.setEndX(i * 100);
                line.setEndY(0);
                pane.getChildren().add(line);
                totalWeight += graph[i][parent[i]];
            }
        }

        // Affichage du résultat sur la console
        System.out.println("Arêtes de l'arbre couvrant de poids minimum (Prim) :");
        for (int i = 0; i < V; i++) {
            if (i != startVertex && parent[i] != -1) {
                System.out.println(parent[i] + " - " + i + " : " + graph[i][parent[i]]);
            }
        }
        System.out.println("Somme des poids des arêtes minimales : " + totalWeight);

        return pane;
    }
}
