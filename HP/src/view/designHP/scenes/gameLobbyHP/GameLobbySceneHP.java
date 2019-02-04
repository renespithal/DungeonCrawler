package view.designHP.scenes.gameLobbyHP;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import network.messages.SpielerInfo;
import util.ViewUtils;
import util.ViewUtilsHP;

public class GameLobbySceneHP extends BorderPane {

    private Scene actualScene;
    private Button mainMenu;
    private Button server;
    private Button startGame;
    private Button leaveGame;
    
    private VBox main, se, leave, start; 

    TableView<SpielerInfo> playersTable;

    public GameLobbySceneHP() {

        this.setCenter(createMainArea());
        this.setBottom(createFooter());


        this.actualScene = ViewUtilsHP.createScene(this);
        ViewUtilsHP.setBackground(this, "hp/media/background/lobbyBG.jpg");
        ViewUtilsHP.configureCSS(actualScene);
    }

    private Node createFooter() {
    	
        mainMenu = ViewUtilsHP.createButton("Back to Menu");
        main = ViewUtilsHP.buttonSurrounding(mainMenu);
        server = ViewUtilsHP.createButton("Server");
        se = ViewUtilsHP.buttonSurrounding(server);
        startGame = ViewUtilsHP.createButton("Start Game");
        start = ViewUtilsHP.buttonSurrounding(startGame);
        leaveGame = ViewUtilsHP.createButton("Leave Game");
        leave = ViewUtilsHP.buttonSurrounding(leaveGame);

        HBox hBox1 = new HBox(main, se);
		HBox hBox2 = new HBox(leave, start);
		hBox1.setSpacing(100);
		hBox2.setSpacing(100);
		HBox hBox = new HBox(hBox1,hBox2);
		hBox.setAlignment(Pos.CENTER);

		hBox.setSpacing(280);
		hBox.setPadding(new Insets(0,0,10,80));
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

        ViewUtilsHP.createColumn(playersTable, "Name", "name", 150);
        ViewUtilsHP.createColumn(playersTable, "Character Class", "character_class", 200);

        Pane players = ViewUtils.newPanel(false, "Connected Players");
        players.getChildren().add(playersTable);

        return players;
    }

    /* Getter */


    public Button getServer() {
        return server;
    }

    public void setServer(Button server) {
        this.server= server;
    }

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
}