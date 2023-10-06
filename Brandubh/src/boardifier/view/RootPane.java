package boardifier.view;

import boardifier.model.GameElement;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Collections;

public class RootPane extends Pane {

    protected GameStageView gameStageView;
    protected Group group; // the group that contains all game elements of the current stage
    protected ComboBox<String> combo;
    protected Button playButton;

    public RootPane() {
        this.gameStageView = null;
        group = new Group();
        //setBackground(Background.EMPTY);
        resetToDefault();
    }

    public final void resetToDefault() {
        createDefaultGroup();
        // add the group to the pane
        getChildren().clear();
        getChildren().add(group);
    }

    public final void resetToDefaultAbout() {
        createDefaultGroupAbout();
        // add the group to the pane
        getChildren().clear();
        getChildren().add(group);
    }

    public final void resetToDefaultRule() {
        createDefaultGroupRule();
        // add the group to the pane
        getChildren().clear();
        getChildren().add(group);
    }

    /**
     * create the element of the default group
     * This method can be overriden to define a different visual aspect.
     */
    protected void createDefaultGroup() {
        Rectangle frame = new Rectangle(100, 100, Color.LIGHTGREY);
        // remove existing children
        group.getChildren().clear();
        // adding default ones
        group.getChildren().addAll(frame);
    }

