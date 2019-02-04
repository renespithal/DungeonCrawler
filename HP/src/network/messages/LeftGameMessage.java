package network.messages;

import network.interfaces.Message;

/**
 * Created by Jenny on 03.01.2016.
 */
public class LeftGameMessage implements Message {

    private final String type = "leave game";
    private int gameID;
    private String nick;

    public LeftGameMessage(int gameID, String nick) {
        this.gameID = gameID;
        this.nick = nick;
    }

    /* Getter & Setter */
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
