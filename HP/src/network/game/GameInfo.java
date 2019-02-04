package network.game;

/**
 * Created by Jenny on 18.01.2016.
 */
public class GameInfo {

    private int gameID;
    private String gamestate;
    private int depth;
    private int difficulty;
    private int playersCount;

    public GameInfo(int gameID, String gamestate) {
        this.gameID = gameID;
        this.gamestate = gamestate;
        this.playersCount = 1;
    }

    public void incPlayer() {
        playersCount++;
    }

    /* Getter & Setter */

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getGamestate() {
        return gamestate;
    }

    public void setGamestate(String gamestate) {
        this.gamestate = gamestate;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public void setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
    }
}
