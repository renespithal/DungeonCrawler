package view.design.scenes.mainMenu;


import java.io.IOException;
import java.net.MalformedURLException;

import network.client.Client;
import network.server.Server;

import org.json.JSONException;

import view.design.MainPresenter;
import view.design.scenes.MusicPlayer;

public class MenuPresenter {


    //FAGGRID

    private MenuScene menuScene;
    private MainPresenter mainPresenter;
    private MusicPlayer mediaplayer = new MusicPlayer();


    public MenuPresenter(MainPresenter mainPresenter) throws MalformedURLException {

        this.mainPresenter = mainPresenter;
        this.menuScene = new MenuScene();
        buttonhandle();
        mainPresenter.getScenesmap().put("menuScene",menuScene.getActualScene());
    }

    public void buttonhandle() {


        menuScene.getExitButton().setOnAction(e -> {
            mainPresenter.getPresenter().closeStage();
            mainPresenter.getStage().close();
        });


        menuScene.getSingleplayerButton().setOnAction(e -> {
            mediaplayer.playClick();
            mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("singlePlayerGameLobbyScene"));


        });


        menuScene.getMultiplayerButton().setOnAction(e -> {
            mediaplayer.playClick();

            try {
                if(mainPresenter.getClient() == null) {
                    mainPresenter.setClient(new Client(this.mainPresenter));
                    mainPresenter.getPresenter().serverCloseListener();
                    mainPresenter.getClient().setMainPresenter(this.mainPresenter);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }


            mainPresenter.getServerListPresenter().getServerListScene().getServerTable().setItems(
                    mainPresenter.getClient().getConnect().getReceive().getServerList());

            mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("serverListScene"));


        });

        menuScene.getInfoButton().setOnAction(e-> {

            mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("infoHelpScene"));


        });


        menuScene.getStartServer().setOnAction(e -> {
            mediaplayer.playClick();

            if(mainPresenter.getServer() == null) {
                mainPresenter.setServer(new Server(false));
            }

            if(mainPresenter.getServer().getRegisteredClients()!= null){
                mainPresenter.getServerPresenter().getServerScene().getClientsTable().
                        setItems(mainPresenter.getServer().getRegisteredClients());
            }
                else {}
                mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("serverScene"));

            if(mainPresenter.getServer().getGameList()!= null){
                mainPresenter.getServerPresenter().getServerScene().getGamesTable().setItems(mainPresenter.getServer().getGameList());}
            else{
                }

            mainPresenter.getServerPresenter().bindTextArea();

        });

        
    }
}