    protected void createDefaultGroupAbout() {
        Pane menuPane = new Pane();

        menuPane.setPrefSize(1325, 745);

        BackgroundSize backgroundSize = new BackgroundSize(1375, 750, false, false, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("file:src/img/background.jpeg"),
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

        Text titre_home = new Text("ABOUT US");
        titre_home.setFont(Font.font("Roboto Light", FontWeight.BOLD, 32));
        titre_home.setFill(Color.WHITE);
        menuGridPane.add(titre_home, 0, 0, 2, 1); // Utilise GridPane.setColumnSpan() pour spécifier 2 colonnes

        // Centre horizontalement le titre dans la première colonne
        GridPane.setHalignment(titre_home, HPos.CENTER);
        GridPane.setValignment(titre_home, VPos.TOP);





        Text text= new Text(10.0,10.0,"Hello, here is the game BRANDUBH made by the group 13, it is composed of Khallouk Elias, Mechroubi M'hammed, Tajani Ayoub as well as Reymond Calixte. We are all computer BUT students at the IUT Nord Franche-Comté.");
        text.setWrappingWidth(565);
        text.setTextAlignment(TextAlignment.JUSTIFY);
        text.setFont(Font.font("Roboto Light", FontWeight.BOLD, 16));
        text.setFill(Color.WHITE);

        menuGridPane.add(text, 0, 1, 3, 1);


        menuGridPane.setStyle("-fx-background-color: rgba(128, 128, 128, 0.80);");
        menuGridPane.setPadding(new Insets(0, 0, 0, 0));

        group.getChildren().add(menuGridPane);



        menuPane.getChildren().add(menuGridPane);

        menuGridPane.setLayoutX(-200);

// Centre le menuGridPane verticalement
        menuGridPane.setLayoutY((menuPane.getHeight() - menuGridPane.getHeight()) / 2);




        // put shapes in the group
        group.getChildren().clear();
        group.getChildren().addAll(menuPane);
    }


    protected void createDefaultGroupRule() {
        Pane menuPane = new Pane();
        ScrollPane scrollPane = new ScrollPane();
        GridPane menuGridPane = new GridPane();


        menuGridPane.add(scrollPane, 0, 1, 3, 1);
        menuPane.setPrefSize(1325, 745);

        BackgroundSize backgroundSize = new BackgroundSize(1375, 750, false, false, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("file:src/img/background.jpeg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize
        );
        Background background = new Background(backgroundImage);
        menuPane.setBackground(background);



        menuGridPane.setPrefSize(300, 500);
        menuGridPane.setAlignment(Pos.CENTER);
        menuGridPane.setHgap(00);
        menuGridPane.setVgap(30);
        menuGridPane.setLayoutX(0);
        menuGridPane.setTranslateX(0);
        menuGridPane.setLayoutY(0);
        menuGridPane.setTranslateY(122.5);
        Text titre_home = new Text("RULE OF THE GAME");
        titre_home.setFont(Font.font("Roboto Light", FontWeight.BOLD, 32));
        titre_home.setFill(Color.WHITE);
        menuGridPane.add(titre_home, 0, 0, 3, 1); // Utilise GridPane.setColumnSpan() pour spécifier 2 colonnes

        // Centre horizontalement le titre dans la première colonne
        GridPane.setHalignment(titre_home, HPos.CENTER);


        //CONTEXT
        Text titre_context = new Text(12.0, 5.0, "Context");
        titre_context.setFont(Font.font("Roboto Light",FontWeight.BOLD, 24));
        titre_context.setFill(Color.WHITE);
        Text text_context = new Text(10.0, 10.0, "This game probably appeared in Ireland, following the numerous contacts between the\n" +
                "the Viking navy and the Irish.\n" +
                "This hunting game is inspired by \"Tablut\", a game between the Swedes and the Muscovites,\n" +
                "evoking the conflicts around the Baltic Sea.\n" +
                "Descriptions of the Irish game can be found in poems such as \"Abair riom a Éire\n" +
                "ógh\" by Maoil Eóin Mac Raith. Here, the particular piece is called \"Branán,\" which means \"chief.\n" +
                "meaning \"chief.\" The literary style allows to date the appearance of the game between 1200 and\n" +
                "1640.\n" +
                "Brandub usually consists of five defensive pieces on the one hand and eight\n" +
                "attackers on the other. The forces involved vary with the size of the board. We\n" +
                "present here the Mac White model.");
        text_context.setWrappingWidth(565);
        text_context.setTextAlignment(TextAlignment.JUSTIFY);
        text_context.setFont(Font.font("Roboto Light", FontWeight.BOLD, 16));
        text_context.setFill(Color.WHITE);

        //MATERIAL
        Text titre_material = new Text(12.0, 5.0, "\nMaterial");
        titre_material.setFont(Font.font("Roboto Light",FontWeight.BOLD, 24));
        titre_material.setFill(Color.WHITE);
        Text text_material = new Text(10.0, 10.0, "1 game board, 8 soldiers of one color (attackers), 4 soldiers of another color (the defenders), 1 branán.");
        text_material.setWrappingWidth(565);
        text_material.setTextAlignment(TextAlignment.JUSTIFY);
        text_material.setFont(Font.font("Roboto Light", FontWeight.BOLD, 16));
        text_material.setFill(Color.WHITE);

        //GOAL OF THE GAME
        Text titre_goal = new Text(12.0, 5.0, "\nGoal of the game");
        titre_goal.setFont(Font.font("Roboto Light",FontWeight.BOLD, 24));
        titre_goal.setFill(Color.WHITE);
        Text text_goal = new Text(10.0, 10.0, "\nThe attackers must capture the branán.\n" +
                "The defenders must take their branán to a corner of the game.");
        text_goal.setWrappingWidth(565);
        text_goal.setTextAlignment(TextAlignment.JUSTIFY);
        text_goal.setFont(Font.font("Roboto Light", FontWeight.BOLD, 16));
        text_goal.setFill(Color.WHITE);

        //COURSE OF ACTION
        Text titre_course = new Text(12.0, 5.0, "\nCourse of action");
        titre_course.setFont(Font.font("Roboto Light",FontWeight.BOLD, 24));
        titre_course.setFill(Color.WHITE);
        Text text_course = new Text(10.0, 10.0, "PREPARATION\n" +
                "The branán is installed in the center of the game, protected by his 4 soldiers. The attackers surround the defenders.\n"+
                "\nTHE MOVEMENTS\n" +
                "The soldiers and the branán all move horizontally or vertically\n" +
                "vertically, of as many squares as desired. It is forbidden to\n" +
                "jump over a piece.\n" +
                "The central square is strictly forbidden to all soldiers. Only the branán can stop on this square: it is his throne.\n"+
                "\nTHE CAPTURES\n" +
                "The capture of an opponent's soldier is done by lateral framing. When a pawn is surrounded on two opposite edges (right and left or top and bottom), it is captured. For example, black piece #1 captures the white soldier by moving to his right.\n" +
                "If the throne is free, a soldier can be captured between an opposing soldier and the throne. Thus, soldier #2 can capture the white soldier near the throne by moving to his left.\n" +
                "Any soldier voluntarily going between two enemies is not captured. The capture is always the result of an attack. Soldier #3 is not captured if he goes between the two black soldiers.\n"+
                "\nEND OF THE GAME\n" +
                "The capture of the branán is successful with 4 soldiers (one on each side). The attackers win the game.\n" +
                "The branán must reach one of the corners of the board to ensure the victory to the defenders.");
        text_course.setWrappingWidth(565);
        text_course.setTextAlignment(TextAlignment.JUSTIFY);
        text_course.setFont(Font.font("Roboto Light", FontWeight.BOLD, 16));
        text_course.setFill(Color.WHITE);

        VBox vbox2 = new VBox();
        vbox2.getChildren().addAll(titre_context,text_context,titre_material,text_material,titre_goal,text_goal,titre_course,text_course);
        vbox2.setSpacing(10);
        vbox2.setPadding(new Insets(10, 10, 10, 10));
        vbox2.setStyle("-fx-background-color: rgba(128, 128, 128, 0.80);");
        vbox2.setPrefWidth(800);
        vbox2.setPrefHeight(800);
        vbox2.setAlignment(Pos.CENTER);

        scrollPane.setContent(vbox2);
        scrollPane.setPrefViewportWidth(800);
        scrollPane.setPrefViewportHeight(800);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);



        menuGridPane.setStyle("-fx-background-color: rgba(128, 128, 128, 0.80);");
        menuGridPane.setPadding(new Insets(0, 0, 0, 0));
        menuGridPane.setAlignment(Pos.CENTER);

        menuGridPane.setPrefWidth(800);

        group.getChildren().add(menuPane);





        menuPane.getChildren().add(menuGridPane);

        menuGridPane.setLayoutX((menuPane.getWidth() - menuGridPane.getWidth()) / 2+250);
        menuGridPane.setLayoutY((menuPane.getHeight() - menuGridPane.getHeight()) / 2);





        // put shapes in the group
        group.getChildren().clear();
        group.getChildren().addAll(menuPane);
    }
    /**
     * Initialize the content of the group.
     * It takes the elements of the model, which are initialized when starting a game stage.
     * It sorts them so that the element with the highest depth are put in first in the group.
     * So they will be hidden by elements with a lower depth.
     */
    public final void init(GameStageView gameStageView) {
        if (gameStageView != null) {
            this.gameStageView = gameStageView;
            // first sort element by their depth
            Collections.sort(gameStageView.getLooks(), (a, b) -> a.getDepth() - b.getDepth());
            // remove existing children
            group.getChildren().clear();
            // add game element looks
            for (ElementLook look : gameStageView.getLooks()) {
                Group group = look.getGroup();
                this.group.getChildren().add(group);
            }
            // add the group to the pane
            getChildren().clear();
            getChildren().add(group);
        }
    }

    /* ***************************************
       TRAMPOLINE METHODS
    **************************************** */

    public ElementLook getElementLook(GameElement element) {
        if (gameStageView == null) return null;
        return gameStageView.getElementLook(element);
    }
    public void update() {
        if (gameStageView == null) return;
        gameStageView.update();
    }
}
