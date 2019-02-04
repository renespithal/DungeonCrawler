package view.design;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import network.client.Client;
import network.game.GameLobby;
import network.server.Server;

import org.json.JSONException;

import util.ViewUtils;
import view.Presenter;
import view.design.scenes.creategame.CreateGamePresenter;
import view.design.scenes.gameLobby.GameLobbyPresenter;
import view.design.scenes.gameOver.GameOverPresenter;
import view.design.scenes.gameOver.GameWonPresenter;
import view.design.scenes.gameOver.GameWonScene;
import view.design.scenes.gamelist.GameListPresenter;
import view.design.scenes.infoHelp.InfoHelpPresenter;
import view.design.scenes.joinGame.JoinGamePresenter;
import view.design.scenes.loginRoom.LoginPresenter;
import view.design.scenes.mainMenu.MenuPresenter;
import view.design.scenes.multiplayerGame.MultiplayerGamePresenter;
import view.design.scenes.server.ServerPresenter;
import view.design.scenes.serverList.ServerListPresenter;
import view.design.scenes.serverRoom.ServerRoomPresenter;
import view.design.scenes.singleplayerGame.SingleplayerGamePresenter;
import view.design.scenes.singleplayerLobby.SingleplayerGameLobbyPresenter;
import view.design.scenes.startscreen.StartScenePresenter;

/**
 * Created by Jenny on 09.01.2016.
 */
public class MainPresenter {

    private Stage stage;

    private InfoHelpPresenter infoHelpPresenter;
    private GameListPresenter gameListPresenter;    // 0
    private GameLobbyPresenter gameLobbyPresenter;    // 1
    private ServerListPresenter serverListPresenter; //2
    private ServerPresenter serverPresenter;   // 3
    private LoginPresenter loginPresenter;

    private CreateGamePresenter createGamePresenter;
    private JoinGamePresenter joinGamePresenter;

    private GameOverPresenter gameOverPresenter;
    private GameWonPresenter gameWonPresenter;

    private MultiplayerGamePresenter multiplayerGamePresenter;

    private ServerRoomPresenter serverRoomPresenter;
    private StartScenePresenter startScenePresenter;
    private MenuPresenter menuPresenter;
    private SingleplayerGameLobbyPresenter singleplayerGameLobbyPresenter;
    private ArrayList<Scene> scenesList;
    private HashMap<String, Scene> scenesmap;

    private Client client;
    private Server server;
    private SingleplayerGamePresenter singleplayerGamePresenter;

    private GameLobby clientGameLobby;
    private Presenter presenter;


    public MainPresenter(Stage primaryStage, Presenter p) throws MalformedURLException {
        this.presenter = p;
        this.scenesList = new ArrayList<Scene>();
        this.scenesmap = new HashMap<String, Scene>();
        this.stage = primaryStage;
        this.stage.setTitle("Dungeon Crawler");
        createPresenter();
    }

    private void createPresenter() throws MalformedURLException {


        gameWonPresenter = new GameWonPresenter(this); 
        gameOverPresenter = new GameOverPresenter(this);

        startScenePresenter = new StartScenePresenter(this);    //0

        menuPresenter = new MenuPresenter(this);                //1

        serverListPresenter = new ServerListPresenter(this);    //2

        serverPresenter = new ServerPresenter(this);            //3

        gameListPresenter = new GameListPresenter(this);        //4

        loginPresenter = new LoginPresenter(this);              //5

        serverRoomPresenter = new ServerRoomPresenter(this);    //6

   //     gameLobbyPresenter = new GameLobbyPresenter(this);      //7

        createGamePresenter = new CreateGamePresenter(this);

        singleplayerGameLobbyPresenter = new SingleplayerGameLobbyPresenter(this);    //9

        singleplayerGamePresenter = new SingleplayerGamePresenter(this); //10

        joinGamePresenter = new JoinGamePresenter(this);

        infoHelpPresenter = new InfoHelpPresenter(this);

    }



    public void herbetListener(){
        client.getCom().getRcv().getClientDecoder().herbertProperty().addListener((observable, oldValue, newValue) -> {
            this.multiplayerGamePresenter = new MultiplayerGamePresenter(this,client.getCom().getRcv().getClientDecoder().getClientModel());
            getStage().setScene(getScenesmap().get("multiplayerGameScene"));
        });
    }


    /* Getter & Setter */
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public ArrayList<Scene> getScenesList() {
        return scenesList;
    }

    public void setScenesList(ArrayList<Scene> scenesList) {
        this.scenesList = scenesList;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public SingleplayerGameLobbyPresenter getSingleplayerGameLobbyPresenter() {
        return singleplayerGameLobbyPresenter;
    }

    public SingleplayerGamePresenter getSingleplayerGamePresenter() {
        return singleplayerGamePresenter;
    }

    public HashMap<String, Scene> getScenesmap() {
        return scenesmap;
    }

    public void setScenesmap(HashMap<String, Scene> scenesmap) {
        this.scenesmap = scenesmap;
    }

    public ServerPresenter getServerPresenter() {
        return serverPresenter;
    }

    public void setServerPresenter(ServerPresenter serverPresenter) {
        this.serverPresenter = serverPresenter;
    }

    public ServerListPresenter getServerListPresenter() {
        return serverListPresenter;
    }

    public void setServerListPresenter(ServerListPresenter serverListPresenter) {
        this.serverListPresenter = serverListPresenter;
    }

    public GameLobbyPresenter getGameLobbyPresenter() {
        return gameLobbyPresenter;
    }

    public GameLobby getClientGameLobby() {
        return clientGameLobby;
    }

    public void setClientGameLobby(GameLobby clientGameLobby) {
        this.clientGameLobby = clientGameLobby;
    }

    public void setGameLobbyPresenter(GameLobbyPresenter gameLobbyPresenter) {
        this.gameLobbyPresenter = gameLobbyPresenter;


    }

    public CreateGamePresenter getCreateGamePresenter() {
        return createGamePresenter;
    }

    public void setCreateGamePresenter(CreateGamePresenter createGamePresenter) {
        this.createGamePresenter = createGamePresenter;
    }

    public GameListPresenter getGameListPresenter() {
        return gameListPresenter;
    }

    public void setGameListPresenter(GameListPresenter gameListPresenter) {
        this.gameListPresenter = gameListPresenter;
    }


    public LoginPresenter getLoginPresenter() {
        return loginPresenter;
    }



    public ServerRoomPresenter getServerRoomPresenter() {
        return serverRoomPresenter;
    }


    public StartScenePresenter getStartScenePresenter() {
        return startScenePresenter;
    }


    public MenuPresenter getMenuPresenter() {
        return menuPresenter;
    }

    public MultiplayerGamePresenter getMultiplayerGamePresenter() {
        return multiplayerGamePresenter;
    }

    public void setMultiplayerGamePresenter(MultiplayerGamePresenter multiplayerGamePresenter) {
        this.multiplayerGamePresenter = multiplayerGamePresenter;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
