package view.design.scenes.serverList;

import util.ViewUtils;
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
import network.server.ServerInfo;

/**
 * Created by Rene on 07.01.2016.
 */
public class ServerListScene extends BorderPane {

	private Button connect;
	private Button gotoMain;
	private Button server;
	private Button back,proceed;

	TableView<ServerInfo> serverTable;
	private Scene actualScene;

	public ServerListScene() {

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


		gotoMain = ViewUtils.newButton("Back to Menu");
		server = ViewUtils.newButton("Server");
		connect = ViewUtils.newButton("connect");
		proceed = ViewUtils.newButton("continue");
		proceed.setDisable(true);

		HBox hBox = new HBox(gotoMain, server, connect,proceed );
		hBox.setSpacing(10);
		hBox.setAlignment(Pos.CENTER);

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

		// TODO
		ViewUtils.createColumn(serverTable, "Group", "group", 100);
		ViewUtils.createColumn(serverTable, "IP", "ip", 200);
		ViewUtils.createColumn(serverTable, "Port", "port", 100);
		ViewUtils.createColumn(serverTable, "Status", "status", 100);
		
		serverTable.setPlaceholder(new Label("no server yet - please start a server"));

		Pane availableServers = ViewUtils.newPanel(false, "Available Servers");
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

	
	public Button getServer() {
		return server;
	}

	
	public void setServer( Button server ) {
		this.server = server;
	}
}


