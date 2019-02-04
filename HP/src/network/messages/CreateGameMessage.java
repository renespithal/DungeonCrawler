package network.messages;

import network.interfaces.Message;

/**
 * Created by sarah on 13.12.2015.
 */
public class CreateGameMessage implements Message {

	private final String type = "create game";
	private String character_class;
	private int depth;
	private int difficulty;

	public CreateGameMessage(String character_class, int depth, int difficulty) {
		this.character_class = character_class;
		this.depth = depth;
		this.difficulty = difficulty;
	}

	public String getType() {
		return type;
	}

	public String getCharacter_class() {
		return character_class;
	}

	public void setCharacter_class( String character_class ) {
		this.character_class = character_class;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth( int depth ) {
		this.depth = depth;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty( int difficulty ) {
		this.difficulty = difficulty;
	}
}
