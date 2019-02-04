package view.designHP.scenes.gameLobbyHP;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import org.json.JSONException;

import util.Chat;
import util.ViewUtils;
import view.design.scenes.multiplayerGame.MultiplayerGamePresenter;
import view.designHP.presenter.MainPresenterHP;
import view.designHP.scenes.multiplayerGameHp.MultiplayerGamePresenterHP;

/**
 * Created by Rene on 02.01.2016.
 */
public class GameLobbyPresenterHP {

	private MainPresenterHP mainPresenter;
	private GameLobbySceneHP gameLobbyScene;
	private Chat chat;
	private int gameID;

	public GameLobbyPresenterHP(MainPresenterHP mainPresenter) {
		this.chat = new Chat();
		this.mainPresenter = mainPresenter;
		this.gameLobbyScene = new GameLobbySceneHP();
		buttonhandle();
		mainPresenter.getScenesMap().put("gameLobbyScene", gameLobbyScene.getActualScene());
		gameLobbyScene.setLeft(posChat());
		setGameScene();
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

	public void buttonhandle() {

		gameLobbyScene.getBackToMenu().setOnAction(e -> {

			// TODO: Game aus gamesList löschen
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

		gameLobbyScene.getServer().setOnAction(e -> {

				mainPresenter.getServerPresenter().setPreviousScene("gameLobbyScene");
				
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

		gameLobbyScene.getLeaveGame().setOnAction(e -> {

			// TODO: Client aus playerList löschen
				mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("gameListScene"));
			});

		gameLobbyScene.getStartGame().setOnAction(e -> {


			try {
				mainPresenter.getClient().getCom().getSend().startGame(0); // TODO
																			// MUSSMAN
																			// WISSEN!
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			gameLobbyScene.getStartGame().setDisable(true);

		});


		gameLobbyScene.getLeaveGame().setOnAction(e -> {

			// TODO: Client aus playerList löschen
				mainPresenter.getStage().setScene(mainPresenter.getScenesMap().get("gameListScene"));
			});

	}

	public void setGameScene() {
		mainPresenter.getClient().getCom().getRcv().getClientDecoder().disableProperty().addListener((observable1, oldValue1, newValue1) -> startThread2());
		mainPresenter.getClient().getCom().getRcv().getClientDecoder().herbertProperty().addListener((observable, oldValue, newValue) -> startThread());


	}

	public void startThread() {
		Task task = new Task<Void>() {
			@Override
			public Void call() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						mainPresenter.setMultiplayerGamePresenterHP(new MultiplayerGamePresenterHP(mainPresenter, mainPresenter.getClient()
								.getCom().getRcv().getClientDecoder().getClientModel()));
						mainPresenter.getMultiplayerGamePresenterHP().getChat().getChat().textProperty()
								.bind(mainPresenter.getClient().getCom().getRcv().getClientDecoder().msgChatProperty());

					}
				});

				return null;
			}

		};
		Thread thread = new Thread(task);
		thread.start();

	}

	public void startThread2() {
		Task task2 = new Task<Void>() {
			@Override
			public Void call() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						gameLobbyScene.getStartGame().setDisable(true);
					}
				});

				return null;
			}

		};
		Thread thread = new Thread(task2);
		thread.start();

	}

	public GameLobbySceneHP getGameLobbyScene() {
		return gameLobbyScene;
	}

	public void setGameLobbyScene( GameLobbySceneHP gameLobbyScene ) {
		this.gameLobbyScene = gameLobbyScene;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public MainPresenterHP getMainPresenter() {
		return mainPresenter;
	}

	public void setMainPresenter(MainPresenterHP mainPresenter) {
		this.mainPresenter = mainPresenter;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
}
