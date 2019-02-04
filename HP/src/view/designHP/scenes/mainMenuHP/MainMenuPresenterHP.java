package view.designHP.scenes.mainMenuHP;

import java.io.IOException;

import network.client.Client;
import network.server.Server;

import org.json.JSONException;

import view.design.scenes.MusicPlayer;
import view.designHP.presenter.MainPresenterHP;

/**
 * Created by Jenny on 27.01.2016.
 */
public class MainMenuPresenterHP {

    private MainPresenterHP mainPresenter;
    private MainMenuSceneHP menuScene;
    private MusicPlayer mediaplayer = new MusicPlayer();
    
    public MainMenuPresenterHP(MainPresenterHP mainPresenter) {
        this.menuScene = new MainMenuSceneHP();
        this.mainPresenter = mainPresenter;
        buttonhandle();
        mainPresenter.getScenesMap().put("menuScene", menuScene.getScene());
    }
    

    private void buttonhandle() {

    	menuScene.getExit().setOnAction(e -> {
    		
            mainPresenter.getPresenter().closeStage();
            mainPresenter.getStage().close();
        });
    	
    	menuScene.getsPlayer().setOnAction(e -> {
            mediaplayer.playClick();
            mainPresenter.getChoosePlayerPresenter().setSingleplayer(true);
            mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("choosePlayerScene"));
        });

    	menuScene.getmPlayer().setOnAction(e -> {
            //mediaplayer.playClick();

            try {
                if(mainPresenter.getClient() == null) {
                    mainPresenter.setClient(new Client(this.mainPresenter));
                    mainPresenter.getPresenter().serverCloseListenerHP();
                    mainPresenter.getClient().setMainPresenter(this.mainPresenter);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            mainPresenter.getServerListPresenterHP().getServerListSceneHP().getServerTable().setItems(
                    mainPresenter.getClient().getConnect().getReceive().getServerList());
            
            mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("serverListScene"));
        });
    	
    	 menuScene.getServer().setOnAction(e -> {
             mediaplayer.playClick();

             if(mainPresenter.getServer() == null) {
                 mainPresenter.setServer(new Server(true));
             }

             if(mainPresenter.getServer().getRegisteredClients()!= null){
                 mainPresenter.getServerPresenter().getServerScene().getClientsTable().setItems(mainPresenter.getServer().getRegisteredClients());}
             else {

             }

             mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("serverScene"));

             if(mainPresenter.getServer().getGameList()!= null){
                 mainPresenter.getServerPresenter().getServerScene().getGamesTable().setItems(mainPresenter.getServer().getGameList());}
             else{
                 }

             mainPresenter.getServerPresenter().bindTextArea();

         });

    }

    /* Getter & Setter */
    public MainMenuSceneHP getScene() {
        return menuScene;
    }

    public void setScene(MainMenuSceneHP scene) {
        this.menuScene = scene;
    }

    public MainPresenterHP getMainPresenter() {
        return mainPresenter;
    }

    public void setMainPresenter(MainPresenterHP mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

	
	public MainMenuSceneHP getMenuScene() {
		return menuScene;
	}

	
	public void setMenuScene( MainMenuSceneHP menuScene ) {
		this.menuScene = menuScene;
	}

}
