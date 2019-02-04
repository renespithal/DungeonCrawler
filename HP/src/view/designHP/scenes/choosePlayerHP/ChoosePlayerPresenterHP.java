package view.designHP.scenes.choosePlayerHP;

import java.io.IOException;
import java.util.Optional;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import network.messages.SpielInfo;

import org.json.JSONException;

import util.ViewUtils;
import util.ViewUtilsHP;
import view.designHP.presenter.MainPresenterHP;
import view.designHP.scenes.gameLobbyHP.GameLobbyPresenterHP;

/**
 * Created by Jenny on 18.01.2016.
 */
public class ChoosePlayerPresenterHP {

	private ChoosePlayerSceneHP choosePlayerScene;
	private MainPresenterHP mainPresenter;

	private Scene actualScene;

	private SimpleBooleanProperty characterIsSelected = new SimpleBooleanProperty(false);
	private String character;

	private boolean singleplayer;
	private boolean create;

	private int gameID;

	public ChoosePlayerPresenterHP(MainPresenterHP mainPresenter) {
		this.mainPresenter = mainPresenter;
		choosePlayerScene = new ChoosePlayerSceneHP();
		buttonDisabling();
		buttonhandle();
		mainPresenter.getScenesMap().put("choosePlayerScene", choosePlayerScene.getScene());
		sceneSwitch();
	}

	private void buttonDisabling() {

		characterIsSelected.addListener(( obs, oldValue, newValue ) -> {
			choosePlayerScene.getChoose().setDisable(!newValue);
		});
	}

