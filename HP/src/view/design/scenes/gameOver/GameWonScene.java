package view.design.scenes.gameOver;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import util.ViewUtils;
import view.design.scenes.singleplayerGame.*;

/**
 * Created by Felix on 17.02.2016.
 */
public class GameWonScene extends BorderPane {

    Scene actualScene;
    VBox options = new VBox();
    Label gameWonLabel = new Label("Game Completed");

    Button exitToMenu = new Button("Exit to MainMenu");
    view.design.scenes.singleplayerGame.ImageHolder images = new view.design.scenes.singleplayerGame.ImageHolder();



    public GameWonScene(){

        options.getChildren().addAll(gameWonLabel, exitToMenu);

        // this.setBackground(new Background(new BackgroundFill(images.backgroundGWPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setCenter(options);
        options.setAlignment(Pos.CENTER);


        actualScene = ViewUtils.createScene(this);
        ViewUtils.configureScene(actualScene);


    }

    public Scene getActualScene() {
        return actualScene;
    }

    public Button getExitToMenu() {
        return exitToMenu;
    }
}
