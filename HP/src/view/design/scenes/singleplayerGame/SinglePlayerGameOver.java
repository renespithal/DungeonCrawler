package view.design.scenes.singleplayerGame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import util.ViewUtils;



/**
 * Created by Felix on 15.02.2016.
 */
public class SinglePlayerGameOver extends BorderPane{

    VBox options = new VBox();
    Button restart = ViewUtils.newButton("Restart");
    Button exitToMenu = ViewUtils.newButton("Exit to MainMenu");
    ImageHolder images = new ImageHolder();



    public SinglePlayerGameOver(){

        restart.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        exitToMenu.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        
        restart.setOnMouseEntered(e ->{
            restart.setTextFill(Color.WHITE);
        });
        restart.setOnMouseExited(e ->{
            restart.setTextFill(Color.BLACK);
        });


        exitToMenu.setOnMouseEntered(e ->{
            exitToMenu.setTextFill(Color.WHITE);
        });
        exitToMenu.setOnMouseExited(e ->{
            exitToMenu.setTextFill(Color.LIGHTGRAY);
        });
        
        restart.setFont(new Font("Segoe UI Light", 40));
        restart.setTextFill(Color.BLACK);
        exitToMenu.setTextFill(Color.LIGHTGRAY);
        exitToMenu.setFont(new Font("Segoe UI Light", 40));

        options.getChildren().addAll(restart, exitToMenu);

        this.setBackground(new Background(new BackgroundFill(images.backgroundGOPattern, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setCenter(options);
        options.setAlignment(Pos.CENTER);
    }


}
