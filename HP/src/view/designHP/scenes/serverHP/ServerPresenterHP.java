package view.designHP.scenes.serverHP;

import java.io.IOException;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;
import network.client.Client;
import network.server.Server;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.ViewUtils;
import view.design.MainPresenter;
import view.design.scenes.MusicPlayer;
import view.designHP.presenter.MainPresenterHP;

/**
 * Created by sarah on 28.01.2016.
 */
public class ServerPresenterHP {

	private static final Logger logger = LoggerFactory.getLogger(ServerPresenterHP.class);

	private MainPresenterHP mainPresenter;
	private ServerSceneHP serverScene;
	private MusicPlayer mediaplayer = new MusicPlayer();
	
	private SimpleBooleanProperty isStarted = new SimpleBooleanProperty(false);
	
	private String previousScene;

	public ServerPresenterHP(MainPresenterHP mainPresenter) {
		this.mainPresenter = mainPresenter;

		this.serverScene = new ServerSceneHP();
		this.previousScene = "serverListScene";
		buttonDisabling();
		buttonhandle();

		mainPresenter.getScenesMap().put("serverScene", serverScene.getActualScene());
	}

	private void buttonDisabling() {

		isStarted.addListener(( obs, oldValue, newValue ) -> {
			serverScene.getStartServer().setDisable(newValue);
			serverScene.getStopServer().setDisable(!newValue);
			serverScene.getServerStarted().setDisable(!newValue);
			serverScene.getServerStopped().setDisable(newValue);
		});
	}

	private void startServer() {

		try {
			logger.info("starting server");
			mainPresenter.getServer().getConnect().connect();
			logger.info("successfully started server");
			isStarted.set(true);
		} catch (IOException e) {
			Alert alert = ViewUtils.alertError("Error", "unable to start server: " + e);
			alert.showAndWait();
			logger.error("cannot start server", e);
		}
	}

	private void stopServer() throws InterruptedException {
		if(!mainPresenter.getServer().getRegisteredClients().isEmpty()){
			try {
				mainPresenter.getServer().stopRegistration();
				mainPresenter.getServer().close();
				mainPresenter.setServer(null);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

		}
		isStarted.set(false);
	}

	public void buttonhandle() {

		serverScene.getStartServer().setOnAction(e -> {
			mediaplayer.playClick();
			if(mainPresenter.getServer() == null) {
				mainPresenter.setServer(new Server(true));
			}
			startServer();
		});
		
		serverScene.getGoToClient().setOnAction(e -> {
			mediaplayer.playClick();
			
			if( mainPresenter.getClient() == null ) {
				try {
					mainPresenter.setClient(new Client(this.mainPresenter));
					mainPresenter.getPresenter().serverCloseListenerHP();
					mainPresenter.getClient().setMainPresenter(this.mainPresenter);
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (JSONException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				mainPresenter.getServerListPresenterHP().getServerListSceneHP().getServerTable()
						.setItems(mainPresenter.getClient().getConnect().getReceive().getServerList());
				mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get(previousScene));
			} else {
				mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get(previousScene));
			}
		});

		serverScene.getGotoMain().setOnAction(e -> {
			mediaplayer.playClick();
			mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("menuScene"));
		});

	}

    public void bindTextArea() {
        mainPresenter.getServer().controlProperty().addListener((observable, oldValue, newValue) -> {
            serverScene.getControl().appendText(mainPresenter.getServer().getControl());
        });
    }

	public ServerSceneHP getServerScene() {
		return serverScene;
	}

	public void setServerScene( ServerSceneHP serverScene ) {
		this.serverScene = serverScene;
	}

	
	public String getPreviousScene() {
		return previousScene;
	}

	
	public void setPreviousScene( String previousScene ) {
		this.previousScene = previousScene;
	}

	
	public SimpleBooleanProperty getIsStarted() {
		return isStarted;
	}

	
	public void setIsStarted( SimpleBooleanProperty isStarted ) {
		this.isStarted = isStarted;
	}
	
	
}
