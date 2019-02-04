package view.designHP.scenes.startSceneHP;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import util.ViewUtilsHP;
import view.design.scenes.MusicPlayer;

/**
 * Created by Jenny on 09.01.2016.
 */
public class StartSceneHP {

    private Scene scene;
    private BorderPane root = new BorderPane();
    private VBox next;
    private MusicPlayer mediaplayer;

    public StartSceneHP() {


        setPositions();
        ViewUtilsHP.setBackground(root, "hp/media/background/startBG.jpg");
        this.scene = ViewUtilsHP.createScene(root);
    }

    private void setPositions() {
       
        root.setBottom(pressKey());
    }

    private VBox pressKey() {
        Text press = ViewUtilsHP.createH1("press enter to continue");
        //press.setTextAlignment(TextAlignment.CENTER);

        //effect
        FadeTransition fadeOut = ViewUtilsHP.createFade(1500, press, true, 0.5, 0.0);
        fadeOut.play();

        FadeTransition fadeIn = ViewUtilsHP.createFade(1500, press, true, 0.0, 0.5);
        fadeIn.play();

        fadeOut.setOnFinished(event -> fadeIn.play());
        fadeIn.setOnFinished(event -> fadeOut.play());

        VBox box = new VBox(press, ViewUtilsHP.createPlaceholder(1280, 150));
        box.setAlignment(Pos.CENTER);
        
        return box;
    }



    /* Getter & Setter */

    public void music(){
        mediaplayer = new MusicPlayer();
        mediaplayer.playHarryPotterIntro();
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public BorderPane getRoot() {
        return root;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }

    public VBox getNext() {
        return next;
    }

    public void setNext(VBox next) {
        this.next = next;
    }
}
