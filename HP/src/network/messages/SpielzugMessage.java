package network.messages;

import network.interfaces.Message;

/**
 * Created by sarah on 13.12.2015.
 */
public class SpielzugMessage implements Message {

	private final String type = "playermove";
	private String nick;
	private String action;
	private String item;
	private String direction;
	private int gameID;
	
	public SpielzugMessage(String nick, String action, String direction) {
		this.nick = nick;
		this.action = action;
		this.direction = direction;
	}

	public SpielzugMessage(String nick, String action, String item, String direction) {
		this.nick = nick;
		this.action = action;
		this.item = item;
		this.direction = direction;
	}
	
	/* getter and setter */
	public String getType() {
		return type;
	}
	
	public String getNick() {
		return nick;
	}
	
	public void setNick( String nick ) {
		this.nick = nick;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction( String action ) {
		this.action = action;
	}
	
	public String getItem() {
		return item;
	}

	public void setItem( String item ) {
		this.item = item;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public void setDirection( String direction ) {
		this.direction = direction;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
}
