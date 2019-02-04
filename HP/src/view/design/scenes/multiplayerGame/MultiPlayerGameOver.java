package view.design.scenes.multiplayerGame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import util.ViewUtils;
import view.design.scenes.singleplayerGame.ImageHolder;

/**
 * Created by Felix on 15.02.2016.
 */
public class MultiPlayerGameOver extends BorderPane{

    VBox options = new VBox();
    Button restart = ViewUtils.newButton("Restart");
    Button exitToMenu = ViewUtils.newButton("Exit to MainMenu");
    ImageHolder2 images = new ImageHolder2();



    public MultiPlayerGameOver(){

        options.getChildren().addAll(restart, exitToMenu);

        this.setBackground(new Background(new BackgroundFill(images.backgroundGOPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setCenter(options);
        options.setAlignment(Pos.CENTER);
    }


}
