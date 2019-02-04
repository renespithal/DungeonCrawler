package network.messages;

/**
 * Created by Jenny on 03.01.2016.
 */
public class GameStarted {

    private final String type = "game started";
    private int gameID;
    private final String gamestate = "started";

    public GameStarted(int gameID) {
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
