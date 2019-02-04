package network.messages;

import network.interfaces.Message;

/**
 * Created by Jenny on 18.01.2016.
 */
public class GameUpdate implements Message {

    private final String type = "game update";
    private SpielInfo[] games;

    public GameUpdate(SpielInfo[] games) {
        this.games = games;
    }

    public SpielInfo[] getGames() {
        return games;
    }
}
