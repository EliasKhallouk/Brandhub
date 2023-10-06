
import control.BrandubhController;
import boardifier.control.StageFactory;
import boardifier.model.Model;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import view.BrandubhRootPane;
import view.BrandubhView;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import javafx.scene.media.MediaPlayer;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;



public class Brandubh extends Application {


    protected MediaPlayer media_player;

    public static int mode;
    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                mode = Integer.parseInt(args[0]);
                if ((mode <0) || (mode>3)) {
                    System.out.println("Argument must be an integer between 0 and 3");
                }
            }
            catch(NumberFormatException e) {
                System.out.println("Argument must be an integer");
            }
        }
        else{
            mode = -1;
        }
        launch(args);
    }

    public int getMode() {
        return mode;
    }





    @Override
    public void start(Stage stage) throws Exception {

        //String musicFile = "gamemusic.mp3"; // Spécifiez le chemin d'accès à votre fichier audio

        //File file = new File(musicFile);
        //System.out.println(file);
        //Media sound = new Media(new File("/src/img/gamemusic.mp3").toURI().toString());
        //MediaPlayer mediaPlayer = new MediaPlayer(sound);

        //mediaPlayer.play();

        // create the global model
        Model model = new Model();
        // add some players taking mode into account
        // register a single stage for the game, called Brandubh
        StageFactory.registerModelAndView("Brandubh", "model.BrandubhStageModel", "view.BrandubhStageView");
        // create the root pane, using the subclass BrandubhRootPane
        BrandubhRootPane rootPane = new BrandubhRootPane();
        // create the global view.
        BrandubhView view = new BrandubhView(model, stage, rootPane);
        // create the controllers.
        BrandubhController control = new BrandubhController(model,view,mode);

        // set the name of the first stage to create when the game is started
        control.setFirstStageName("Brandubh");
        // set the stage title
        stage.setTitle("The Brandubh");

        model.setIdWinner(-1);
        // show the JavaFx main stage
        stage.show();

        //view.resetView();
    }

}
