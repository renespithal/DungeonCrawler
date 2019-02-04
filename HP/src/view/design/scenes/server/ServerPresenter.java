package view.design.scenes.server;

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

/**
 * Created by sarah on 09.01.2016.
 */
public class ServerPresenter {

	private static final Logger logger = LoggerFactory.getLogger(ServerPresenter.class);

	private MainPresenter mainPresenter;
	private ServerScene serverScene;
	private MusicPlayer mediaplayer = new MusicPlayer();

	private SimpleBooleanProperty isStarted = new SimpleBooleanProperty(false);

	private String previousScene;

	public ServerPresenter(MainPresenter mainPresenter) {
		this.mainPresenter = mainPresenter;

		this.serverScene = new ServerScene();
		buttonDisabling();
		buttonhandle();

		this.previousScene = "serverListScene";

		mainPresenter.getScenesmap().put("serverScene", serverScene.getActualScene());
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
		if( !mainPresenter.getServer().getRegisteredClients().isEmpty() ) {
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
			if( mainPresenter.getServer() == null ) {
				mainPresenter.setServer(new Server(false));
			}
			startServer();
		});

		serverScene.getStopServer().setOnAction(e -> {
			try {
				mediaplayer.playClick();
				stopServer();
				} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		});

		serverScene.getGotoMain().setOnAction(e -> {
			mediaplayer.playClick();
			mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("menuScene"));
		});

		serverScene.getClient().setOnAction(e -> {
			
					mediaplayer.playClick();

					if( mainPresenter.getClient() == null ) {
						try {
							mainPresenter.setClient(new Client(this.mainPresenter));
							mainPresenter.getPresenter().serverCloseListener();
							mainPresenter.getClient().setMainPresenter(this.mainPresenter);
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (JSONException e1) {
							e1.printStackTrace();
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}

						mainPresenter.getServerListPresenter().getServerListScene().getServerTable()
								.setItems(mainPresenter.getClient().getConnect().getReceive().getServerList());
						mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get(previousScene));
					} else {
						mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get(previousScene));
					}

				});

	}

	public void bindTextArea() {
		mainPresenter.getServer().controlProperty().addListener(( observable, oldValue, newValue ) -> {
			serverScene.getControl().appendText(mainPresenter.getServer().getControl());
		});
	}

	public ServerScene getServerScene() {
		return serverScene;
	}

	public void setServerScene( ServerScene serverScene ) {
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
