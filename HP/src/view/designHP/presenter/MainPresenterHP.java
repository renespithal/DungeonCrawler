package view.designHP.presenter;

import java.io.IOException;
import java.util.HashMap;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import network.client.Client;
import network.server.Server;

import org.json.JSONException;

import util.Chat;
import view.Presenter;
import view.design.scenes.MusicPlayer;
import view.designHP.scenes.choosePlayerHP.ChoosePlayerPresenterHP;
import view.designHP.scenes.creategameHP.CreateGamePresenterHP;
import view.designHP.scenes.gameLobbyHP.GameLobbyPresenterHP;
import view.designHP.scenes.gameOverHP.GameOverPresenterHP;
import view.designHP.scenes.gamelistHP.GameListPresenterHP;
import view.designHP.scenes.loginHP.LoginPresenterHP;
import view.designHP.scenes.mainMenuHP.MainMenuPresenterHP;
import view.designHP.scenes.multiplayerGameHp.MultiplayerGamePresenterHP;
import view.designHP.scenes.serverHP.ServerPresenterHP;
import view.designHP.scenes.serverListHP.ServerListPresenterHP;
import view.designHP.scenes.singleplayerGameHP.SinglePlayerGameWon;
import view.designHP.scenes.singleplayerGameHP.SingleplayerGamePresenterHP;
//import view.designHP.scenes.singleplayerGameHP.SingleplayerGamePresenterHP;
import view.designHP.scenes.startSceneHP.StartScenePresenterHP;

/**
 * Created by Jenny on 19.01.2016.
 */
public class MainPresenterHP {

    private Stage stage;
    private HashMap<String, Scene> scenesMap;
    private Server server;
    private Client client;

    private MusicPlayer musicPlayer;

    private Chat chat;

    /* Subpresenter */
    private StartScenePresenterHP startScenePresenter;
    private MainMenuPresenterHP mainMenuPresenter;
    private ServerListPresenterHP serverListPresenter;
    private ServerPresenterHP serverPresenter;
    private LoginPresenterHP loginPresenter;
    private GameListPresenterHP gameListPresenter;
    private CreateGamePresenterHP createGamePresenter;
    private GameLobbyPresenterHP gameLobbyPresenter;
    private ChoosePlayerPresenterHP choosePlayerPresenter;
    private MultiplayerGamePresenterHP multiplayerGamePresenterHP;
    private GameOverPresenterHP gameOverPresenterHP;
    private SinglePlayerGameWon singlePlayerGameWon;
    private SingleplayerGamePresenterHP singleplayerGamePresenter;

    private Presenter presenter;
    
    public MainPresenterHP(Stage primaryStage, Presenter p) {
        this.chat = new Chat();
        this.presenter =  p;
        this.stage = primaryStage;
        this.scenesMap = new HashMap<String, Scene>();
        addMusic();
        createSubPresenter();
        stage.setScene(scenesMap.get("startScene"));
    }


