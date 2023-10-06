package view;

import boardifier.control.Controller;
import boardifier.model.Model;
import boardifier.view.RootPane;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BrandubhRootPane extends RootPane {


    public Button playButton;

    public Model model;
    public ComboBox<String> combo;

    public BrandubhRootPane() {
        super();
    }



    @Override
    public void createDefaultGroup() {

        /*Rectangle frame = new Rectangle(600, 600, Color.LIGHTGREY);
        Text text = new Text("Playing to The Brandubh");
        text.setFont(new Font(15));
        text.setFill(Color.BLACK);
        text.setX(10);
        text.setY(50);
        // put shapes in the group
        group.getChildren().clear();
        group.getChildren().addAll(frame, text);*/


        Pane menuPane = new Pane();

        menuPane.setPrefSize(1325, 745);
            Path currentPath = Paths.get("").toAbsolutePath();

            // Vérifie si le chemin se termine par "Brandubh"
            if (currentPath.endsWith("Brandubh")) {
            } else {
                // Ajoute "Brandubh" à la fin du chemin
                currentPath = currentPath.resolve("/Brandubh");
            }








        BackgroundSize backgroundSize = new BackgroundSize(1375, 750, false, false, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("file:"+currentPath+"/src/img/background.jpeg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize
        );
        Background background = new Background(backgroundImage);
        menuPane.setBackground(background);







        GridPane menuGridPane = new GridPane();
        menuGridPane.setPrefSize(300, 500);
        menuGridPane.setAlignment(Pos.CENTER);
        menuGridPane.setHgap(20);
        menuGridPane.setVgap(30);
        menuGridPane.setLayoutX(0);
        menuGridPane.setTranslateX(512.5);
        menuGridPane.setLayoutY(0);
        menuGridPane.setTranslateY(122.5);

        Text titre_home = new Text("HOME");
        titre_home.setFont(Font.font("Roboto Light", FontWeight.BOLD, 32));
        titre_home.setFill(Color.WHITE);
        menuGridPane.add(titre_home, 0, 0, 2, 1); // Utilise GridPane.setColumnSpan() pour spécifier 2 colonnes

// Centre horizontalement le titre dans la première colonne
        GridPane.setHalignment(titre_home, HPos.CENTER);
        GridPane.setValignment(titre_home, VPos.TOP);



        combo = new ComboBox<>(FXCollections.observableArrayList(
                "Human vs Human", "Human vs IA white", "Human vs IA black", "IA vs IA"));
        combo.getSelectionModel().select(0);
        combo.setStyle("-fx-background-color: white;");
        menuGridPane.add(combo, 0, 2, 2, 1);

        playButton = new Button("PLAY");
        playButton.setStyle("-fx-background-color: white;");
        menuGridPane.add(playButton, 0, 1, 3, 1);


        menuGridPane.setStyle("-fx-background-color: rgba(128, 128, 128, 0.80);");
        menuGridPane.setPadding(new Insets(0, 0, 0, 0));

        group.getChildren().add(menuGridPane);



        menuPane.getChildren().add(menuGridPane);

        menuGridPane.setLayoutX((menuPane.getWidth() - menuGridPane.getWidth()) / 2);

// Centre le menuGridPane verticalement
        menuGridPane.setLayoutY((menuPane.getHeight() - menuGridPane.getHeight()) / 2);




        // put shapes in the group
        group.getChildren().clear();
        group.getChildren().addAll(menuPane);
    }

    public Button getPlayButton() {
        return playButton;
    }


}
