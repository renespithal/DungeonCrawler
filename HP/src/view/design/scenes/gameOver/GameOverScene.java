package view.design.scenes.gameOver;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import util.ViewUtils;
import view.design.scenes.multiplayerGame.ImageHolder2;
import view.design.scenes.singleplayerGame.ImageHolder;

/**
 * Created by Felix on 17.02.2016.
 */
public class GameOverScene extends BorderPane {

    Scene actualScene;
    VBox options = new VBox();

    Button exitToMenu = ViewUtils.newButton("Exit to MainMenu");
    view.design.scenes.gameOver.ImageHolder images = new view.design.scenes.gameOver.ImageHolder();



    public GameOverScene(){


        exitToMenu.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));



        exitToMenu.setOnMouseEntered(e ->{
            exitToMenu.setTextFill(Color.WHITE);
        });
        exitToMenu.setOnMouseExited(e ->{
            exitToMenu.setTextFill(Color.LIGHTGRAY);
        });


        exitToMenu.setTextFill(Color.LIGHTGRAY);
        exitToMenu.setFont(new Font("Segoe UI Light", 40));

        options.getChildren().addAll( exitToMenu);

        this.setBackground(new Background(new BackgroundFill(images.backgroundGOPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setCenter(options);
        options.setAlignment(Pos.CENTER);


        actualScene = new Scene(this,1200,700);

    }

    public Scene getActualScene() {
        return actualScene;
    }

    public void setActualScene(Scene actualScene) {
        this.actualScene = actualScene;
    }

    public Button getExitToMenu() {
        return exitToMenu;
    }

    public void setExitToMenu(Button exitToMenu) {
        this.exitToMenu = exitToMenu;
    }


}
