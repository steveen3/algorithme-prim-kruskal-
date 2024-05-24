import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main extends Application {
    private static final int RADIUS = 20;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 400;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Zone de texte pour saisir la matrice adjacente
        TextArea textArea = new TextArea();
        textArea.setPromptText("Entrez la matrice adjacente...");
        textArea.setPrefColumnCount(20);
        textArea.setPrefRowCount(10);

        // Bouton pour générer le graphe
        Button generateButton = new Button("Générer le graphe");
        generateButton.setOnAction(e -> {
            int[][] matriceAdjacente = parseMatrice(textArea.getText());
            Pane graphePane = generateGraphe(matriceAdjacente, textArea.getWidth(), textArea.getHeight());
            root.setCenter(graphePane);
        });
        

       Button kruskalButton = new Button("Kruskal");
kruskalButton.setOnAction(e -> {
    int[][] matriceAdjacente = parseMatrice(textArea.getText());
   Pane graphePane = KruskalMST.kruskal(matriceAdjacente);
    // Afficher le résultat dans un pane
    VBox resultPane = new VBox();
    resultPane.getChildren().addAll(new Label("Résultat de l'algorithme de Kruskal :"), graphePane);
    root.setCenter(resultPane);
});

/////////////

Button primButton = new Button("prim");
primButton.setOnAction(e -> {
    int[][] matriceAdjacente = parseMatrice(textArea.getText());
   Pane graphePane = PrimMST.primMST(matriceAdjacente);;
    // Afficher le résultat dans un pane
    VBox resultPane = new VBox();
    resultPane.getChildren().addAll(new Label("Résultat de l'algorithme de Prim:"), graphePane);
    root.setCenter(resultPane);
});
/////////////////////
Button dijkstraButton = new Button("dijkstra");
dijkstraButton.setOnAction(e -> {
    int[][] matriceAdjacente = parseMatrice(textArea.getText());
   Pane graphePane = Dijkstra.dijkstra(matriceAdjacente);
    // Afficher le résultat dans un pane
    VBox resultPane = new VBox();
    resultPane.getChildren().addAll(new Label("Résultat de l'algorithme de Prim:"), graphePane);
    root.setCenter(resultPane);
});


        VBox leftBox = new VBox(10, new Label("Matrice :"), textArea, generateButton, kruskalButton,primButton,dijkstraButton);
        root.setLeft(leftBox);

        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.setTitle("Graph from Adjacency Matrix");
        primaryStage.show();
    }

    private int[][] parseMatrice(String input) {
        String[] lignes = input.split("\n");
        int[][] matrice = new int[lignes.length][];
        for (int i = 0; i < lignes.length; i++) {
            String[] valeurs = lignes[i].trim().split("\\s+");
            matrice[i] = new int[valeurs.length];
            for (int j = 0; j < valeurs.length; j++) {
                matrice[i][j] = Integer.parseInt(valeurs[j]);
            }
        }
        return matrice;
    }

    private Pane generateGraphe(int[][] matriceAdjacente, double width, double height) {
        Pane root = new Pane();

        // Dessiner les sommets
        Circle[] sommets = new Circle[matriceAdjacente.length];
        for (int i = 0; i < matriceAdjacente.length; i++) {
            double angle = Math.toRadians(360.0 / matriceAdjacente.length * i);
            double x = width / 2 + width / 3 * Math.cos(angle);
            double y = height / 2 + height / 3 * Math.sin(angle);

            Circle circle = new Circle(x, y, RADIUS, Color.WHITE);
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(2.0);

            Label label = new Label(String.valueOf(i ));
            label.setLayoutX(x - 5);
            label.setLayoutY(y - 5);

            root.getChildren().addAll(circle, label);
            sommets[i] = circle;
        }

        // Dessiner les arêtes
        for (int i = 0; i < matriceAdjacente.length; i++) {
            for (int j = i + 1; j < matriceAdjacente.length; j++) {
                int poids = matriceAdjacente[i][j];
                if (poids != 0) {
                    double x1 = sommets[i].getCenterX();
                    double y1 = sommets[i].getCenterY();
                    double x2 = sommets[j].getCenterX();
                    double y2 = sommets[j].getCenterY();

                    Line line = new Line(x1, y1, x2, y2);
                    line.setStrokeWidth(poids * 0.1); // Ajuster l'épaisseur en fonction du poids
                    line.setStroke(Color.BLACK);
                    line.setStrokeLineCap(StrokeLineCap.ROUND);
                    root.getChildren().add(line);

                    Label label = new Label(String.valueOf(poids));
                    label.setLayoutX((x1 + x2) / 2 - 5);
                    label.setLayoutY((y1 + y2) / 2 - 5);

                    root.getChildren().add(label);
                }
            }
        }

        // Placer le graphe dans un cadre
        Pane cadre = new Pane(root);
        cadre.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        cadre.setPrefSize(width, height);

        return cadre;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
