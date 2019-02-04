package network.messages;



/**
 * Created by sarah on 13.12.2015.
 */
public class SpielInfo {
	
	private int gameID;
	private String gamestate;
	private SpielerInfo[] players;
	private int depth;
	private int difficulty;
	
	int playersCount = 1;
	
	public SpielInfo(int gameID, String gamestate, SpielerInfo[] players, int depth, int difficulty) {
		this.gameID = gameID;
		this.gamestate = gamestate;
		this.players = players;
		this.depth = depth;
		this.difficulty = difficulty;
	}
	
	public int getGameID() {
		return gameID;
	}
	
	public void setGameID( int gameID ) {
		this.gameID = gameID;
	}
	
	public String getGamestate() {
		return gamestate;
	}
	
	public void setGamestate( String gamestate ) {
		this.gamestate = gamestate;
	}
	
	public SpielerInfo[] getPlayers() {
		return players;
	}
	
	public void setPlayers( SpielerInfo[] players ) {
		this.players = players;
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

	public int getPlayersCount() {
		return playersCount;
	}

	public void setPlayersCount( int playersCount ) {
		this.playersCount = playersCount;
	}
}
