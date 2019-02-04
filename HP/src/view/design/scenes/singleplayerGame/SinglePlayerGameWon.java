package view.design.scenes.singleplayerGame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * Created by Felix on 15.02.2016.
 */
public class SinglePlayerGameWon extends BorderPane{

    VBox options = new VBox();
    Label gameWonLabel = new Label("Game Completed");
    Button playAgain = new Button("Play Again");
    Button exitToMenu = new Button("Exit to MainMenu");
    ImageHolder images = new ImageHolder();



    public SinglePlayerGameWon(){

        options.getChildren().addAll(gameWonLabel, playAgain, exitToMenu);

       // this.setBackground(new Background(new BackgroundFill(images.backgroundGWPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setCenter(options);
        options.setAlignment(Pos.CENTER);


    }



}
