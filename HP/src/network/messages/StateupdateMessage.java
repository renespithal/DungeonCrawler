package network.messages;

import network.client.communication.coder.PlayerDecoder;
import network.interfaces.Message;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by sarah on 13.12.2015.
 */
public class StateupdateMessage implements Message {

	private final String type = "stateupdate";
	private int gameID;
	private SpielerInfo[] players;
	private Spielfeld[] map;
	private SpielzugMessage[] moves;
	private PlayerDecoder playerDecoder;

	public StateupdateMessage(int gameID, SpielerInfo[] players, SpielzugMessage[] moves, Spielfeld[] map) {
		this.gameID = gameID;
		this.players = players;
		this.moves = moves;
		this.map = map;

	}
	
	/* getter and setter */

	public String getType() {
		return type;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public SpielerInfo[] getPlayers() {
		return players;
	}

	public void setPlayers(SpielerInfo[] players) {
		this.players = players;
	}

	public Spielfeld[] getMap() {
		return map;
	}

	public void setMap(Spielfeld[] map) {
		this.map = map;
	}

	public SpielzugMessage[] getMoves() {
		return moves;
	}

	public void setMoves(SpielzugMessage[] moves) {
		this.moves = moves;
	}
}
