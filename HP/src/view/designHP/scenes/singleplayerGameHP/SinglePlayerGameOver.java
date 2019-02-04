package view.designHP.scenes.singleplayerGameHP;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import util.ViewUtilsHP;

/**
 * Created by Felix on 15.02.2016.
 */
public class SinglePlayerGameOver extends BorderPane {

	// ImageHolder images = new ImageHolder();

	public SinglePlayerGameOver() {

		VBox buttons = createButtons(); 

		// TODO: GameOver background setzen
		// this.setBackground(new Background(new
		// BackgroundFill(images.backgroundGOPattern, CornerRadii.EMPTY,
		// Insets.EMPTY)));
		
		this.setCenter(buttons);
		buttons.setAlignment(Pos.CENTER);

	}
	
	private VBox createButtons() {
		
		VBox buttons = new VBox();
		Label gameOverLabel = new Label("GameOver");
		gameOverLabel.setStyle("-fx-font: 10");
		Button restart = ViewUtilsHP.createButton("Restart");
		Button exitToMenu = ViewUtilsHP.createButton("Back to Menu");
		buttons.getChildren().addAll(gameOverLabel, restart, exitToMenu);
		
		return buttons;
	}

}
