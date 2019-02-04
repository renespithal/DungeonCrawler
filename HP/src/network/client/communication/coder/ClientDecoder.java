package network.client.communication.coder;

import java.io.IOException;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.ClientModel;
import model.Floor;
import network.client.communication.ClientCommunication;
import network.messages.ChatMessage;
import network.messages.Disconnected;
import network.messages.GameCreatedMessage;
import network.messages.GameStarted;
import network.messages.GameUpdate;
import network.messages.PlayerAddedMessage;
import network.messages.SpielInfo;
import network.messages.SpielerInfo;
import network.messages.StateupdateMessage;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.ArrayConverter;
import util.ViewUtils;
import view.design.MainPresenter;

import com.google.gson.Gson;
import view.designHP.presenter.MainPresenterHP;

/**
 * Created by Jenny on 01.12.2015.
 */
public class ClientDecoder {

	private static final Logger logger = LoggerFactory.getLogger(ClientDecoder.class);

	private Gson gson = new Gson();

	private ClientCommunication com;
	private boolean started;
	private PlayerDecoder playerDecoder;
	private MoveDecoder moveDecoder = new MoveDecoder();
	private ClientModel clientModel;
	private String selfName;
	private FieldDecoder fieldDecoder;
	private MainPresenter mainPresenter;
	private MainPresenterHP mainPresenterHP;
	private SimpleBooleanProperty herbert = new SimpleBooleanProperty();
	private SimpleBooleanProperty disable = new SimpleBooleanProperty();
	private SimpleStringProperty msgChat;
	private boolean alreadyInList = false;

	public ClientDecoder(ClientCommunication com, String selfName, MainPresenter mainPresenter, MainPresenterHP mainPresenterHP) {
		this.mainPresenter = mainPresenter;
		this.mainPresenterHP = mainPresenterHP;
		this.selfName = selfName;
		this.com = com;
		this.started = false;
		this.msgChat = new SimpleStringProperty("");

	}

	/* decodes message and calls right method */
	public void handle( JSONObject json ) throws JSONException, IOException {
		switch (json.get("type").toString()) {
		case "chat":
			chat(json);
			break;
		case "user added":
			userAdded(json);
			break;
		case "login successful":
			loginSuccessful(json);
			break;
		case "user left":
			userLeft(json);
			break;
		case "disconnected":
			disconnected(json);
			break;
		case "player added":
			playerAdded(json);
			break;
		case "stateupdate":
			stateupdate(json);
			break;
		case "game update":
			gameUpdate(json);
			break;
		case "game created":
			gameCreated(json);
			break;
		case "game started":
			gameStarted(json);
			break;
			case "leave game":
			leftGame(json);
			break;
		case "game ended":
			gameEnded(json);
			break;
		case "game deleted":
			gameDeleted(json);
			break;
		case "ok":
			okMsg();
			break;
		case "error":
			errorMsg(json);
			break;
		default:
			logger.error("Message konnte nicht geswitched werden");
			break;
		}
	}

	private void gameUpdate( JSONObject json ) {
		GameUpdate msg = gson.fromJson(String.valueOf(json), GameUpdate.class);
		SpielInfo[] games = msg.getGames();
		if( games != null ) {
			for (int i = 0; i < games.length; i++) {
				com.getClient().getGameList().add(games[i]);
				logger.info("set game list");
			}
		}

	}

	/* methods */

	/*
	 * !! TODO: Verarbeitung der eingegangenen Messages im
	 * ClientMaster/Presenter und Veränderung des ClientModel !!
	 */

	private void loginSuccessful( JSONObject json ) {
		logger.info("login successful");
	}

