package view.designHP.scenes.singleplayerGameHP;

import util.ViewUtilsHP;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Created by Felix on 15.02.2016.
 */
public class SinglePlayerGameWon extends BorderPane {

	// ImageHolder images = new ImageHolder();

	public SinglePlayerGameWon() {

		// TODO: GameWon Background setzen
		// this.setBackground(new Background(new
		// BackgroundFill(images.backgroundGWPattern, CornerRadii.EMPTY,
		// Insets.EMPTY)));
		VBox buttons = createButtons();
		this.setCenter(buttons);
		buttons.setAlignment(Pos.CENTER);
	}

	private VBox createButtons() {

		VBox buttons = new VBox();
		Label gameWonLabel = new Label("Game Won");
		Button playAgain = ViewUtilsHP.createButton("Play Again");
		Button exitToMenu = ViewUtilsHP.createButton("Back to Menu");
		buttons.getChildren().addAll(gameWonLabel, playAgain, exitToMenu);

		return buttons;
	}
}
