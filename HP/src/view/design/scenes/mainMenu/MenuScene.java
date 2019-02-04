package view.design.scenes.mainMenu;

import java.net.MalformedURLException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.ViewUtils;

public class MenuScene extends BorderPane {

	// Controls
	private Label titleLabel;
	private Button singleplayerButton;
	private Button multiplayerButton;
	private Button startServer;
	private Button exitButton;
	private Scene actualScene;
	private Button infoButton;

	/**
	 * Creates the view of the main menu.
	 */
	public MenuScene() throws MalformedURLException {

		//titleLabel = ViewUtils.newH1("Main Menu");
		singleplayerButton = ViewUtils.newMainButton("Singleplayer", "singleplayer");
		multiplayerButton = ViewUtils.newMainButton("Multiplayer", "multiplayer");
		startServer = ViewUtils.newMainButton("Start Server", "server");
		exitButton = ViewUtils.newMainButton("Exit", "exit");
		infoButton = ViewUtils.newMainButton("Info","info");
//		HBox hBox = new HBox(titleLabel);
//		hBox.setAlignment(Pos.CENTER);

		VBox vBox = new VBox( singleplayerButton, multiplayerButton, startServer, exitButton,infoButton);
		vBox.setPadding(new Insets(0,0,150,0));
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(15);

		this.setCenter(vBox);
		actualScene = ViewUtils.createScene(this);
		ViewUtils.configureScene(actualScene);
	}

	public Scene getActualScene() {
		return actualScene;
	}

	public void setActualScene( Scene actualScene ) {
		this.actualScene = actualScene;
	}

	public Button getSingleplayerButton() {
		return singleplayerButton;
	}

	public Button getMultiplayerButton() {
		return multiplayerButton;
	}

	public Button getExitButton() {
		return exitButton;
	}

	public Button getStartServer() {
		return startServer;
	}

	public Button getInfoButton() {
		return infoButton;
	}

	public void setInfoButton(Button infoButton) {
		this.infoButton = infoButton;
	}

	public void setStartServer(Button startServer ) {
		this.startServer = startServer;
	}
}
