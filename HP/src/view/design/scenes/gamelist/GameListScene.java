package view.design.scenes.gamelist;

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
import network.messages.SpielInfo;
import util.ViewUtils;

/**
 * Created by Rusty on 18.12.2015.
 */
public class GameListScene extends BorderPane {

	private Button join;
	private Button mainMenu;
	private Button server;
	private Scene actualScene;
	private Button create;
	TableView<SpielInfo> gamesTable;
	TableView<SpielInfo> playersTable;

	public GameListScene() {

		this.setTop(createHeader());
		this.setCenter(createMainArea());
		this.setBottom(createFooter());
		actualScene = ViewUtils.createScene(this);
		ViewUtils.configureScene(actualScene);
	}

	private Node createHeader() {

		return ViewUtils.createHeader("Multiplayer");
	}

	private Node createFooter() {
		create = ViewUtils.newButton("Create Game");
		server = ViewUtils.newButton("Server");
		server.setDisable(true);
		mainMenu = ViewUtils.newButton("Back to Main");
		join = ViewUtils.newButton("Join");

		HBox hBox = new HBox(mainMenu, server, join, create);
		hBox.setSpacing(10);
		hBox.setAlignment(Pos.CENTER);

		return hBox;
	}

	private GridPane createMainArea() {

		GridPane tables = new GridPane();
		tables.add(createGamesTable(), 0, 0);
		tables.setAlignment(Pos.CENTER);
		
		return tables;
	}

	private Pane createGamesTable() {

		gamesTable = new TableView<>();

		ViewUtils.createColumn(gamesTable, "ID", "gameID", 100);
		ViewUtils.createColumn(gamesTable, "State", "gamestate", 100);
		ViewUtils.createColumn(gamesTable, "Depth", "depth", 100);
		ViewUtils.createColumn(gamesTable, "Difficulty", "difficulty", 100);
		ViewUtils.createColumn(gamesTable, "# of Players", "playersCount", 100);
		
		gamesTable.setPlaceholder(new Label("no available games yet - please create a game"));

		gamesTable.setPrefHeight(300);
		
		Pane games = ViewUtils.newPanel(false, "Available Games");
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

	
	public Button getServer() {
		return server;
	}

	
	public void setServer( Button server ) {
		this.server = server;
	}
}