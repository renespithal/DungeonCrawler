package view.designHP.scenes.serverListHP;

//import javafx.scene.control.Alert;
//import javafx.scene.control.ButtonType;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;
import network.server.ServerInfo;
import util.ViewUtils;
import view.design.scenes.MusicPlayer;
import view.designHP.presenter.MainPresenterHP;

public class ServerListPresenterHP {

	private MainPresenterHP mainPresenter;
	private ServerListSceneHP serverListScene;
	private MusicPlayer mediaplayer = new MusicPlayer();

	private BooleanProperty isconnected = new SimpleBooleanProperty(false);

	public ServerListPresenterHP(MainPresenterHP mainPresenter) {
		this.mainPresenter = mainPresenter;
		this.serverListScene = new ServerListSceneHP();
		buttonDisabling();
		buttonhandle();
		mainPresenter.getScenesMap().put("serverListScene", serverListScene.getActualScene());
	}

	private void buttonDisabling() {
		
		isconnected.addListener(( obs, oldValue, newValue ) -> {
			if(!mainPresenter.getServerPresenter().getIsStarted().getValue()) {
				serverListScene.getGoToServer().setDisable(newValue);
			}
			serverListScene.getProceed().setDisable(!newValue);
			serverListScene.getConnect().setDisable(newValue);
		});
	}

	public void buttonhandle() {

		serverListScene.getConnect().setOnAction(e -> {

			
			if( serverListScene.getServerTable().getSelectionModel().isEmpty() ) {
				mediaplayer.playBuzzer();
				Alert alert = ViewUtils.alertError("No Server selected", "Please choose a server to connect to");
				alert.showAndWait();
			}
			else {
				mainPresenter.getClient().getConnect().getReceive().setTcpPort(serverListScene.getServerTable().getSelectionModel().getSelectedItem().getPort());
				mediaplayer.playClick();
			ServerInfo info = serverListScene.getServerTable().getSelectionModel().getSelectedItem();
			try {
				InetAddress address = InetAddress.getByName(info.getIp().substring(1));

				mainPresenter.getClient().getConnect().getReceive().setServerIP(address);
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}

			mainPresenter.getClient().getConnect().getReceive().setTcpPort(info.getPort());

			// mainPresenter.getClient().getConnect().getServerList().

				for (ServerInfo s : mainPresenter.getClient().getConnect().getReceive().getServerList()) {

					if( s.getIp().contains(info.getIp()) ) {
						s.setStatus("connected");

						serverListScene.getServerTable().refresh();
					}

				}
				try {
					mainPresenter.getClient().getConnect().establish();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				isconnected.set(true);
				serverListScene.getConnect().setDisable(true);
		}
			
			});

		serverListScene.getGotoMain().setOnAction(e -> {

			mediaplayer.playClick();

			mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("menuScene"));

		});
		
		serverListScene.getGoToServer().setOnAction(e -> {

			mediaplayer.playClick();

			mainPresenter.getServerPresenter().setPreviousScene("serverListScene");
			mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("serverScene"));

		});

		serverListScene.getProceed().setOnAction(e -> {

			if( isconnected.get() ) {
				mediaplayer.playClick();
				mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("loginScene"));
			} else {
				mediaplayer.playBuzzer();
				Alert alert = ViewUtils.alertError("Error", "You are not connected yet");
				alert.showAndWait();
			}
		});
	}

	public ServerListSceneHP getServerListSceneHP() {
		return serverListScene;
	}

	public void setServerListScene( ServerListSceneHP serverListScene ) {
		this.serverListScene = serverListScene;
	}

	public BooleanProperty getIsconnected() {
		return isconnected;
	}

	public void setIsconnected( BooleanProperty isconnected ) {
		this.isconnected = isconnected;
	}
}
