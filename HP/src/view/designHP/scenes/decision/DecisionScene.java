package view.designHP.scenes.decision;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import util.ViewUtils;


public class DecisionScene extends BorderPane{
	
	private Scene scene;
	private Label titleLabel;
	private Button harryPotter;
	private Button dungeonCrawler;
	
	public DecisionScene() {
		
		titleLabel = new Label("Please choose game version");

		dungeonCrawler = new Button();
		dungeonCrawler.setBackground(createDCButton());
		dungeonCrawler.setPrefSize(1200,338);

		harryPotter = new Button();
		harryPotter.setBackground(createHPButton());
		harryPotter.setPrefSize(1200,338);

		HBox hBox = new HBox(titleLabel);
		hBox.setAlignment(Pos.CENTER);
		
		VBox vBox = new VBox(dungeonCrawler, harryPotter);
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(15);

		this.setCenter(vBox);
		scene = ViewUtils.createScene(this);
		}

	public Background createHPButton() {
		Image img = new Image("choiceButton/decisionHP.jpg");
		BackgroundImage image = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background background = new Background(image);
		return background;
	}
	public Background createDCButton() {
		Image img = new Image("choiceButton/decisionDC.jpg");
		BackgroundImage image = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background background = new Background(image);
		return background;
	}

	public Scene getActualScene() {
		return scene;
	}

	
	public void setScene( Scene scene ) {
		this.scene = scene;
	}

	
	public Label getTitleLabel() {
		return titleLabel;
	}

	
	public void setTitleLabel( Label titleLabel ) {
		this.titleLabel = titleLabel;
	}

	
	public Button getHarryPotter() {
		return harryPotter;
	}

	
	public void setHarryPotter( Button harryPotter ) {
		this.harryPotter = harryPotter;
	}

	
	public Button getDungeonCrawler() {
		return dungeonCrawler;
	}

	
	public void setDungeonCrawler( Button dungeonCrawler ) {
		this.dungeonCrawler = dungeonCrawler;
	}
}