	private void chat( JSONObject json ) throws JSONException {
		logger.info("[ {} ] {} : ", json.get("timestamp").toString(), json.get("sender").toString(), json.get("message"));
		ChatMessage chat = gson.fromJson((String.valueOf(json)), ChatMessage.class);
		String time = chat.getTimestamp().substring(11,16);
		msgChat.setValue(msgChat.getValue() + "[" + time + "] " + chat.getSender() + ": " + chat.getMessage() + '\n');
		//scroll to end
		//mainPresenterHP.getGameListPresenterHP().getChat().getChat().requestFocus();
		//mainPresenterHP.getGameLobbyPresenterHP().getChat().getChat().end();
		if(mainPresenterHP != null){
			if(!mainPresenterHP.getChat().isOut() && !chat.getSender().equals(mainPresenterHP.getClient().getNick())) {
				mainPresenterHP.getChat().setNotify(true);
				}
		} else if (mainPresenter != null) {
			mainPresenter.getMultiplayerGamePresenter().getMultiplayerGameScene().getTextArea().appendText(msgChat.getValue());
		}
	}

	private void userAdded( JSONObject json ) throws JSONException {
		logger.info("Spieler {} wurde hinzugefügt", json.get("nick").toString());
		/*
		 * TODO in view ausgeben, dass Spieler hinzugefügt wurde (ggf über alert
		 * box)
		 */
	}

	private void userLeft( JSONObject json ) throws JSONException {
		logger.info("Spieler {} wurde entfernt", json.get("nick").toString());
		/*
		 * TODO in view ausgeben, dass Spieler entfernt wurde (ggf über alert
		 * box)
		 */
	}

	private void gameCreated( JSONObject json ) throws JSONException {
		int id = json.getInt("gameID");
		logger.info("Spiel {} wurde erstellt", id);
		String gamestate = "not started";
		GameCreatedMessage created = gson.fromJson((String.valueOf(json)), GameCreatedMessage.class);
		SpielInfo info = created.getGame();
		com.getClient().getGameList().add(info);
		}

	private void disconnected( JSONObject json ) throws IOException, JSONException {
		logger.info("Bestätigung von server, Socket wird nun geschlossen");
		com.getSend().close();
		com.getRcv().close();
		com.getClient().getConnect().setTcpConnected(false);
		Disconnected dis = gson.fromJson((String.valueOf(json)), Disconnected.class);
		if( dis.getMessage().equals("Server closed") ) {
			Platform.runLater(( ) -> {
				Alert alert = ViewUtils.alertError("Server closed", "Please search for a new server");
				Optional<ButtonType> result = alert.showAndWait();
				if( result.get() == ButtonType.OK ) {
					com.getClient().serverErrorProperty().setValue(true);
				}
			});

		}
	}

	private void playerAdded( JSONObject json ) throws JSONException {
		logger.info("player added message received");
		logger.info("Spieler ist dem Spiel {} beigetreten", json.get("gameID").toString());

		PlayerAddedMessage msg = gson.fromJson(String.valueOf(json), PlayerAddedMessage.class);
		SpielInfo info = msg.getGame();
		int id = json.getInt("gameID");
		int playersCount = info.getPlayersCount();
		
		com.getClient().getGameList().get(id).setPlayersCount(playersCount);
		if (mainPresenterHP == null) {
			com.getClient().getMainPresenter().getGameListPresenter().getGameListScene().getGamesTable().refresh();
		} else {
			mainPresenterHP.getGameListPresenter().getGameListScene().getGamesTable().refresh();
		}
		SpielerInfo[] players = info.getPlayers();
		ObservableList<SpielerInfo> helpList;
		if( players.length != 0 ) {
			ObservableList<SpielerInfo> list = ArrayConverter.ArrayToSpielerInfo(players);
			helpList = ArrayConverter.ArrayToSpielerInfo(players);
			if( list != null ) {
				ObservableList<SpielerInfo> newList = FXCollections.observableArrayList();
				ObservableList<SpielerInfo> clientPlayerList = com.getClient().getPlayerList();
				for (int i = 0; i < clientPlayerList.size(); i++) {
					}
				int i = -1;
				for (SpielerInfo spielerInfo : list) {
					i++;
					for (int j = 0; j < clientPlayerList.size(); j++) {
						if( spielerInfo.getName().equals(clientPlayerList.get(j).getName()) ) {
							helpList.remove(helpList.get(i));
							i--;
							}
					}
				}
			}

			for (int i = 0; i < helpList.size(); i++) {
				com.getClient().getPlayerList().add(helpList.get(i));
				}
		}
	}

