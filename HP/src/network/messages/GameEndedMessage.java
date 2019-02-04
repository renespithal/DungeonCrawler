package network.messages;

import network.interfaces.Message;

/**
 * Created by Jenny on 07.01.2016.
 */
public class GameEndedMessage implements Message {

    private final String type = "game ended";
    private int gameID;
    private String nick;

    public GameEndedMessage(int gameID) {
        this.gameID = gameID;
    }

    public GameEndedMessage(int gameID, String nick) {
        this.gameID = gameID;
        this.nick = nick;
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
