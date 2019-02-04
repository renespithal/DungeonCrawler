package network.messages;

import network.interfaces.Message;

/**
 * Created by sarah on 13.12.2015.
 */
public class PlayerAddedMessage implements Message {

	private final String type = "player added";
	private int gameID;
	private SpielInfo game;

	public PlayerAddedMessage(int gameID, SpielInfo game) {
		this.gameID = gameID;
		this.game = game;
	}

	/* getter and setter */
	public String getType() {
		return type;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID( int gameID ) {
		this.gameID = gameID;
	}

	public SpielInfo getGame() {
		return game;
	}

	public void setGame( SpielInfo game ) {
		this.game = game;
	}
}