    public VBox posChat() {
        VBox vBox = new VBox();
        getChat().getChat().textProperty().bind(this.getClient().getCom().getRcv().getClientDecoder().msgChatProperty());

        vBox.getChildren().addAll(getChat().getAll(), getChat().getIcon());
        vBox.setPadding(new Insets(150,-260,35,5));

        getChat().getInput().setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                String msg = getChat().getInput().getText();
                String client = this.getClient().getNick();
                try {
                    this.getClient().getCom().getSend().sendJSON(this.getClient().getCom().getSend().getEncoder().chatMessage(msg,client));
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

    public void addMusic() {
        musicPlayer = new MusicPlayer();
    }

    public void createSubPresenter() {
        startScenePresenter = new StartScenePresenterHP(this);
        mainMenuPresenter = new MainMenuPresenterHP(this);
        serverPresenter = new ServerPresenterHP(this);
        serverListPresenter = new ServerListPresenterHP(this);
        loginPresenter = new LoginPresenterHP(this);
        gameListPresenter = new GameListPresenterHP(this);
        createGamePresenter = new CreateGamePresenterHP(this);
        choosePlayerPresenter = new ChoosePlayerPresenterHP(this);
        singleplayerGamePresenter = new SingleplayerGamePresenterHP(this);
        
    }

    /* Getter & Setter */
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public HashMap<String, Scene> getScenesMap() {
        return scenesMap;
    }

    public void setScenesMap(HashMap<String, Scene> scenesMap) {
        this.scenesMap = scenesMap;
    }

    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }

    public void setMusicPlayer(MusicPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;
    }

    public StartScenePresenterHP getStartScenePresenter() {
        return startScenePresenter;
    }

    public void setStartScenePresenter(StartScenePresenterHP startScenePresenter) {
        this.startScenePresenter = startScenePresenter;
    }

    public MainMenuPresenterHP getMainMenuPresenter() {
        return mainMenuPresenter;
    }

    public void setMainMenuPresenter(MainMenuPresenterHP mainMenuPresenter) {
        this.mainMenuPresenter = mainMenuPresenter;
    }

	public ServerPresenterHP getServerPresenter() {
		return serverPresenter;
	}

	public void setServerPresenter( ServerPresenterHP serverPresenter ) {
		this.serverPresenter = serverPresenter;
	}

	
	public Server getServer() {
		return server;
	}

	
	public void setServer( Server server ) {
		this.server = server;
	}

	public Client getClient() {
		return client;
	}

	
	public void setClient( Client client ) {
		this.client = client;
	}

	
	public ServerListPresenterHP getServerListPresenterHP() {
		return serverListPresenter;
	}
	
	public void setServerListPresenterHP( ServerListPresenterHP serverListPresenter ) {
		this.serverListPresenter = serverListPresenter;
	}
	
	public LoginPresenterHP getLoginPresenterHP() {
		return loginPresenter;
	}
	
	public void setLoginPresenterHP( LoginPresenterHP loginPresenter ) {
		this.loginPresenter = loginPresenter;
	}
	
	public GameListPresenterHP getGameListPresenterHP() {
		return gameListPresenter;
	}
	
	public void setGameListPresenterHP( GameListPresenterHP gameListPresenter) {
		this.gameListPresenter = gameListPresenter;
	}
	
	public CreateGamePresenterHP getCreateGamePresenterHP() {
		return createGamePresenter;
	}
	
	public void setCreateGamePresenterHP( CreateGamePresenterHP createGamePresenter) {
		this.createGamePresenter = createGamePresenter;
	}
	
	public GameLobbyPresenterHP getGameLobbyPresenterHP() {
		return gameLobbyPresenter;
	}
	
	public void setGameLobbyPresenterHP( GameLobbyPresenterHP gameLobbyPresenter) {
		this.gameLobbyPresenter = gameLobbyPresenter;
	}

    public Presenter getPresenter() {
        return presenter;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public ServerListPresenterHP getServerListPresenter() {
        return serverListPresenter;
    }

    public void setServerListPresenter(ServerListPresenterHP serverListPresenter) {
        this.serverListPresenter = serverListPresenter;
    }

    public LoginPresenterHP getLoginPresenter() {
        return loginPresenter;
    }

    public void setLoginPresenter(LoginPresenterHP loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    public GameListPresenterHP getGameListPresenter() {
        return gameListPresenter;
    }

    public void setGameListPresenter(GameListPresenterHP gameListPresenter) {
        this.gameListPresenter = gameListPresenter;
    }

    public CreateGamePresenterHP getCreateGamePresenter() {
        return createGamePresenter;
    }

    public void setCreateGamePresenter(CreateGamePresenterHP createGamePresenter) {
        this.createGamePresenter = createGamePresenter;
    }

    public GameLobbyPresenterHP getGameLobbyPresenter() {
        return gameLobbyPresenter;
    }

    public void setGameLobbyPresenter(GameLobbyPresenterHP gameLobbyPresenter) {
        this.gameLobbyPresenter = gameLobbyPresenter;
    }

    public ChoosePlayerPresenterHP getChoosePlayerPresenter() {
        return choosePlayerPresenter;
    }

    public void setChoosePlayerPresenter(ChoosePlayerPresenterHP choosePlayerPresenter) {
        this.choosePlayerPresenter = choosePlayerPresenter;
    }


	
public SingleplayerGamePresenterHP getSingleplayerGamePresenter() {
        return singleplayerGamePresenter;
	}


	
	public void setSingleplayerGamePresenterHP( SingleplayerGamePresenterHP singleplayerGamePresenter ) {
		this.singleplayerGamePresenter = singleplayerGamePresenter;
	}

    public MultiplayerGamePresenterHP getMultiplayerGamePresenterHP() {
        return multiplayerGamePresenterHP;
    }

    public void setMultiplayerGamePresenterHP(MultiplayerGamePresenterHP multiplayerGamePresenterHP) {
        this.multiplayerGamePresenterHP = multiplayerGamePresenterHP;
    }

    public SinglePlayerGameWon getSinglePlayerGameWon() {
        return singlePlayerGameWon;
    }

    public void setSinglePlayerGameWon(SinglePlayerGameWon singlePlayerGameWon) {
        this.singlePlayerGameWon = singlePlayerGameWon;
    }

    public GameOverPresenterHP getGameOverPresenterHP() {
        return gameOverPresenterHP;
    }

    public void setGameOverPresenterHP(GameOverPresenterHP gameOverPresenterHP) {
        this.gameOverPresenterHP = gameOverPresenterHP;
    }
}
