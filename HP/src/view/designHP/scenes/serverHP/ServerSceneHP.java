package view.designHP.scenes.serverHP;

import javafx.geometry.Insets;
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
import util.ViewUtilsHP;

/**
 * Created by sarah on 09.01.2016.
 */
public class ServerSceneHP extends BorderPane {

	private Scene actualScene;
	private Button gotoMain;
	private Button goToClient;
	private Button startServer;
	private Button stopServer;
	
	private Button serverStarted;
	private Button serverStopped;

	private VBox main, client, start, stop;
	
	private ImageView viewStart;
	private ImageView viewStop;

	private TextArea control;

	TableView<ServerHandler> clientsTable;
	TableView<GameLobby> gamesTable;
	
	public ServerSceneHP() {

		setPositions();
		actualScene = ViewUtilsHP.createScene(this);
		ViewUtilsHP.setBackground(this, "hp/media/background/ServerSceneBG.jpg");
		ViewUtilsHP.configureCSS(actualScene);
	}

	private void setPositions() {
		VBox table = new VBox(createMainArea());
		VBox area = new VBox(control);
		area.setAlignment(Pos.CENTER_RIGHT);

		this.setCenter(createMainArea());
		this.setBottom(createFooter());
	}

	
	private Node createFooter() {
		
		gotoMain = ViewUtilsHP.createButton("Back to Main");
		main = ViewUtilsHP.buttonSurrounding(gotoMain);
		goToClient = ViewUtilsHP.createButton("Client");
		client = ViewUtilsHP.buttonSurrounding(goToClient);
		startServer = ViewUtilsHP.createButton("start");
		start = ViewUtilsHP.buttonSurrounding(startServer);
		stopServer = ViewUtilsHP.createButton("stop");
		stop = ViewUtilsHP.buttonSurrounding(stopServer);

		HBox hBox1 = new HBox(main, client);
		HBox hBox2 = new HBox(start, stop);
		hBox1.setSpacing(50);
		hBox2.setSpacing(100);
		hBox2.setPadding(new Insets(0,0,0,100));
		HBox hBox = new HBox(hBox1,hBox2);
		hBox.setAlignment(Pos.CENTER);

		hBox.setSpacing(280);
		hBox.setPadding(new Insets(0,0,10,100));
		return hBox;
	}
	
	private GridPane createMainArea() {
		
        GridPane tables = new GridPane();
        tables.add(createClientTable(), 0, 1);
        tables.add(createGamesTable(), 0, 2);
		tables.add(createTextArea(), 3, 2);
		tables.add(createSymbols(), 3,1);
		
		tables.setAlignment(Pos.CENTER);


		return tables;
	}
	
	private Pane createSymbols() {
		
		serverStarted = ViewUtilsHP.newMainButton("started", "serverStarted");
		serverStarted.setDisable(true);
		
		serverStopped = ViewUtilsHP.newMainButton("stopped", "serverStopped");
		serverStopped.setDisable(true);
		
		HBox symbolBox = new HBox();
		symbolBox.getChildren().addAll(serverStarted, serverStopped);
		
		Pane symbols = ViewUtilsHP.newPanel(false, "Server Status");
		symbols.getChildren().add(symbolBox);
		
		return symbols;
	}

	private Pane createClientTable() {
		
		clientsTable = new TableView<ServerHandler>();
		clientsTable.setEditable(false);
		clientsTable.setFocusTraversable(false);
		clientsTable.setPrefHeight(180);
		
		ViewUtilsHP.createColumn(clientsTable, "Nick", "nick", 150);
		ViewUtilsHP.createColumn(clientsTable, "IP", "remoteAddress", 200);
		ViewUtilsHP.createColumn(clientsTable, "Port", "remotePort", 150);
		
		clientsTable.setPlaceholder(new Label("no clients connnected"));

		Pane connectedClients = ViewUtilsHP.newPanel(false, "Connected Clients");
		connectedClients.getChildren().add(clientsTable);

		return connectedClients;
	}

	private Pane createGamesTable() {

		gamesTable = new TableView<GameLobby>();

		ViewUtilsHP.createColumn(gamesTable, "ID", "gameID", 100);
		ViewUtilsHP.createColumn(gamesTable, "State", "gamestate", 100);
		ViewUtilsHP.createColumn(gamesTable, "Depth", "depth", 100);
		ViewUtilsHP.createColumn(gamesTable, "Difficulty", "difficulty", 100);
		ViewUtilsHP.createColumn(gamesTable, "# of Players", "playersCount", 100);
		
		gamesTable.setPlaceholder(new Label("no games created"));

		Pane games = ViewUtilsHP.newPanel(false, "Available Games");
		games.getChildren().add(gamesTable);
		gamesTable.setPrefHeight(180);
		return games;
	}


	public Pane createTextArea() {

		control = ViewUtilsHP.createTextArea();
		Pane controlPane = ViewUtilsHP.newPanel(false,"Server Control");
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

	
	public VBox getClient() {
		return client;
	}

	
	public void setClient( VBox client ) {
		this.client = client;
	}

	
	public Button getGoToClient() {
		return goToClient;
	}

	
	public void setGoToClient( Button goToClient ) {
		this.goToClient = goToClient;
	}
}
