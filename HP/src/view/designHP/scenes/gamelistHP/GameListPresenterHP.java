package view.designHP.scenes.gamelistHP;

import java.io.IOException;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import network.messages.ChatMessage;
import network.messages.SpielInfo;

import org.json.JSONException;

import util.Chat;
import util.ViewUtils;
import view.designHP.presenter.MainPresenterHP;
import view.designHP.scenes.gameLobbyHP.GameLobbyPresenterHP;

/**
 * Created by Rusty on 18.12.2015.
 */
public class GameListPresenterHP {

	private MainPresenterHP mainPresenter;
	private GameListSceneHP gameListScene;
	private int gameID;
	private Chat chat;

	public GameListPresenterHP(MainPresenterHP mainPresenter) {
		this.chat = new Chat();
		this.mainPresenter = mainPresenter;
		this.gameListScene = new GameListSceneHP();
		this.gameID = -1;
		bindPlayers();
		buttonDisabling();
		buttonhandle();
		mainPresenter.getScenesMap().put("gameListScene", gameListScene.getActualScene());
		gameListScene.setLeft(posChat());
	}

	public VBox posChat() {
		VBox vBox = new VBox();

		vBox.getChildren().addAll(getChat().getAll(), getChat().getIcon());
		vBox.setPadding(new Insets(150,-260,35,5));

		getChat().getInput().setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ENTER) {
				String msg = getChat().getInput().getText();
				String client = mainPresenter.getClient().getNick();
				try {
					mainPresenter.getClient().getCom().getSend().sendJSON(mainPresenter.getClient().getCom().getSend().getEncoder().chatMessage(msg,client));
					getChat().getInput().clear();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		});

		getChat().getAll().setVisible(false);

		return vBox;
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
				mainPresenter.setGameLobbyPresenterHP(new GameLobbyPresenterHP(this.mainPresenter));
				mainPresenter.getChoosePlayerPresenter().setCreate(false);
				mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("choosePlayerScene"));
				//mainPresenter.getChoosePlayerPresenter().getChoosePlayerScene().setLeft(mainPresenter.posChat());
			}
			else {
				Alert alert = ViewUtils.alertError("Error", "Game is unavailable");
				alert.showAndWait();
			}
		});

		this.gameListScene.getCreate().setOnAction(e -> {
			int currentGameID = mainPresenter.getClient().getGameList().size();
			mainPresenter.getChoosePlayerPresenter().setGameID(currentGameID);
			mainPresenter.getChoosePlayerPresenter().setCreate(true);
			mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("choosePlayerScene"));

		});

		this.gameListScene.getBackToMenu().setOnAction(e -> {

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

		this.gameListScene.getServer().setOnAction(e -> {

			mainPresenter.getServerPresenter().setPreviousScene("gameListScene");
			if(mainPresenter.getServer().getRegisteredClients()!= null){
                mainPresenter.getServerPresenter().getServerScene().getClientsTable().setItems(mainPresenter.getServer().getRegisteredClients());}
            else {
				mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("serverScene"));
			}
            if(mainPresenter.getServer().getGameList()!= null){
                mainPresenter.getServerPresenter().getServerScene().getGamesTable().setItems(mainPresenter.getServer().getGameList());}
            else{
                }
			mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("serverScene"));
		});
	}

	public GameListSceneHP getGameListScene() {
		return gameListScene;
	}

	public void setGameListScene( GameListSceneHP gameListScene ) {
		this.gameListScene = gameListScene;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID( int gameID ) {
		this.gameID = gameID;
	}

	public MainPresenterHP getMainPresenter() {
		return mainPresenter;
	}

	public void setMainPresenter(MainPresenterHP mainPresenter) {
		this.mainPresenter = mainPresenter;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}
}
