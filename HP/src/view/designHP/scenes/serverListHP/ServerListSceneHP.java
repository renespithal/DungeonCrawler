package view.designHP.scenes.serverListHP;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import network.server.ServerInfo;
import util.ViewUtilsHP;

/**
 * Created by Rene on 07.01.2016.
 */
public class ServerListSceneHP extends BorderPane {

	private Button connect;
	private Button gotoMain;
	private Button goToServer;
	private Button back,proceed;

	private VBox con, main, server, pro;

	TableView<ServerInfo> serverTable;
	private Scene actualScene;

	public ServerListSceneHP() {

		setPositions();
		this.actualScene = ViewUtilsHP.createScene(this);
        ViewUtilsHP.setBackground(this, "hp/media/background/ServerListBG.jpg");
        ViewUtilsHP.configureCSS(actualScene);
	}

	private void setPositions() {
		this.setCenter(createMainArea());
		this.setBottom(createFooter());
	}

	private Node createFooter() {


		gotoMain = ViewUtilsHP.createButton("Back to Menu");
		main = ViewUtilsHP.buttonSurrounding(gotoMain);
		goToServer = ViewUtilsHP.createButton("Server");
		server = ViewUtilsHP.buttonSurrounding(goToServer);
		connect = ViewUtilsHP.createButton("connect");
		con = ViewUtilsHP.buttonSurrounding(connect);
		proceed = ViewUtilsHP.createButton("continue");
		pro = ViewUtilsHP.buttonSurrounding(proceed);
		proceed.setDisable(true);

		HBox hBox1 = new HBox(main, server);
		HBox hBox2 = new HBox(con,pro);
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
		tables.add(createServerTable(), 0, 0);
		tables.setAlignment(Pos.CENTER);
		return tables;
	}

	private Pane createServerTable() {

		serverTable = new TableView<ServerInfo>();
		serverTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		serverTable.setEditable(false);
		serverTable.setFocusTraversable(false);
		serverTable.setPrefHeight(300);

		ViewUtilsHP.createColumn(serverTable, "Group", "group", 100);
		ViewUtilsHP.createColumn(serverTable, "IP", "ip", 200);
		ViewUtilsHP.createColumn(serverTable, "Port", "port", 100);
		ViewUtilsHP.createColumn(serverTable, "Status", "status", 100);
		
		serverTable.setPlaceholder(new Label("no server yet - please start a server"));

		Pane availableServers = ViewUtilsHP.newPanel(false, "Available Servers");
		availableServers.getChildren().add(serverTable);

		return availableServers;
	}


	public Scene getActualScene() {
		return actualScene;
	}

	public void setActualScene(Scene actualScene) {
		this.actualScene = actualScene;
	}

	public Button getConnect() {
		return connect;
	}

	public void setConnect(Button connect) {
		this.connect = connect;
	}

	public Button getGotoMain() {
		return gotoMain;
	}

	public void setGotoMain(Button gotoMain) {
		this.gotoMain = gotoMain;
	}

	public TableView<ServerInfo> getServerTable() {
		return serverTable;
	}

	public void setServerTable(TableView<ServerInfo> serverTable) {
		this.serverTable = serverTable;
	}

	public Button getBack() {
		return back;
	}

	public void setBack(Button back) {
		this.back = back;
	}

	public Button getProceed() {
		return proceed;
	}

	public void setProceed(Button cont) {
		this.proceed = cont;
	}

	public Button getGoToServer() {
		return goToServer;
	}
	
	public void setGoToServer( Button goToServer ) {
		this.goToServer = goToServer;
	}
}