	public void buttonhandle() {
		choosePlayerScene.getMainMenu().setOnAction(e -> {

			Alert alert = ViewUtils.alertConfirm("Confirm logout", "Do you want to disconnect?");
			Optional<ButtonType> result = alert.showAndWait();
			if( result.get() == ButtonType.OK ) {
				try {
					mainPresenter.getClient().getCom().getSend().disconnect();
					mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("menuScene"));
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		});

		choosePlayerScene.getBack().setOnAction(e -> {

			mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("gameListScene"));
		});

		choosePlayerScene.getChoose().setOnAction(e -> {

			if( singleplayer ) {
				String chooser = choosePlayerScene.getChooser();
				mainPresenter.getSingleplayerGamePresenter().startGame(chooser);
				mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("singlePlayerGameScene"));
			} else {
				if(create) {
					try {
						mainPresenter.getClient().getCom().getSend().createGame(choosePlayerScene.getChooser(), 3, 1);
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (JSONException e1) {
						e1.printStackTrace();
					}

					mainPresenter.setGameLobbyPresenter(new GameLobbyPresenterHP(this.mainPresenter));
					mainPresenter.getGameLobbyPresenter().setGameID(gameID);
					mainPresenter.getGameLobbyPresenterHP().getGameLobbyScene().getPlayersTable().setItems(mainPresenter.getClient().getPlayerList());

					mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("gameLobbyScene"));
				} else {
					try {
						SpielInfo spielInfo = mainPresenter.getGameListPresenter().getGameListScene().getGamesTable().getSelectionModel().getSelectedItem();
						int gameID = spielInfo.getGameID();
						mainPresenter.getClient().getCom().getSend().joinGame(gameID, choosePlayerScene.getChooser());
						mainPresenter.getGameLobbyPresenterHP().getGameLobbyScene().getPlayersTable().setItems(mainPresenter.getClient().getPlayerList());
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("gameLobbyScene"));
				}
				mainPresenter.getGameLobbyPresenterHP().getChat().getChat().textProperty().bind(mainPresenter.getClient().getCom().getRcv().getClientDecoder().msgChatProperty());
				mainPresenter.getClient().getCom().getRcv().getClientDecoder().msgChatProperty().addListener((observable,oldValue,newValue) -> {
					mainPresenter.getGameLobbyPresenterHP().getChat().getChat().setScrollTop(Double.MAX_VALUE);
				});
			}

		});
	}

	public void sceneSwitch() {
		choosePlayerScene.getGoRight().setOnAction(e -> {
			switch (choosePlayerScene.getChooser()) {
			case "harry":
				ViewUtilsHP.setBackground(choosePlayerScene, "hp/media/background/ChoosePlayerRonBG.jpg");
				choosePlayerScene.setChooser("ron");
				choosePlayerScene.setRon();
				break;
			case "ron":
				ViewUtilsHP.setBackground(choosePlayerScene, "hp/media/background/ChoosePlayerHermineBG.jpg");
				choosePlayerScene.setChooser("hermine");
				choosePlayerScene.setHermine();
				break;
			case "hermine":
				ViewUtilsHP.setBackground(choosePlayerScene, "hp/media/background/ChoosePlayerHagridBG.jpg");
				choosePlayerScene.setChooser("hagrid");
				choosePlayerScene.setHagrid();
				break;
			case "hagrid":
				ViewUtilsHP.setBackground(choosePlayerScene, "hp/media/background/ChoosePlayerDumbledoreBG.jpg");
				choosePlayerScene.setChooser("dumbledore");
				choosePlayerScene.setDeumbledore();
				break;
			case "dumbledore":
				ViewUtilsHP.setBackground(choosePlayerScene, "hp/media/background/ChoosePlayerGinnyBG.jpg");
				choosePlayerScene.setChooser("ginny");
				choosePlayerScene.setGinny();
				break;
			case "ginny":
				ViewUtilsHP.setBackground(choosePlayerScene, "hp/media/background/ChoosePlayerNevilleBG.jpg");
				choosePlayerScene.setChooser("neville");
				choosePlayerScene.setNeville();
				break;
			case "neville":
				ViewUtilsHP.setBackground(choosePlayerScene, "hp/media/background/ChoosePlayerMoodyBG.jpg");
				choosePlayerScene.setChooser("moody");
				choosePlayerScene.setMoody();
				break;
			case "moody":
				ViewUtilsHP.setBackground(choosePlayerScene, "hp/media/background/ChoosePlayerSnapeBG.jpg");
				choosePlayerScene.setChooser("snape");
				choosePlayerScene.setSnape();
				break;
			case "snape":
				ViewUtilsHP.setBackground(choosePlayerScene, "hp/media/background/ChoosePlayerHarryBG.jpg");
				choosePlayerScene.setChooser("harry");
				choosePlayerScene.setHarry();
				break;
			}
		});

		choosePlayerScene.getGoLeft().setOnAction(e -> {
			switch (choosePlayerScene.getChooser()) {
			case "harry":
				ViewUtilsHP.setBackground(choosePlayerScene, "hp/media/background/ChoosePlayerSnapeBG.jpg");
				choosePlayerScene.setChooser("snape");
				choosePlayerScene.setSnape();
				break;
			case "ron":
				ViewUtilsHP.setBackground(choosePlayerScene, "hp/media/background/ChoosePlayerHarryBG.jpg");
				choosePlayerScene.setChooser("harry");
				choosePlayerScene.setHarry();
				break;
			case "hermine":
				ViewUtilsHP.setBackground(choosePlayerScene, "hp/media/background/ChoosePlayerRonBG.jpg");
				choosePlayerScene.setChooser("ron");
				choosePlayerScene.setRon();
				break;
			case "hagrid":
				ViewUtilsHP.setBackground(choosePlayerScene, "hp/media/background/ChoosePlayerHermineBG.jpg");
				choosePlayerScene.setChooser("hermine");
				choosePlayerScene.setHermine();
				break;
			case "dumbledore":
				ViewUtilsHP.setBackground(choosePlayerScene, "hp/media/background/ChoosePlayerHagridBG.jpg");
				choosePlayerScene.setChooser("hagrid");
				choosePlayerScene.setHagrid();
				break;
			case "ginny":
				ViewUtilsHP.setBackground(choosePlayerScene, "hp/media/background/ChoosePlayerDumbledoreBG.jpg");
				choosePlayerScene.setChooser("dumbledore");
				choosePlayerScene.setDeumbledore();
				break;
			case "neville":
				ViewUtilsHP.setBackground(choosePlayerScene, "hp/media/background/ChoosePlayerGinnyBG.jpg");
				choosePlayerScene.setChooser("ginny");
				choosePlayerScene.setGinny();
				break;
			case "moody":
				ViewUtilsHP.setBackground(choosePlayerScene, "hp/media/background/ChoosePlayerNevilleBG.jpg");
				choosePlayerScene.setChooser("neville");
				choosePlayerScene.setNeville();
				break;
			case "snape":
				ViewUtilsHP.setBackground(choosePlayerScene, "hp/media/background/ChoosePlayerMoodyBG.jpg");
				choosePlayerScene.setChooser("moody");
				choosePlayerScene.setMoody();
				break;
			}
		});
	}

	public Scene getActualScene() {
		return actualScene;
	}

	public void setActualScene( Scene actualScene ) {
		this.actualScene = actualScene;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter( String character ) {
		this.character = character;
	}

	public ChoosePlayerSceneHP getChoosePlayerScene() {
		return choosePlayerScene;
	}

	public void setChoosePlayerScene( ChoosePlayerSceneHP choosePlayerScene ) {
		this.choosePlayerScene = choosePlayerScene;
	}

	public MainPresenterHP getMainPresenter() {
		return mainPresenter;
	}

	public void setMainPresenter( MainPresenterHP mainPresenter ) {
		this.mainPresenter = mainPresenter;
	}

	public boolean getCharacterIsSelected() {
		return characterIsSelected.get();
	}

	public SimpleBooleanProperty characterIsSelectedProperty() {
		return characterIsSelected;
	}

	public void setCharacterIsSelected( boolean characterIsSelected ) {
		this.characterIsSelected.set(characterIsSelected);
	}

	public boolean isSingleplayer() {
		return singleplayer;
	}

	public void setSingleplayer( boolean singleplayer ) {
		this.singleplayer = singleplayer;
	}

	public boolean isCreate() {
		return create;
	}

	public void setCreate(boolean create) {
		this.create = create;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

}
