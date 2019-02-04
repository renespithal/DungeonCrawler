package network.messages;

import network.interfaces.Message;

/**
 * Created by Jenny on 07.01.2016.
 */
public class GameDeletedMessage implements Message {

    private final String type = "game deleted";
    private int gameID;
    private String nick;

    public GameDeletedMessage(int gameID) {
        this.gameID = gameID;
    }

    /* Getter & Setter */
    public String getType() {
        return type;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
