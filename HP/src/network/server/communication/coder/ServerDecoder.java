package network.server.communication.coder;

import static java.lang.Thread.sleep;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import model.entity.Player;
import model.util.Field;
import model.util.Position;
import network.game.GameLobby;
import network.messages.ChatMessage;
import network.messages.LoginMessage;
import network.messages.SpielInfo;
import network.messages.SpielerInfo;
import network.messages.Spielfeld;
import network.messages.SpielzugMessage;
import network.server.Server;
import network.server.connection.threads.ServerHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.ArrayConverter;

import com.google.gson.Gson;

/**
 * Created by Jenny on 02.12.2015.
 */
public class ServerDecoder {

	private static final Logger logger = LoggerFactory.getLogger(ServerDecoder.class);

	private Server server;
	private Gson gson;
	private ServerEncoder encoder;

	private int errorCode = -1;
	private int moveCount;
	private boolean harry;

	public ServerDecoder(Server server, boolean harry) {
		this.harry = harry;
		this.server = server;
		this.encoder = new ServerEncoder(server);
		this.gson = new Gson();
	}

	/* decodes message and calls right method */
	public void handle( JSONObject json ) throws JSONException, IOException, InterruptedException {
		if( isValid(json) ) {
			server.getCom().sendToClientID(Integer.parseInt(json.get("clientID").toString()), new JSONObject(ok()));
			logger.info("handle message");
			switch (json.get("type").toString()) {
			case "chat":
				chat(json);
				break;
			case "login":
				login(json);
				break;
			case "create game":
				createGame(json);
				break;
			case "join game":
				joinGame(json);
				break;
			case "start game":
				startGame(json);
				break;
			case "leave game":
				leaveGame(json);
				break;
			case "disconnect":
				disconnect(json);
				break;
			case "playermove":
				playermove(json);
				break;
			default:
				logger.error("message invalid");
				break;
			}
		} else {
			server.getCom().sendToClientID(json.getInt("clientID"), new JSONObject(error(errorMsg())));
		}
	}

	/* methods */

	private void chat( JSONObject json ) throws JSONException {
		json.remove("clientID");
		server.getCom().sendToAll(json);
		server.setControl(createControlOutput(json) + json.getString("sender") + " | " + json.getString("message") + '\n');
	}

	private void login( JSONObject json ) throws JSONException {
		String nick = json.getString("nick");
		LoginMessage login = gson.fromJson(String.valueOf(json), LoginMessage.class);
		// server.getCom().sendToNick(nick, encoder.loginSuccessfulMessage();
		server.getCom().sendToAll(new JSONObject(encoder.userAddedMessage(nick)));
		server.getCom().sendToNick(nick, new JSONObject(server.getEncoder().gameUpdate(server.getGameInfoList())));
		logger.info("game update geschickt");
		logger.info("successfully sent user added message to all");
		logger.info("user {} added", nick);
		server.setControl(createControlOutput(json) + login.getNick() + " | " + login.getGroup() + " | " + login.getVersion() + " | " + '\n');
	}

	private void createGame( JSONObject json ) throws JSONException {
		logger.info("starting to decode create game message");

		int gameID = server.getGameList().size();
		int depth = json.getInt("depth");
		int difficulty = json.getInt("difficulty");
		String nick = server.getCom().getClientList().get(json.getInt("clientID") - 1).getNick();

		GameLobby lobby = new GameLobby(gameID, json.getString("character_class"), depth, difficulty, nick, server, harry);
		server.getGameList().add(lobby);
		logger.info("new game lobby created");
		SpielInfo spielInfo = new SpielInfo(gameID, "not started", ArrayConverter.spielerInfoToArray(lobby.getPlayerInfoList()), depth, difficulty);
		server.getGameInfoList().add(spielInfo);

		//logger.info("setting gameID for new game");
		//server.getGameList().get(server.getGameList().size() - 1).setGameID(server.getGameList().size());
		//System.out.println("!!!!!!NEW GAME ID" + server.getGameList().size() );
		//SpielInfo info = server.getGameList().get(gameID+1).getSpielInfo();

		logger.info("sending game created message to all");
		server.getCom().sendToAll(new JSONObject(encoder.gameCreated(gameID, spielInfo)));
		logger.info("successfully sent game created message to all");

		logger.info("successfully decoded create game message");
		server.setControl(createControlOutput(json) + nick + " | " + json.getString("character_class") + " | " + gameID + " | " + depth + " | "
				+ difficulty + '\n');
	}

