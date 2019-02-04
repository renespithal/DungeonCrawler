package view.design.scenes.startscreen;


import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import util.ViewUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.HBox;
import view.design.scenes.MusicPlayer;

import static java.lang.Thread.sleep;


/**
 * Created by Felix on 14.01.2016.
 */
public class StartScreenScene extends BorderPane {

    private Scene startscene;
    private Button start;
    private MusicPlayer mediaPlayer;


    private HBox logo;

    public StartScreenScene (){

        pressKey();
      

        startscene = ViewUtils.createScene(this);

        ViewUtils.configureScene(startscene);

        startscene.getRoot().setId("scene-start");
    }

    private void pressKey() {
        Text press = new Text("press enter to continue");
        press.setStyle("-fx-font-size: 250%");
        press.setFill(Color.BLACK);
        press.setTextAlignment(TextAlignment.CENTER);
        //press.setOpacity(0.5);


        VBox text = new VBox(press);
        VBox place = new VBox();
        place.setPrefHeight(100);

        HBox all = new HBox(text, place);
        all.setAlignment(Pos.CENTER);
        this.setBottom(all);


        //effect
        FadeTransition fadeOut = new FadeTransition(Duration.millis(2000), press);
        fadeOut.setToValue(0.0);
        fadeOut.setAutoReverse(true);
        fadeOut.play();

        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), press);
        fadeIn.setToValue(1);
        fadeIn.setAutoReverse(true);

        fadeOut.setOnFinished(event -> fadeIn.play());

        fadeIn.setOnFinished(event -> fadeOut.play());
    }



    /* Getter & Setter */

    public void music(){mediaPlayer = new MusicPlayer();
    mediaPlayer.playIntro();}

    public Scene getStartscene() {
        return startscene;
    }

    public void setStartscene(Scene startscene) {
        this.startscene = startscene;
    }

    public Button getStart() {
        return start;
    }

    public void setStart(Button start) {
        this.start = start;
    }
}
