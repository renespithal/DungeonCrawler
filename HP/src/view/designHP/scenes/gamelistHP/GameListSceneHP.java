package view.designHP.scenes.gamelistHP;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import network.messages.SpielInfo;
import util.ViewUtilsHP;

/**
 * Created by Rusty on 18.12.2015.
 */
public class GameListSceneHP extends BorderPane {

	private Button join;
	private Button mainMenu;
	private Button server;
	private Scene actualScene;
	private Button create;
	TableView<SpielInfo> gamesTable;
	TableView<SpielInfo> playersTable;

	private VBox main, se, cre, jo;
	public GameListSceneHP() {
		this.setCenter(createMainArea());
		this.setBottom(createFooter());
        this.actualScene = ViewUtilsHP.createScene(this);
        ViewUtilsHP.setBackground(this, "hp/media/background/gameListBG.jpg");
        ViewUtilsHP.configureCSS(actualScene);
	}

	private Node createFooter() {
		create = ViewUtilsHP.createButton("Create Game");
		cre = ViewUtilsHP.buttonSurrounding(create);
		mainMenu = ViewUtilsHP.createButton("Back to Main");
		main = ViewUtilsHP.buttonSurrounding(mainMenu);
		server = ViewUtilsHP.createButton("Server");
		se = ViewUtilsHP.buttonSurrounding(server);
		join = ViewUtilsHP.createButton("Join");
		jo = ViewUtilsHP.buttonSurrounding(join);

		HBox hBox1 = new HBox(main,se);
		HBox hBox2 = new HBox(jo, cre);
		hBox1.setSpacing(100);
		hBox2.setSpacing(100);
		HBox hBox = new HBox(hBox1,hBox2);
		hBox.setSpacing(280);
		hBox.setPadding(new Insets(0,0,10,100));
		hBox.setAlignment(Pos.CENTER);

		return hBox;
	}

	private GridPane createMainArea() {

		GridPane tables = new GridPane();
		tables.add(createGamesTable(),0,0);
		tables.setAlignment(Pos.CENTER);
		
		return tables;
	}

	private Pane createGamesTable() {

		gamesTable = new TableView<>();

		ViewUtilsHP.createColumn(gamesTable, "ID", "gameID", 100);
		ViewUtilsHP.createColumn(gamesTable, "State", "gamestate", 100);
		ViewUtilsHP.createColumn(gamesTable, "Depth", "depth", 100);
		ViewUtilsHP.createColumn(gamesTable, "Difficulty", "difficulty", 100);
		ViewUtilsHP.createColumn(gamesTable, "# of Players", "playersCount", 100);
		
		gamesTable.setPlaceholder(new Label("no available games yet - please create a game"));

		gamesTable.setPrefHeight(300);
		
		Pane games = ViewUtilsHP.newPanel(false, "Available Games");
		games.getChildren().add(gamesTable);

		return games;
	}

	/* Getter */

	public Button getCreate() {
		return create;
	}

	public void setCreate( Button create ) {
		this.create = create;
	}

	public Button getJoin() {
		return join;
	}

	public Button getBackToMenu() {
		return mainMenu;
	}

	public TableView<SpielInfo> getGamesTable() {
		return gamesTable;
	}

	public TableView<SpielInfo> getPlayersTable() {
		return playersTable;
	}

	public Scene getActualScene() {
		return actualScene;
	}

	public void setActualScene( Scene actualScene ) {
		this.actualScene = actualScene;
	}

	public Button getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(Button mainMenu) {
		this.mainMenu = mainMenu;
	}

	public void setJoin(Button join) {
		this.join = join;
	}

	public Button getServer() {
		return server;
	}
}