	private void stateupdate( JSONObject json ) throws JSONException {
		logger.info("Neues Spielupdate von Spiel {} eingetroffen", json.get("gameID").toString());
		Gson gson = new Gson();
		StateupdateMessage stateupdateMessage = gson.fromJson(json.toString(), StateupdateMessage.class);


		if (started) {
			moveDecoder.decodeMoves(stateupdateMessage.getMoves(), clientModel.getPlayers(), clientModel);
			moveDecoder.decodeMonsterMoves(stateupdateMessage.getMoves(), clientModel.getMonsters());
			playerDecoder.playerUpdate(stateupdateMessage.getPlayers(), clientModel);
			playerDecoder.monsterUpdate(stateupdateMessage.getPlayers());
			clientModel.setPlayers(playerDecoder.getPlayerList());
			clientModel.setMonsters(playerDecoder.getMonsterList());
			if (mainPresenterHP == null && mainPresenter.getMultiplayerGamePresenter() != null) {
				mainPresenter.getMultiplayerGamePresenter().getMultiplayerGameScene().integerPropertyProperty()
						.set(mainPresenter.getMultiplayerGamePresenter().getMultiplayerGameScene().getIntegerProperty() + 1);
				mainPresenter.getMultiplayerGamePresenter().setNotyetSubmitted(true);
			} else if (mainPresenterHP != null && mainPresenterHP.getMultiplayerGamePresenterHP().
					getSingleplayerGameScene() != null) {
				mainPresenterHP.getMultiplayerGamePresenterHP().getSingleplayerGameScene().integerPropertyProperty()
						.set(mainPresenterHP.getMultiplayerGamePresenterHP().getSingleplayerGameScene().getIntegerProperty() + 1);
				mainPresenterHP.getMultiplayerGamePresenterHP().setNotyetSubmitted(true);
			}

		} else {
			disableProperty().set(true);
			this.playerDecoder = new PlayerDecoder(stateupdateMessage.getPlayers());
			this.fieldDecoder = new FieldDecoder(stateupdateMessage.getMap());
			Floor floor = new Floor(fieldDecoder.getFields(), stateupdateMessage.getPlayers(), fieldDecoder.getXSize(), fieldDecoder.getYSize());
			if (mainPresenterHP == null) {
				this.clientModel = new ClientModel(floor, selfName, playerDecoder.getPlayerList(), playerDecoder.getMonsterList(), false);
			} else {
				this.clientModel = new ClientModel(floor, selfName, playerDecoder.getPlayerList(), playerDecoder.getMonsterList(), true);
			}
			playerDecoder.doFirstSteps(clientModel, stateupdateMessage.getPlayers());
			herbertProperty().set(true);
			started = true;

		}
		if( moveDecoder.isStairDown() ) {
			floorChange(stateupdateMessage);
			moveDecoder.setStairDown(false);
		}
	}

	private void floorChange( StateupdateMessage msg ) {
		fieldDecoder.getXandY(msg.getMap());
		fieldDecoder.decodeSpielfeld(msg.getMap());
		Floor floor = new Floor(fieldDecoder.getFields(), msg.getPlayers(), fieldDecoder.getXSize(), fieldDecoder.getYSize());
		ClientModel.setCurrentFloor(floor);
		clientModel.searchChests();
		clientModel.getMonsters().clear();
		clientModel.getPlayers().clear();
		playerDecoder.floorChange(msg.getPlayers(), clientModel);
		clientModel.setMonsters(playerDecoder.getMonsterList());
		clientModel.setPlayers(playerDecoder.getPlayerList());
		clientModel.searchSelfPlayer(selfName);
		if (mainPresenterHP == null) {
			mainPresenter.getMultiplayerGamePresenter().getMultiplayerGameScene().floorChangePropertyProperty()
					.set(mainPresenter.getMultiplayerGamePresenter().getMultiplayerGameScene().getIntegerProperty() + 1);
			mainPresenter.getMultiplayerGamePresenter().getMultiplayerGameScene().integerPropertyProperty()
					.set(mainPresenter.getMultiplayerGamePresenter().getMultiplayerGameScene().getIntegerProperty() + 1);
		} else {
			mainPresenterHP.getMultiplayerGamePresenterHP().getSingleplayerGameScene().floorChangePropertyProperty()
					.set(mainPresenterHP.getMultiplayerGamePresenterHP().getSingleplayerGameScene().getIntegerProperty() + 1);
			mainPresenterHP.getMultiplayerGamePresenterHP().getSingleplayerGameScene().integerPropertyProperty()
					.set(mainPresenterHP.getMultiplayerGamePresenterHP().getSingleplayerGameScene().getIntegerProperty() + 1);
		}

	}

