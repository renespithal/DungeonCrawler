package view.design.scenes.gameLobby;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import org.json.JSONException;

import util.ViewUtils;
import view.design.MainPresenter;
import view.design.scenes.multiplayerGame.MultiplayerGamePresenter;

/**
 * Created by Rene on 02.01.2016.
 */
public class GameLobbyPresenter {

	private MainPresenter mainPresenter;
	private GameLobbyScene gameLobbyScene;
	private int gameID;

	public GameLobbyPresenter(MainPresenter mainPresenter) {


		this.mainPresenter = mainPresenter;
		this.gameLobbyScene = new GameLobbyScene();
		buttonDisabling();
		buttonhandle();
		mainPresenter.getScenesmap().put("gameLobbyScene", gameLobbyScene.getActualScene());
       // waitForGameToStart();
        setGameScene();
	}

	private void buttonDisabling() {

		gameLobbyScene.getServer().setDisable(!mainPresenter.getServerPresenter().getIsStarted().getValue());
		
		mainPresenter.getServerPresenter().getIsStarted().addListener(( obs, oldValue, newValue ) -> {
			gameLobbyScene.getServer().setDisable(!newValue);
		});
	}

	public void  buttonhandle(){

        gameLobbyScene.getBackToMenu().setOnAction(e -> {

        	// TODO: Game aus gamesList löschen
        	Alert alert = ViewUtils.alertConfirm("Confirm logout","Do you want to disconnect?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
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
        
        this.gameLobbyScene.getServer().setOnAction(e -> {

			mainPresenter.getServerPresenter().setPreviousScene("gameLobbyScene");
			mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("serverScene"));

		});


        

        gameLobbyScene.getLeaveGame().setOnAction(e -> {

            // TODO: Client aus playerList löschen
            mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("gameListScene"));
        });



        gameLobbyScene.getStartGame().setOnAction(e -> {
            try {
                 mainPresenter.getClient().getCom().getSend().startGame(gameID); //TODO MUSSMAN WISSEN!
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }

        });
        
        gameLobbyScene.getLeaveGame().setOnAction(e -> {
        	
        	// TODO: Client aus playerList löschen
        	mainPresenter.getStage().setScene(mainPresenter.getScenesmap().get("gameListScene"));
        });
        
    }

	public GameLobbyScene getGameLobbyScene() {
		return gameLobbyScene;
	}

    public void setGameScene(){
        mainPresenter.getClient().getCom().getRcv().getClientDecoder().disableProperty().addListener((observable1, oldValue1, newValue1) -> startThread2());
        mainPresenter.getClient().getCom().getRcv().getClientDecoder().herbertProperty().addListener((observable, oldValue, newValue) -> startThread());




    }

    public void setMainPresenter(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    public void startThread() {
        Task task = new Task<Void>() {
            @Override
            public Void call() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        mainPresenter.setMultiplayerGamePresenter(new MultiplayerGamePresenter(mainPresenter, mainPresenter.getClient().
                                getCom().getRcv().getClientDecoder().getClientModel()));
                        mainPresenter.getMultiplayerGamePresenter().setGameID(gameID);


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
            @Override public  Void  call() {
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







	public void setGameLobbyScene( GameLobbyScene gameLobbyScene ) {
		this.gameLobbyScene = gameLobbyScene;
	}

	
	public int getGameID() {
		return gameID;
	}

	
	public void setGameID( int gameID ) {
		this.gameID = gameID;
	}
	
	
}