	private void joinGame( JSONObject json ) throws JSONException {
		logger.info("starting to decode join game message");

		int id = json.getInt("gameID");
		String nick = server.getCom().getClientList().get(json.getInt("clientID") - 1).getNick();
		String charClass = json.getString("character_class");
		for (int i = 0; i < server.getCom().getClientList().size(); i++) {
			}
		logger.info("adding player {} playing as {} to playerinfo list", nick, charClass);
		server.getGameList().get(id).addPlayerInfo(nick, charClass);

		logger.info("adding player {} playing as {} to player list", nick, charClass);
		server.getGameList().get(id).addPlayer(nick, charClass);
		
		logger.info("players count + 1 in gameInfoList");
		int playersCount2 = server.getGameInfoList().get(id).getPlayersCount();
		server.getGameInfoList().get(id).setPlayersCount(playersCount2 + 1);
		
		SpielInfo info = server.getGameList().get(id).getSpielInfo();
		ObservableList<Player> playerList = server.getGameList().get(id).getPlayerList();
		for (int i = 0; i < playerList.size(); i++) {

		}

		logger.info("sending player added message to all");
		server.getCom().sendToAll(new JSONObject(encoder.playerAdded(json.getInt("gameID"), info)));
		logger.info("successfully sent player added message to all");

		// server.getCom().sendToNick(nick,new
		// JSONObject(encoder.gameUpdate(server.getGameInfoList())));

		logger.info("successfully decoded join game message");
		server.setControl(createControlOutput(json) + nick + " | " + charClass + " | " + id + '\n');

	}

	private void playermove( JSONObject json ) throws JSONException, IOException {
		logger.info("starting to decode playermove message");

		SpielzugMessage input = gson.fromJson(String.valueOf(json), SpielzugMessage.class);
		int id = json.getInt("gameID");

		logger.info("adding playermove to game master");
		server.getGameList().get(id).getMaster().addSpielzug(input);

		moveCount++;
		if( moveCount == server.getGameList().get(id).getPlayersCount() ) {
			moveCount = 0;
			server.getGameList().get(id).startTimeline();

		}
		server.setControl(createControlOutput(json) + input.getNick() + " | " + input.getAction() + " | " + input.getDirection() + '\n');

		// TestPresenter.master.addSpielzug(input);

	}

	private void startGame( JSONObject json ) throws JSONException, IOException {
		logger.info("starting to decode start game message");

		logger.info("sending game started message to all");
		int gameID = json.getInt("gameID");
		server.getCom().sendToAll(new JSONObject(encoder.gameStarted(gameID)));
		server.getGameInfoList().get(gameID).setGamestate("game started");
		server.getGameList().get(gameID).setGamestate("game started");
		logger.info("successfully sent game started message to all");

		int id = gameID;
		server.getGameList().get(id).startGame();

		SpielerInfo[] spielerInfoArray = server.getGameList().get(id).getPlayerInfoListArray();
		Spielfeld[] map = server.getGameList().get(id).getModel().updateMap();

		logger.info("starting game {}", id);
		server.setControl(createControlOutput(json) + id + '\n');

		/* TODO: Timeline starten */

	}

