package view.design.scenes.joinGame;

import java.io.IOException;
import java.util.Optional;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import network.messages.SpielInfo;

import org.json.JSONException;


import util.ViewUtils;
import view.design.MainPresenter;

/**
 * Created by Jenny on 18.01.2016.
 */
public class JoinGamePresenter {

	private JoinGameScene joinGameScene;
	private MainPresenter mainPresenter;

	private Scene actualScene;

	private SimpleBooleanProperty characterIsSelected = new SimpleBooleanProperty(false);
	private String character;

	public JoinGamePresenter(MainPresenter mainPresenter) {
		this.mainPresenter = mainPresenter;
		joinGameScene = new JoinGameScene();
		buttonDisabling();
		buttonhandle();
		mainPresenter.getScenesmap().put("joinGameScene", joinGameScene.getActualScene());
	}

	private void buttonDisabling() {

		characterIsSelected.addListener(( obs, oldValue, newValue ) -> {
			joinGameScene.getProceed().setDisable(!newValue);
		});
	}

	public void buttonhandle() {

		joinGameScene.getBackToMenu().setOnAction(e -> {

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

		joinGameScene.getBack().setOnAction(e -> {
			mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("gameListScene"));
		});

		joinGameScene.getProceed().setOnAction(e -> {
			/* TODO: difficulty
			 * mainPresenter.getGameLobbyPresenter().getGameLobbyScene().
			 * setDifficulty(createGameScene.getDifficultyBox().
			 * getSelectionModel().getSelectedItem());
			 */
			try {
				switch (joinGameScene.x.get()) {

				case 0:
					character = "warrior";
					characterIsSelected.set(true);
					break;
				case 1:
					character = "mage";
					characterIsSelected.set(true);
					break;
				case 2:
					character = "ranger";
					characterIsSelected.set(true);
					break;
				case 3:
					character = "professor";
					characterIsSelected.set(true);
					break;
				case 4:
					character = "student";
					characterIsSelected.set(true);
					break;
				case 5:
					character = "tourist";
					characterIsSelected.set(true);
					break;
				}
				
				SpielInfo spielInfo = mainPresenter.getGameListPresenter().getGameListScene().getGamesTable().getSelectionModel().getSelectedItem();
				int gameID = spielInfo.getGameID();
				mainPresenter.getClient().getCom().getSend().joinGame(gameID, character);
				mainPresenter.getGameLobbyPresenter().getGameLobbyScene().getPlayersTable().setItems(mainPresenter.getClient().getPlayerList());
				mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("gameLobbyScene"));
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (JSONException e1) {
				e1.printStackTrace();
			}

		});

	}

	public Scene getActualScene() {
		return actualScene;
	}

	public void setActualScene( Scene actualScene ) {
		this.actualScene = actualScene;
	}

	public JoinGameScene getJoinGameScene() {
		return joinGameScene;
	}

	public void setJoinGameScene( JoinGameScene joinGameScene ) {
		this.joinGameScene = joinGameScene;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter( String character ) {
		this.character = character;
	}
}
