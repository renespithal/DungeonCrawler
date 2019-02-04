package network.messages;

import network.interfaces.Message;

/**
 * Created by sarah on 13.12.2015.
 */
public class JoinGameMessage implements Message {

	private final String type = "join game";
	private int gameID;
	private String character_class;
	
	public JoinGameMessage(int gameID, String character_class) {
		super();
		this.gameID = gameID;
		this.character_class = character_class;
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

	public String getCharacter_class() {
		return character_class;
	}
	
	public void setCharacter_class( String character_class ) {
		this.character_class = character_class;
	}
}