	private void gameStarted( JSONObject json ) throws JSONException {
		
		int id = json.getInt("gameID");
		logger.info("Spiel {} wurde gestartet", id);
		com.getClient().getGameList().get(id).setGamestate("game started");
		if (mainPresenterHP == null) {
			com.getClient().getMainPresenter().getGameListPresenter().getGameListScene().getGamesTable().refresh();
		} else {
			//TODO: HP Einbindung
		}
	}

	private void gameEnded( JSONObject json ) throws JSONException {
		
		int id = json.getInt("gameID");
		com.getClient().getGameList().get(id).setGamestate("game ended");
		if (mainPresenterHP == null) {
			com.getClient().getMainPresenter().getGameListPresenter().getGameListScene().getGamesTable().refresh();
		} else {
			//TODO: HP Einbindung
		}
	}

	private void leftGame(JSONObject json) throws JSONException {
		/* TODO */

		String leftNick = json.getString("nick");
		playerDecoder.deletePlayer(leftNick);

	}

	private void gameDeleted( JSONObject json ) {
		/* TODO */
	}

	private void okMsg() {
		logger.info("Nachricht wurde vom Server validiert");
		com.getSend().setError(1);
		com.getSend().setCheck(false);
	}

	private void errorMsg( JSONObject json ) throws JSONException {
		logger.info("Fehler {} in gesendeter Nachricht", json.get("message"));
		com.getSend().setError(-1);
		com.getSend().setCheck(false);
	}

	/* Getter & Setter */
	public ClientCommunication getCom() {
		return com;
	}

	public void setCom( ClientCommunication com ) {
		this.com = com;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted( boolean started ) {
		this.started = started;
	}

	public PlayerDecoder getPlayerDecoder() {
		return playerDecoder;
	}

	public void setPlayerDecoder( PlayerDecoder playerDecoder ) {
		this.playerDecoder = playerDecoder;
	}

	public MoveDecoder getMoveDecoder() {
		return moveDecoder;
	}

	public void setMoveDecoder( MoveDecoder moveDecoder ) {
		this.moveDecoder = moveDecoder;
	}

	public ClientModel getClientModel() {
		return clientModel;
	}

	public void setClientModel( ClientModel clientModel ) {
		this.clientModel = clientModel;
	}

	public String getSelfName() {
		return selfName;
	}

	public void setSelfName( String selfName ) {
		this.selfName = selfName;
	}

	public FieldDecoder getFieldDecoder() {
		return fieldDecoder;
	}

	public void setFieldDecoder( FieldDecoder fieldDecoder ) {
		this.fieldDecoder = fieldDecoder;
	}

	public MainPresenter getMainPresenter() {
		return mainPresenter;
	}

	public void setMainPresenter( MainPresenter mainPresenter ) {
		this.mainPresenter = mainPresenter;
	}

	public String getMsgChat() {
		return msgChat.get();
	}

	public SimpleStringProperty msgChatProperty() {
		return msgChat;
	}

	public void setMsgChat( String msgChat ) {
		this.msgChat.set(msgChat);
	}

	public boolean getHerbert() {
		return herbert.get();
	}

	public SimpleBooleanProperty herbertProperty() {
		return herbert;
	}

	public void setHerbert( boolean herbert ) {
		this.herbert.set(herbert);
	}

	public boolean getDisable() {
		return disable.get();
	}

	public SimpleBooleanProperty disableProperty() {
		return disable;
	}

	public void setDisable( boolean disable ) {
		this.disable.set(disable);
	}


}
