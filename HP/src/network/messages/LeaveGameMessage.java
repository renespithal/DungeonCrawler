package network.messages;

import network.interfaces.Message;

/**
 * Created by Jenny on 05.12.2015.
 */
public class LeaveGameMessage implements Message {

    private final String type = "leave game";
    private int gameID;
    private String nick;

    public LeaveGameMessage(int gameID, String nick) {
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
}
