package network.server.communication.coder;

import javafx.collections.ObservableList;
import network.messages.ChatMessage;
import network.messages.Disconnected;
import network.messages.ErrorMessage;
import network.messages.GameCreatedMessage;
import network.messages.GameDeletedMessage;
import network.messages.GameEndedMessage;
import network.messages.GameStarted;
import network.messages.GameUpdate;
import network.messages.LeftGameMessage;
import network.messages.LoginSuccessful;
import network.messages.OkMessage;
import network.messages.PlayerAddedMessage;
import network.messages.SpielInfo;
import network.messages.SpielerInfo;
import network.messages.Spielfeld;
import network.messages.SpielzugMessage;
import network.messages.StateupdateMessage;
import network.messages.UserAdded;
import network.messages.UserLeft;
import network.server.Server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import util.ArrayConverter;

/**
 * Created by Jenny on 02.12.2015.
 */
public class ServerEncoder {
	
	private static final Logger logger = LoggerFactory.getLogger(ServerEncoder.class);

    private Gson gson;
    private Server server;

    public ServerEncoder(Server server) {
        this.gson = new Gson();
        this.server = server;
    }

    public String chatMessage(String message) {
        return gson.toJson(new ChatMessage(message));
    }

    public String chatMessage(String message, String nick) {
        return gson.toJson(new ChatMessage(message, nick));
    }

    public String loginSuccessfulMessage(String[] expansions, String[] nick_array, SpielInfo[] game_array) {
    	logger.info("encoding login successful message");
        return gson.toJson(new LoginSuccessful(expansions,nick_array,game_array));
    }

    public String userAddedMessage(String nick) {
    	logger.info("encoding user added message");
        return gson.toJson(new UserAdded(nick));
    }

    public String userLeftMessage(String nick) {
    	logger.info("encoding user left message");
        return gson.toJson(new UserLeft(nick));
    }

    public String playerAdded(int gameID, SpielInfo game) {
    	logger.info("encoding player added message");
    	return gson.toJson(new PlayerAddedMessage(gameID, game));
    }
    
    public String stateupdate(int gameID, SpielerInfo[] players, SpielzugMessage[] moves, Spielfeld[] map){
    	logger.info("encoding stateupdate");
    	return gson.toJson(new StateupdateMessage(gameID, players, moves, map));
    }

    public String gameUpdate(ObservableList<SpielInfo> games) {
        return gson.toJson(new GameUpdate(ArrayConverter.spielInfoToArray(games)));
    }

    public String gameCreated(int gameID, SpielInfo info) {
    	logger.info("encoding game created message");
        return gson.toJson(new GameCreatedMessage(gameID, info));
    }

    public String gameStarted(int gameID) {
    	logger.info("encoding game started message");
        return gson.toJson(new GameStarted(gameID));
    }

    //public String spielinfo(int gameID, String gamestate, List<SpielerInfo> players, int depth, int difficulty) {}

    public String leftGame(int gameID, String nick){
    	logger.info("encoding left game message");
        return gson.toJson(new LeftGameMessage(gameID, nick));
    }

    public String disconnectedMessage(String msg) {
    	logger.info("encoding disconnect message");
        return gson.toJson(new Disconnected(msg));
    }

    public String okMessage() {
    	logger.info("encoding ok message");
        return gson.toJson(new OkMessage());
    }

    public String errorMessage(String msg) {
    	logger.info("encoding error message");
        return gson.toJson(new ErrorMessage(msg));
    }

    public String gameEnded(int gameID, String nick) {
    	logger.info("encoding game ended message");
        return gson.toJson(new GameEndedMessage(gameID, nick));
    }

    public String gameEnded(int gameID) {
    	logger.info("encoding game ended message");
        return gson.toJson(new GameEndedMessage(gameID));
    }

    public String gameDeleted(int gameID) {
    	logger.info("encoding game deleted message");
        return gson.toJson(new GameDeletedMessage(gameID));
    }

}
