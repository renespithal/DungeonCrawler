package network.messages;

import network.interfaces.Message;

/**
 * Created by Jenny on 04.01.2016.
 */
public class StartGame implements Message {

    private final String type = "start game";
    private int gameID;

    public StartGame(int gameID) {
        this.gameID = gameID;
    }

    /* Getter & Setter */
    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