	private void leaveGame( JSONObject json ) throws JSONException {
		logger.info("starting to decode leave game message");

		int id = json.getInt("gameID");
		String nick = json.getString("nick");
		//	String nick = server.getCom().getClientList().get(json.getInt("clientID")).getNick();
		logger.info("sending left game to all");
		server.getCom().sendToGame(new JSONObject(encoder.leftGame(id, nick)));
		server.getGameList().get(id).setPlayersCount(server.getGameList().get(id).getPlayersCount() - 1);
		for (Player player : server.getGameList().get(id).getPlayerList()) {
			if (player.getNick().equals(nick)) {
				player.setAlive(false);
				server.getGameList().get(id).getModel().getCurrentFloor().getFieldAtPos(player.getPos()).setContent(Field.cnt.FLOOR);
				player.setPos(new Position(0, 0));
			}
		}
		logger.info("successfully sent left game message to all");

		logger.info("sending game ended to all");
		server.getCom().sendToAll(new JSONObject(encoder.gameEnded(id)));
		logger.info("successfully sent game ended message to all");

		logger.info("ending game {}", id);
		server.getGameList().get(id).endGame(nick);
		if( !server.getGameList().get(id).isVisible() ) {
			logger.info("sending game {} deleted", id);
			server.getCom().sendToAll(new JSONObject(encoder.gameDeleted(id)));
			logger.info("successfully sent game deleted message to all");
		}
		logger.info("successfully decoded leave game message");

		server.setControl(createControlOutput(json) + nick + " | " + id + '\n');
	}

	private void disconnect( JSONObject json ) throws JSONException, IOException, InterruptedException {
		logger.info("starting to decode disconnect message");

		for (int i = 0; i < server.getCom().getClientList().size(); i++) {
			if( server.getCom().getClientList().get(i).getHandlerID() == json.getInt("clientID") ) {

				String deletedNick = server.getCom().getClientList().get(i).getNick();
				logger.info("sending disconnect message to user {}", deletedNick);
				server.getCom().sendToNick(deletedNick, new JSONObject(encoder.disconnectedMessage("you are disconnected")));
				logger.info("successfully sent disconnect message to user {}", deletedNick);

				logger.info("sending user left message to all except user {}", deletedNick);
				server.getCom().sendToAllExcept(new JSONObject(encoder.userLeftMessage(deletedNick)), deletedNick);
				logger.info("successfully sent user left message to all except user {}", deletedNick);
				sleep(5000);
				server.getCom().getClientList().get(i).close();
				server.getRegisteredClients().remove(server.getCom().getClientList().get(i));
				server.getCom().getClientList().remove(i);
				logger.info("Client " + deletedNick + " wurde geschlossen");

				server.setControl(createControlOutput(json) + deletedNick + " | " + '\n');
			}
		}
		logger.info("successfully decoded disconnect message");
	}

	/* ok & error messages */
	private String ok() {
		return encoder.okMessage();
	}

	private String error( String msg ) {
		return encoder.errorMessage(msg);
	}

	private boolean isValid( JSONObject json ) throws JSONException {
		try {
			gson.fromJson(String.valueOf(json), Object.class);
			if( json.getString("type").equals("login") ) {
				return nickUnique(json);
			}
			return true;
		} catch (com.google.gson.JsonSyntaxException ex) {
			setErrorCode(1);
			return false;
		} catch (com.google.gson.JsonParseException e) {
			setErrorCode(2);
			return false;
		}
	}

	private boolean nickUnique( JSONObject json ) throws JSONException {
		int id = json.getInt("clientID");
		String nick = json.getString("nick");
		ArrayList<ServerHandler> u = new ArrayList<>();
		int counter = 0;
		for (int i = 0; i < server.getRegisteredClients().size(); i++) {
			if( server.getRegisteredClients().get(i).getNick().equals(nick) ) {
				counter++;
			}
			if( counter >= 2 ) {
				setErrorCode(3);
				ServerHandler h = server.getRegisteredClients().get(i);
				h.getInputHandler().setLogin(false);
				server.getRegisteredClients().remove(i);
				return false;
			}
		}
		return true;
	}

	private String errorMsg() {
		switch (getErrorCode()) {
		case 1:
			return "syntax error in json message";
		case 2:
			return "parse error";
		case 3:
			return "your nickname is already taken. Please choose another one";
		default:
			return "not an error int";
		}
		/* TODO create message */
	}

	private String createControlOutput( JSONObject json ) throws JSONException {
		String type = json.getString("type");
		return type + " : " + '\n';
	}

	/* Getter & Setter */
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode( int errorCode ) {
		this.errorCode = errorCode;
	}


}
