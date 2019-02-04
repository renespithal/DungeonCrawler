package view.design.scenes.creategame;

import java.io.IOException;
import java.util.Optional;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import org.json.JSONException;

import util.ViewUtils;
import view.design.MainPresenter;
import view.design.scenes.gameLobby.GameLobbyPresenter;

/**
 * Created by Felix on 15.01.2016.
 */
public class CreateGamePresenter {

	private CreateGameScene createGameScene;
	private MainPresenter mainPresenter;

	private Scene actualScene;
	private SimpleBooleanProperty characterIsSelected = new SimpleBooleanProperty(false);

	private String character;
	private int gameID;

	public CreateGamePresenter(MainPresenter mainPresenter) {
		this.mainPresenter = mainPresenter;
		createGameScene = new CreateGameScene();
		buttonhandle();
		buttonDisabling();
		mainPresenter.getScenesmap().put("createGameScene", createGameScene.getActualScene());
	}

	private void buttonDisabling() {

		characterIsSelected.addListener(( obs, oldValue, newValue ) -> {
			createGameScene.getProceed().setDisable(newValue);
		});
	}

	public void buttonhandle() {

		createGameScene.getBack().setOnAction(e -> {
			mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("gameListScene"));
		});

		createGameScene.getProceed().setOnAction(e -> {
			/* TODO: select difficulty
			 * mainPresenter.getGameLobbyPresenter().getGameLobbyScene().
			 * setDifficulty(createGameScene.getDifficultyBox().
			 * getSelectionModel().getSelectedItem());
			 */
			try {
				switch (createGameScene.x.get()) {

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
				mainPresenter.getClient().getCom().getSend().createGame(character, 3, 1);

				mainPresenter.setGameLobbyPresenter(new GameLobbyPresenter(this.mainPresenter));
				mainPresenter.getGameLobbyPresenter().setGameID(gameID);
				mainPresenter.getGameLobbyPresenter().getGameLobbyScene().getPlayersTable().setItems(mainPresenter.getClient().getPlayerList());
				mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("gameLobbyScene"));
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (JSONException e1) {
				e1.printStackTrace();
			}

		});

		createGameScene.getBackToMenu().setOnAction(e -> {

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

	public Scene getActualScene() {
		return actualScene;
	}

	public void setActualScene( Scene actualScene ) {
		this.actualScene = actualScene;
	}

	public CreateGameScene getCreateGameScene() {
		return createGameScene;
	}

	public void setCreateGameScene( CreateGameScene createGameScene ) {
		this.createGameScene = createGameScene;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter( String character ) {
		this.character = character;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
}
