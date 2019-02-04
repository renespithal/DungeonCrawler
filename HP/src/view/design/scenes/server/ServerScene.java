package view.design.scenes.server;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import network.game.GameLobby;
import network.server.connection.threads.ServerHandler;
import util.ViewUtils;

/**
 * Created by sarah on 09.01.2016.
 */
public class ServerScene extends BorderPane {

	private Scene actualScene;
	private Button gotoMain;
	private Button client;
	private Button startServer;
	private Button stopServer;
	
	private Button serverStarted;
	private Button serverStopped;
	
	private ImageView viewStart;
	private ImageView viewStop;

	private TextArea control;

	TableView<ServerHandler> clientsTable;
	TableView<GameLobby> gamesTable;
	
	public ServerScene() {

		VBox table = new VBox(createMainArea());
		VBox area = new VBox(control);
		area.setAlignment(Pos.CENTER_RIGHT);

		this.setTop(createHeader());
		this.setCenter(createMainArea());

		this.setBottom(createFooter());

		actualScene = ViewUtils.createScene(this);
		ViewUtils.configureScene(actualScene);
	}

	private Node createHeader() {

		return ViewUtils.createHeader("Server");
	}
	
	private Node createFooter() {
		
		gotoMain = ViewUtils.newButton("Back to Main");
		client = ViewUtils.newButton("Client");
		startServer = ViewUtils.newButton("start");
		stopServer = ViewUtils.newButton("stop");
		
		HBox hBox = new HBox(gotoMain, client, startServer, stopServer);
		hBox.setAlignment(Pos.CENTER);
		hBox.setSpacing(15);
		return hBox;
	}
	
	private GridPane createMainArea() {
		
        GridPane tables = new GridPane();
        tables.add(createClientTable(), 0, 1);
        tables.add(createGamesTable(), 3, 1);
		tables.add(createTextArea(), 0, 2);
		tables.add(createSymbols(), 3,2);
		
		tables.setAlignment(Pos.CENTER);


		return tables;
	}
	
	private Pane createSymbols() {
		
		serverStarted = ViewUtils.newMainButton("started", "serverStarted");
		serverStarted.setDisable(true);
		
		serverStopped = ViewUtils.newMainButton("stopped", "serverStopped");
		serverStopped.setDisable(true);
		
		HBox symbolBox = new HBox();
		symbolBox.getChildren().addAll(serverStarted, serverStopped);
		
		Pane symbols = ViewUtils.newPanel(false, "Server Status");
		symbols.getChildren().add(symbolBox);
		
		return symbols;
	}

	private Pane createClientTable() {
		
		clientsTable = new TableView<ServerHandler>();
		clientsTable.setEditable(false);
		clientsTable.setFocusTraversable(false);
		clientsTable.setPrefHeight(200);
		
		ViewUtils.createColumn(clientsTable, "Nick", "nick", 150);
		ViewUtils.createColumn(clientsTable, "IP", "remoteAddress", 200);
		ViewUtils.createColumn(clientsTable, "Port", "remotePort", 150);
		
		clientsTable.setPlaceholder(new Label("no clients connnected"));

		Pane connectedClients = ViewUtils.newPanel(false, "Connected Clients");
		connectedClients.getChildren().add(clientsTable);

		return connectedClients;
	}

	private Pane createGamesTable() {

		gamesTable = new TableView<GameLobby>();

		ViewUtils.createColumn(gamesTable, "ID", "gameID", 100);
		ViewUtils.createColumn(gamesTable, "State", "gamestate", 100);
		ViewUtils.createColumn(gamesTable, "Depth", "depth", 100);
		ViewUtils.createColumn(gamesTable, "Difficulty", "difficulty", 100);
		ViewUtils.createColumn(gamesTable, "# of Players", "playersCount", 100);
		
		gamesTable.setPlaceholder(new Label("no games created"));

		Pane games = ViewUtils.newPanel(false, "Available Games");
		games.getChildren().add(gamesTable);
		gamesTable.setPrefHeight(200);
		return games;
	}


	public Pane createTextArea() {

		control = ViewUtils.createTextArea();
		Pane controlPane = ViewUtils.newPanel(false,"Server Control");
		controlPane.getChildren().add(control);

		return controlPane;

	}

	/* Getter */

	public Scene getActualScene() {
		return actualScene;
	}

	public void setActualScene(Scene actualScene) {
		this.actualScene = actualScene;
	}

	public Button getGotoMain() {
		return gotoMain;
	}

	
	public Button getStartServer() {
		return startServer;
	}

	
	public Button getStopServer() {
		return stopServer;
	}

	
	public TableView<ServerHandler> getClientsTable() {
		return clientsTable;
	}

	
	public TableView<GameLobby> getGamesTable() {
		return gamesTable;
	}

	
	public ImageView getViewStart() {
		return viewStart;
	}

	
	public void setViewStart( ImageView viewStart ) {
		this.viewStart = viewStart;
	}

	
	public ImageView getViewStop() {
		return viewStop;
	}

	
	public void setViewStop( ImageView viewStop ) {
		this.viewStop = viewStop;
	}


	public TextArea getControl() {
		return control;
	}

	public void setControl(TextArea control) {
		this.control = control;
	}

	
	public Button getServerStarted() {
		return serverStarted;
	}

	
	public void setServerStarted( Button serverStarted ) {
		this.serverStarted = serverStarted;
	}

	
	public Button getServerStopped() {
		return serverStopped;
	}

	
	public void setServerStopped( Button serverStopped ) {
		this.serverStopped = serverStopped;
	}

	
	public Button getClient() {
		return client;
	}

	
	public void setClient( Button client ) {
		this.client = client;
	}
	
}
