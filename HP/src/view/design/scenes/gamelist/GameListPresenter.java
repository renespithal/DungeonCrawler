package view.design.scenes.gamelist;

import java.io.IOException;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import network.messages.SpielInfo;

import org.json.JSONException;

import util.ViewUtils;
import view.design.MainPresenter;
import view.design.scenes.gameLobby.GameLobbyPresenter;

/**
 * Created by Rusty on 18.12.2015.
 */
public class GameListPresenter {

	private MainPresenter mainPresenter;
	private GameListScene gameListScene;
	private int gameID;

	public GameListPresenter(MainPresenter mainPresenter) {
		this.mainPresenter = mainPresenter;
		this.gameListScene = new GameListScene();
		this.gameID = -1;
		bindPlayers();
		buttonDisabling();
		buttonhandle();
		mainPresenter.getScenesmap().put("gameListScene", gameListScene.getActualScene());
	}

	private void buttonDisabling() {

		mainPresenter.getServerPresenter().getIsStarted().addListener(( obs, oldValue, newValue ) -> {
			gameListScene.getServer().setDisable(!newValue);
		});

		gameListScene.getJoin().disableProperty().bind(gameListScene.getGamesTable().getSelectionModel().selectedItemProperty().isNull());
	}

	private void bindPlayers() {
		// TODO Auto-generated method stub

	}

	public void buttonhandle() {
		this.gameListScene.getJoin().setOnAction(e -> {

			SpielInfo info = gameListScene.getGamesTable().getSelectionModel().getSelectedItem();
			
			if(info.getGamestate().equals("not started")) {
				int currentGameID = info.getGameID();
				mainPresenter.setGameLobbyPresenter(new GameLobbyPresenter(this.mainPresenter));
				mainPresenter.getGameLobbyPresenter().setGameID(currentGameID);
				mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("joinGameScene"));
			}
			else {
				Alert alert = ViewUtils.alertError("Error", "Game is unavailable");
				alert.showAndWait();
			}
				
		});
		
		this.gameListScene.getServer().setOnAction(e -> {

			mainPresenter.getServerPresenter().setPreviousScene("gameListScene");
			mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("serverScene"));

		});

		this.gameListScene.getCreate().setOnAction(e -> {
			int currentGameID = mainPresenter.getClient().getGameList().size();
			mainPresenter.getCreateGamePresenter().setGameID(currentGameID);
			mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("createGameScene"));

		});

		this.gameListScene.getBackToMenu().setOnAction(e -> {

			Alert alert = ViewUtils.alertConfirm("Confirm logout", "Do you want to disconnect?");
			Optional<ButtonType> result = alert.showAndWait();
			if( result.get() == ButtonType.OK ) {
				try {
					mainPresenter.getClient().getCom().getSend().disconnect();
					mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("menuScene"));
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	public GameListScene getGameListScene() {
		return gameListScene;
	}

	public void setGameListScene( GameListScene gameListScene ) {
		this.gameListScene = gameListScene;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID( int gameID ) {
		this.gameID = gameID;
	}
}
