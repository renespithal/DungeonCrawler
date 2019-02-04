package network.client.communication.coder;

import com.google.gson.Gson;

import network.messages.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jenny on 01.12.2015.
 */
public class ClientEncoder {

    private static final Logger logger = LoggerFactory.getLogger(ClientEncoder.class);

    private Gson gson;

    public ClientEncoder() {
        this.gson = new Gson();
    }

    public String loginMessage(String nick, String[] expansions) {
        return gson.toJson(new LoginMessage(nick, expansions));
    }

    public String chatMessage(String message, String sender) {
        return gson.toJson(new ChatMessage(message, sender));
    }
    
    public String createGame(String character_class, int depth, int difficulty){
        return gson.toJson(new CreateGameMessage(character_class, depth, difficulty));
    }
    
    public String joinGame(int gameID, String character_class){
        return gson.toJson(new JoinGameMessage(gameID, character_class));
    }
    
    public String playermove(){
        return null;
        //return gson.toJson(new SpielzugMessage());
        // TODO : verpacken der entscheidung eines clients in einen spielzug
    }

    public String leaveGame(){
        return gson.toJson(new LeaveGameMessage(0, "Herbert")); /* TODO getGameID */
    }

    public String disconnect() {
        return gson.toJson(new Disconnect());
    }

    public String startGame(int gameID) {
        return gson.toJson(new StartGame(gameID));
    }



}
