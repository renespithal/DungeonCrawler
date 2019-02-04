package view.design.scenes.gameLobby;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import network.messages.SpielerInfo;
import util.ViewUtils;

public class GameLobbyScene extends BorderPane {

    private Scene actualScene;
    private Button mainMenu;
    private Button server;
    private Button startGame;
    private Button leaveGame;

    TableView<SpielerInfo> playersTable;

    public GameLobbyScene() {

        this.setTop(createHeader());
        this.setCenter(createMainArea());
        this.setBottom(createFooter());

        actualScene = ViewUtils.createScene(this);
        ViewUtils.configureScene(actualScene);
    }

    private Node createHeader() {

        return ViewUtils.createHeader("GameLobby");
    }

    private Node createFooter() {

        mainMenu = ViewUtils.newButton("Back to Menu");
        server = ViewUtils.newButton("Server");
        startGame = ViewUtils.newButton("start game");
        leaveGame = ViewUtils.newButton("Leave Game");


        HBox hBox = new HBox(mainMenu, server, startGame);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15);
        return hBox;
    }

    private GridPane createMainArea() {

        GridPane tables = new GridPane();
        tables.add(createPlayersTable(), 0, 0);
        tables.setPrefHeight(300);
        tables.setAlignment(Pos.CENTER);
        return tables;
    }

    private Pane createPlayersTable() {

        playersTable = new TableView<SpielerInfo>();
        playersTable.setEditable(false);
        playersTable.setFocusTraversable(false);
        playersTable.setMaxHeight(120);

        ViewUtils.createColumn(playersTable, "Name", "name", 150);
        ViewUtils.createColumn(playersTable, "Character Class", "character_class", 200);

        Pane players = ViewUtils.newPanel(false, "Connected Players");
        players.getChildren().add(playersTable);

        return players;
    }

    /* Getter */



    public Scene getActualScene() {
		return actualScene;
	}
	
	public Button getBackToMenu() {
		return mainMenu;
	}

	public Button getStartGame() {
		return startGame;
	}
	
	public Button getLeaveGame() {
		return leaveGame;
	}

	public void setLeaveGame( Button leaveGame ) {
		this.leaveGame = leaveGame;
	}

	public TableView<SpielerInfo> getPlayersTable() {
		return playersTable;
	}

	
	public Button getServer() {
		return server;
	}

	
	public void setServer( Button server ) {
		this.server = server;
	}
}